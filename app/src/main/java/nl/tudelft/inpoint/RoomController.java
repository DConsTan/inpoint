package nl.tudelft.inpoint;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RoomController implements View.OnClickListener {

    private TextView roomView;

    public RoomController(TextView roomID) {
        this.roomView = roomID;
    }

    @Override
    public void onClick(View view) {
        Globals.SELECTED_ROOM.setBackgroundColor(Globals.MAP_DEFAULT_COLOR);
        Globals.SELECTED_ROOM = roomView;
        Log.i("Room selection:", roomView.getText().toString());
        roomView.setBackgroundColor(Globals.MAP_SELECTED_COLOR);
    }
}
