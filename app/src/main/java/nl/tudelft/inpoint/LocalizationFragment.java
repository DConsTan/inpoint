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
        setResetListener();
    }

    private void initRooms() {
        float probability = 1f / Globals.NUMBER_OF_ROOMS;
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++) {
            int id = Globals.RESOURCES.getIdentifier("room" + i, "id", Globals.PACKAGE_NAME);
            setRoom((TextView) getView().findViewById(id), probability);
        }
        for (int i = 1; i <= Globals.NUMBER_OF_ROOMS; i++) {
            Globals.POSTERIOR[i] = probability;
        }
        Globals.MAX_PRIOR = probability;
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

    private void setResetListener() {
        FloatingActionButton button = (FloatingActionButton) getView().findViewById(R.id.fabReset);
        button.setOnClickListener(new ResetController(getView()));
    }

}
