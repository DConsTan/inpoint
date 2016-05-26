package nl.tudelft.inpoint;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class RecordController implements View.OnClickListener {

    private FloatingActionButton button;

    public RecordController(FloatingActionButton floatingActionButton) {
        this.button = floatingActionButton;
    }

    @Override
    public void onClick(View view) {
        toggleIcon();
     }

    private void toggleIcon() {
        if (Globals.RECORDING) {
            button.setImageResource(R.drawable.ic_play_arrow);
            button.setBackgroundTintList(ColorStateList.valueOf(Globals.MAP_DEFAULT_COLOR));
            storeRSStoDatabase();
        } else {
            Globals.RSS_VALUES.clear();
            button.setImageResource(R.drawable.ic_pause);
            button.setBackgroundTintList(ColorStateList.valueOf(Globals.MAP_SELECTED_COLOR));
            Thread t = new Thread(new RSSRecorder());
            t.start();
        }
        Globals.RECORDING = !Globals.RECORDING;
    }

    private void storeRSStoDatabase() {
        int roomID = Integer.parseInt(Globals.SELECTED_ROOM.getText().toString());

        Iterator iter = Globals.RSS_VALUES.keySet().iterator();
        while (iter.hasNext()) {
            String mac = (String) iter.next();
            String table = SQLiteHelper.encodeMAC(mac);
            Globals.DATABASE.createFrequencyTable(table);
            Globals.DATABASE.createGaussianTable(table);
            Globals.DATABASE.updateRSSFrequencies(table, roomID, Globals.RSS_VALUES.get(mac));
        }

        Globals.DATABASE.filterAP();
    }
}
