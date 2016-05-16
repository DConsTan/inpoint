package nl.tudelft.inpoint;

import android.content.res.ColorStateList;
import android.content.res.Resources;
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
        } else {
            button.setImageResource(R.drawable.ic_pause);
            button.setBackgroundTintList(ColorStateList.valueOf(Globals.mapSelectedColor));
        }
        Globals.recording = !Globals.recording;
    }
}
