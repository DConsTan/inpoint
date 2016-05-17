package nl.tudelft.inpoint;

import android.net.wifi.ScanResult;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

class RSSRecorder implements Runnable {
    @Override
    public void run() {
        while (Globals.recording) {
            if (Globals.wifiManager.startScan()) {
                List<ScanResult> list = Globals.wifiManager.getScanResults();

                for (ScanResult r : list) {
                    int level = Math.abs(r.level);
                    if (!Globals.rssValues.containsKey(r.BSSID)) {
                        int[] rss = new int[101];
                        Globals.rssValues.put(r.BSSID, rss);
                    }
                    int[] rss = Globals.rssValues.get(r.BSSID);
                    Log.i(r.BSSID, Arrays.toString(rss));
                    rss[level]++;
                    Globals.rssValues.put(r.BSSID, rss);
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


