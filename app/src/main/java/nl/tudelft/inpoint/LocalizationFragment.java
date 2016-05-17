package nl.tudelft.inpoint;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocalizationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_localization, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRooms();
        setLocalizeController();
    }

    private void initRooms() {
        float probability = 1f / 17;
        setRoom((TextView) getView().findViewById(R.id.room1), probability);
        setRoom((TextView) getView().findViewById(R.id.room2), probability);
        setRoom((TextView) getView().findViewById(R.id.room3), probability);
        setRoom((TextView) getView().findViewById(R.id.room4), probability);
        setRoom((TextView) getView().findViewById(R.id.room5), probability);
        setRoom((TextView) getView().findViewById(R.id.room6), probability);
        setRoom((TextView) getView().findViewById(R.id.room7), probability);
        setRoom((TextView) getView().findViewById(R.id.room8), probability);
        setRoom((TextView) getView().findViewById(R.id.room9), probability);
        setRoom((TextView) getView().findViewById(R.id.room10), probability);
        setRoom((TextView) getView().findViewById(R.id.room11), probability);
        setRoom((TextView) getView().findViewById(R.id.room12), probability);
        setRoom((TextView) getView().findViewById(R.id.room13), probability);
        setRoom((TextView) getView().findViewById(R.id.room14), probability);
        setRoom((TextView) getView().findViewById(R.id.room15), probability);
        setRoom((TextView) getView().findViewById(R.id.room16), probability);
        setRoom((TextView) getView().findViewById(R.id.room17), probability);
    }

    private void setRoom(TextView room, float probability) {
        int p = Math.round(probability * 100);
        room.setBackgroundColor(getResources().getColor(Globals.getColor(p)));
        room.setText(p + "");
    }

    private void setLocalizeController() {
        FloatingActionButton button = (FloatingActionButton) getView().findViewById(R.id.fabLocalize);
        button.setOnClickListener(new LocalizeController(getView()));
    }

}
