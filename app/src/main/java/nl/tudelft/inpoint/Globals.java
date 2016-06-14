package nl.tudelft.inpoint;

import android.app.Activity;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ConcurrentHashMap;

public class Globals {
    public static TextView SELECTED_ROOM;
    public static int MAP_DEFAULT_COLOR;
    public static int MAP_SELECTED_COLOR;
    public static boolean RECORDING = false;
    public static final String DATABASE_NAME = "InPoint.sqlite";
    public static ConcurrentHashMap<String, int[]> RSS_VALUES = new ConcurrentHashMap<>();
    public static WifiManager WIFI_MANAGER;
    public static SQLiteHelper DATABASE;
    public static int NUMBER_OF_ROOMS = 21;
    public static Resources RESOURCES;
    public static String PACKAGE_NAME;
    public static float[] POSTERIOR = new float[NUMBER_OF_ROOMS + 1];
    public static float MAX_PRIOR;
    public static Activity ACTIVITY;
    public static View VIEW;
    public static int SAMPLE_COUNTER;
    public static float CONFIDENCE_INTERVAL = 0.20f;
    public static RSSRecorder RECORDER;

    public static int getColor(int i) {
        if (i >= 0 && i <= 100) return RESOURCES.getIdentifier("color" + i, "color", PACKAGE_NAME);
        return R.color.mapDefault;
    }
}
