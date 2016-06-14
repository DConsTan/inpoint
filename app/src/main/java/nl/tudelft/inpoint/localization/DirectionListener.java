package nl.tudelft.inpoint.localization;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import nl.tudelft.inpoint.Globals;
import nl.tudelft.inpoint.R;

public class DirectionListener implements View.OnClickListener, SensorEventListener {

    private float[] mGravity;
    private float[] mGeomagnetic;

    @Override
    public void onClick(View view) {
        Globals.DIRECTION_ZERO = Globals.DIRECTION_CURRENT;
        Log.d("DIRECTION ZERO: ", Globals.DIRECTION_ZERO + "");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        Log.d("Z-Value: ", sensorEvent.values[2] + "");
//        float x = - sensorEvent.values[2];
//        if (x < 0) {
//            Globals.DIRECTION_CURRENT = (int) ((x + 2) * 180);
//        } else {
//            Globals.DIRECTION_CURRENT = (int) ((x) * 180);
//        }
//        Globals.DIRECTION = Globals.DIRECTION_CURRENT - Globals.DIRECTION_ZERO;
//        if (Globals.DIRECTION > 360) {
//            Globals.DIRECTION = Globals.DIRECTION - 360;
//        } else if (Globals.DIRECTION < 0) {
//            Globals.DIRECTION = Globals.DIRECTION + 360;
//        }
////        Log.i("VALUES: ", x + ", " + Globals.DIRECTION_CURRENT + ", " + Globals.DIRECTION_ZERO  + ", " + Globals.DIRECTION );
//        int id = Globals.ACTIVITY.getResources().getIdentifier("fabDirection", "id", Globals.ACTIVITY.getPackageName());
//        FloatingActionButton button = (FloatingActionButton) Globals.VIEW.findViewById(id);
//
//        if (Globals.DIRECTION > 315 || Globals.DIRECTION <= 45) {
//            button.setImageResource(R.drawable.ic_arrow_forward);
//        } else if (Globals.DIRECTION > 45 && Globals.DIRECTION <= 115) {
//            button.setImageResource(R.drawable.ic_arrow_downward);
//        } else if (Globals.DIRECTION > 115 && Globals.DIRECTION <= 225) {
//            button.setImageResource(R.drawable.ic_arrow_back);
//        } else {
//            button.setImageResource(R.drawable.ic_arrow_upward);
//        }


        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = sensorEvent.values;
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = sensorEvent.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                float azimut = orientation[0];
                Log.d("Azimut: ", azimut + "");
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
