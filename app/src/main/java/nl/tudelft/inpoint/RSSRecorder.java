package nl.tudelft.inpoint;

import android.net.wifi.ScanResult;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;

class RSSRecorder extends Observable implements Runnable {

    private Handler handler;

    public RSSRecorder() {
        handler = new Handler();
    }

    @Override
    public void run() {
        Globals.SAMPLE_COUNTER = 0;
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

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) Globals.VIEW.findViewById(R.id.sampleCounter)).setText(++Globals.SAMPLE_COUNTER + "");
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    //
                }
            }
        }
    }
}


