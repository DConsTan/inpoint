package nl.tudelft.inpoint;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

public class LocalizeController implements View.OnClickListener {

    private View view;

    public LocalizeController(View view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        setRoom(view, (TextView) view.findViewById(R.id.room1), (float) Math.random());
    }

    private void setRoom(View v, TextView room, float probability) {
        int p = Math.round(probability * 100);
        room.setBackgroundColor(view.getResources().getColor(Globals.getColor(p)));
        room.setText(p + "");
    }

}
