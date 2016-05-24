package nl.tudelft.inpoint;

import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.widget.TextView;

import java.util.HashMap;

public class Globals {
    public static TextView SELECTED_ROOM;
    public static int MAP_DEFAULT_COLOR;
    public static int MAP_SELECTED_COLOR;
    public static boolean RECORDING = false;
    public static final String DATABASE_NAME = "InPoint.sqlite";
    public static volatile HashMap<String, int[]> RSS_VALUES = new HashMap<>();
    public static WifiManager WIFI_MANAGER;
    public static SQLiteHelper DATABASE;
    public static int NUMBER_OF_ROOMS = 21;
    public static Resources RESOURCES;
    public static String PACKAGE_NAME;

    public static int getColor(int i) {
        if (i == 0) return R.color.mapDefault;
        if (i > 0 && i <= 100) return RESOURCES.getIdentifier("color" + i, "color", PACKAGE_NAME);
        return R.color.mapDefault;
    }
}
