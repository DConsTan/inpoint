package nl.tudelft.inpoint;

import android.content.res.Resources;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LocalizeController implements View.OnClickListener {

    private View view;

    public LocalizeController(View view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        if (Globals.WIFI_MANAGER.startScan()) {
            List<ScanResult> list = Globals.WIFI_MANAGER.getScanResults();
            Collections.sort(list, new ScanResultComparator());
            Iterator<ScanResult> iter = list.iterator();
            while (iter.hasNext() && Globals.MAX_PRIOR <= 0.95) {
                ScanResult r = iter.next();
                String mac = SQLiteHelper.encodeMAC(r.BSSID);
                int level = -r.level;
                float[] probabilities = Globals.DATABASE.getRSSProbabilities(mac, level);
                if (probabilities != null) {
                    applyBayes(probabilities);
                    showPosterior();
                    for (int j = 1; j <= Globals.NUMBER_OF_ROOMS; j++)
                        Log.i("Room" + j, Globals.POSTERIOR[j] + "");
                }
            }
        }
    }

    private void applyBayes(float[] probabilities) {
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++)
            Globals.POSTERIOR[i] = Globals.POSTERIOR[i] * probabilities[i];
        normalizePosterior();
    }

    private void normalizePosterior() {
        float sum = 0;
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++)
            sum += Globals.POSTERIOR[i];
        if (sum == 0)
            return;
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++) {
            Globals.POSTERIOR[i] = Globals.POSTERIOR[i] / sum;
            if (Globals.POSTERIOR[i] > Globals.MAX_PRIOR)
                Globals.MAX_PRIOR = Globals.POSTERIOR[i];
        }
    }

    private void showPosterior() {
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++) {
            int id = Globals.RESOURCES.getIdentifier("room" + i, "id", Globals.PACKAGE_NAME);
            TextView room = (TextView) view.findViewById(id);
            setRoom(room, Globals.POSTERIOR[i]);
        }
    }

    private void setRoom(TextView room, float probability) {
        int p = Math.round(probability * 100);
        room.setBackgroundColor(Globals.RESOURCES.getColor(Globals.getColor(p)));
        room.setText(p + "");
    }

}
