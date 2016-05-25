package nl.tudelft.inpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    public ArrayList<String> TABLES = new ArrayList<>();
    private final String PREFIX_FREQUENCY = "frequency_";
    private final String PREFIX_GAUSSIAN = "gaussian_";
    private final String PREFIX_PMF = "pmf_";


    public SQLiteHelper(Context context) {
        super(context, context.getExternalFilesDir(null) + File.separator + Globals.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS ";
        for (String table : TABLES)
            db.execSQL(query + table);
        onCreate(db);
    }

    public void createFrequencyTable(String name) {
        name = PREFIX_FREQUENCY + name;
        TABLES.add(name);
        SQLiteDatabase db = getWritableDatabase();
        String query= "CREATE TABLE IF NOT EXISTS " + name + " (room_id INTEGER PRIMARY KEY";
        for (int i = 10; i <= 100; i++)
            query += ", rss" + i + " INTEGER DEFAULT 0";
        query += ");";
        db.execSQL(query);
    }

    public void createPMFTable(String name) {
        name = PREFIX_PMF + name;
        TABLES.add(name);
        SQLiteDatabase db = getWritableDatabase();
        String query= "CREATE TABLE IF NOT EXISTS " + name + " (room_id INTEGER PRIMARY KEY";
        for (int i = 10; i <= 100; i++)
            query += ", rss" + i + " REAL DEFAULT 0";
        query += ");";
        db.execSQL(query);
    }

    public void createGaussianTable(String name) {
        name = PREFIX_GAUSSIAN + name;
        TABLES.add(name);
        SQLiteDatabase db = getWritableDatabase();
        String query= "CREATE TABLE IF NOT EXISTS " + name + " (room_id INTEGER PRIMARY KEY, mean REAL DEFAULT 0, standard_deviation REAL DEFAULT 0);";
        db.execSQL(query);
    }

    public void updateRSSFrequencies(String table, int roomID, int[] rss) {
        SQLiteDatabase db = getWritableDatabase();
        String frequencyTable = PREFIX_FREQUENCY + table;
        int[] frequencies = readRSSFrequencies(frequencyTable, roomID);
        ContentValues values = new ContentValues();
        values.put("room_id", roomID);
        for (int i = 10; i <= 100; i++) {
            frequencies[i] += rss[i];
            values.put("rss" + i, frequencies[i]);
        }
        db.delete(frequencyTable, "room_id = " + roomID, null);
        db.insert(frequencyTable, null, values);
        db.close();
        updateRSSGuassian(table, roomID, frequencies);
    }

    public void updateRSSGuassian(String table, int roomID, int[] rss) {
        SQLiteDatabase db = getWritableDatabase();
        table = PREFIX_GAUSSIAN + table;

        float mean = mean(rss);
        float sd = standardDeviation(mean, rss);

        ContentValues values = new ContentValues();
        values.put("room_id", roomID);
        values.put("mean", mean);
        values.put("standard_deviation", sd);
        db.delete(table, "room_id = " + roomID, null);
        db.insert(table, null, values);
        db.close();
    }

    public float mean(int[] rss) {
        float sum = 0;
        float n = 0;
        for (int i = 10; i <= 100; i++) {
            sum += rss[i] * i;
            n += rss[i];
        }
        return sum / n;
    }

    public float standardDeviation(float mean, int[] rss) {
        float sum = 0;
        float n = -1;
        for (int i = 10; i <= 100; i++) {
            sum += rss[i] * Math.pow(i - mean, 2);
            n += rss[i];
        }
        if (n == 0) return 0;
        return (float) Math.sqrt((1 / n) * sum);
    }

    public void updateRSSPMF(String table, int roomID, int[] rss) {
        SQLiteDatabase db = getWritableDatabase();
        table = PREFIX_PMF + table;
        float[] pmf = toPMF(rss);
        ContentValues values = new ContentValues();
        values.put("room_id", roomID);
        for (int i = 10; i <= 100; i++)
            values.put("rss" + i, pmf[i]);
        db.delete(table, "room_id = " + roomID, null);
        db.insert(table, null, values);
        db.close();
    }

    public int[] readRSSFrequencies(String table, int roomID) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.query(table, null, "room_id =" + roomID, null, null, null, null);
            cursor.moveToNext();
            int[] frequencies = new int[101];
            for (int i = 10; i <= 100; i++)
                frequencies[i] = cursor.getInt(cursor.getColumnIndex("rss" + i));
            return frequencies;
        } catch (Exception e) {
            return new int[101];
        }
    }

    public float[] getRSSProbabilities(String table, int rss) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { "room_id", "rss" + rss };
        try {
            Cursor cursor = db.query(table, columns, null, null, null, null, null);
            float[] probabilities = new float[Globals.NUMBER_OF_ROOMS + 1];
            while (cursor.moveToNext()) {
                int roomID = cursor.getInt(cursor.getColumnIndex("room_id"));
                float p = cursor.getFloat(cursor.getColumnIndex("rss" + rss));
                probabilities[roomID] = p;
            }
            return probabilities;
        } catch (Exception e) {
            return null;
        }
    }

    public float[] toPMF(int[] rss) {
        float[] result = new float[101];
        float sum = 0f;
        for (int i : rss)
            sum += (float) i;
        for (int i = 10; i <= 100; i++)
            result[i] = (float) rss[i] / sum;
        return result;
    }

    public static String encodeMAC(String mac) {
        return mac.replace(":", "");
    }

}
