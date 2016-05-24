package nl.tudelft.inpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    public ArrayList<String> TABLES = new ArrayList<>();

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

    public void createAPTable(String name) {
        TABLES.add(name);
        SQLiteDatabase db = getWritableDatabase();
        String query= "CREATE TABLE IF NOT EXISTS " + name + " (room_id INTEGER PRIMARY KEY";
        for (int i = 10; i <= 100; i++)
            query += ", rss" + i + " REAL DEFAULT 0";
        query += ");";
        db.execSQL(query);
//        populateTable(name);
    }

    public void populateTable(String table) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++) {
            values.put("room_id", i);
            for (int j = 10; j <= 100; j++)
                values.put("rss" + j, 0);
            db.delete(table, "room_id = " + i, null);
            db.insert(table, null, values);
        }
        db.close();
    }

    public void updateRSSValues(String table, int roomID, int[] rss) {
        SQLiteDatabase db = getWritableDatabase();

        float[] pmf = toPMF(rss);

        ContentValues values = new ContentValues();
        values.put("room_id", roomID);
        for (int i = 10; i <= 100; i++)
            values.put("rss" + i, pmf[i]);
        db.delete(table, "room_id = " + roomID, null);
        db.insert(table, null, values);
        db.close();
    }

    public void readRSSValues(String table, int roomID) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = new String[92];
        columns[0] = "room_id";
        for (int i = 10; i <= 100; i++)
            columns[i - 9] = "rss" + i;
        Cursor cursor = db.query(table, columns, "room_id =" + roomID, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        for (int i = 0; i < 92; i++) {
            Log.d("Column: ", cursor.getString(i));
        }
    }

    public float[] getRSSProbabilities(String table, int rss) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = { "room_id", "rss" + rss };
        Cursor cursor = db.query(table, columns, null, null, null, null, null);
        float[] probabilities = new float[Globals.NUMBER_OF_ROOMS + 1];
        if (cursor.getCount() == 0) return null;
        while (cursor.moveToNext()) {
            int roomID = cursor.getInt(cursor.getColumnIndex("room_id"));
            float p = cursor.getFloat(cursor.getColumnIndex("rss" + rss));
            probabilities[roomID] = p;
        }
        return probabilities;
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
        String code = "ap_";
        code += mac.replace(":", "");
        return code;
    }

}
