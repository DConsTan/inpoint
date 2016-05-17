package nl.tudelft.inpoint;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

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
        if (Globals.recording) {
            button.setImageResource(R.drawable.ic_play_arrow);
            button.setBackgroundTintList(ColorStateList.valueOf(Globals.mapDefaultColor));
            storeRSStoDatabase();
            Globals.db.readRSSValues("ap_00145c87fed8", 1);
        } else {
            Globals.rssValues.clear();
            button.setImageResource(R.drawable.ic_pause);
            button.setBackgroundTintList(ColorStateList.valueOf(Globals.mapSelectedColor));
            Thread t = new Thread(new RSSRecorder());
            t.start();
        }
        Globals.recording = !Globals.recording;
    }

    private void storeRSStoDatabase() {
        int roomID = Integer.parseInt(Globals.selectedRoom.getText().toString());

        for (String mac : Globals.rssValues.keySet()) {
            String table = SQLiteHelper.encodeMAC(mac);
            Globals.db.createTable(table);
            Globals.db.updateRSSValues(table, roomID, Globals.rssValues.get(mac));
        }
    }
}
