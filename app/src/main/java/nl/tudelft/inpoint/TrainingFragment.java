package nl.tudelft.inpoint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrainingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Globals.selectedRoom = (TextView) getView().findViewById(R.id.room1);
        Globals.selectedRoom.setBackgroundColor(getResources().getColor(R.color.mapSelected));
        setRoomControllers();
    }

    private void setRoomControllers() {
        TextView room;
        room = (TextView) getView().findViewById(R.id.room1);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room2);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room3);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room4);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room5);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room6);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room7);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room8);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room9);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room10);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room11);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room12);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room13);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room14);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room15);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room16);
        room.setOnClickListener(new RoomController(room));
        room = (TextView) getView().findViewById(R.id.room17);
        room.setOnClickListener(new RoomController(room));
    }



}
