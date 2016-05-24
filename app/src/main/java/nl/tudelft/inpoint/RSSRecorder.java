package nl.tudelft.inpoint;

import android.net.wifi.ScanResult;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

class RSSRecorder implements Runnable {
    @Override
    public void run() {
        while (Globals.RECORDING) {
            if (Globals.WIFI_MANAGER.startScan()) {
                List<ScanResult> list = Globals.WIFI_MANAGER.getScanResults();

                for (ScanResult r : list) {
                    int level = Math.abs(r.level);
                    if (!Globals.RSS_VALUES.containsKey(r.BSSID)) {
                        int[] rss = new int[101];
                        Globals.RSS_VALUES.put(r.BSSID, rss);
                    }
                    int[] rss = Globals.RSS_VALUES.get(r.BSSID);
                    Log.i(r.BSSID, Arrays.toString(rss));
                    rss[level]++;
                    Globals.RSS_VALUES.put(r.BSSID, rss);
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    //
                }
            }
        }
    }
}


