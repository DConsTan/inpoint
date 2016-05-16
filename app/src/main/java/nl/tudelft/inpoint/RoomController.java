package nl.tudelft.inpoint;

import android.content.res.Resources;
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
        Globals.selectedRoom.setBackgroundColor(Globals.mapDefaultColor);
        Globals.selectedRoom = roomView;
        Log.i("Room selection:", roomView.getText().toString());
        roomView.setBackgroundColor(Globals.mapSelectedColor);
    }
}
