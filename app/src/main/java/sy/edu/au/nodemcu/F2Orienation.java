package sy.edu.au.nodemcu;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;

public class F2Orienation extends Fragment implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;

    public static F2Orienation of(int sectionNumber) {
        Log.d("suhel", "F2Orientation of " + sectionNumber);

        F2Orienation fragment = new F2Orienation();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    void initSensors() {

        int sensorType = Sensor.TYPE_ORIENTATION;
        Log.d("suhel", "init sensor of type : " + sensorType);
//        int sensorType = Sensor.TYPE_GYROSCOPE;
//        int sensorType = Sensor.TYPE_MOTION_DETECT;

        mSensorManager = (SensorManager) ((Activity) getActivity())
                .getSystemService(Context.SENSOR_SERVICE);


        if (mSensorManager.getDefaultSensor(sensorType) != null) {
            // Success! There's a gyroscope.
            Log.d("suhel", "there's a gyroscope");
            mSensor = mSensorManager.getSensorList(sensorType).get(0);

        } else {
            Log.e("suhel", "Found no sensor of type : " + sensorType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f2_orientation, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.lbl);
//        textView.setText(getString(R.string.section_format, getArguments().getInt("sn")));

        Button btnPrint = (Button) rootView.findViewById(R.id.btnPrintIP);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((MainActivity) getActivity()).getIpAddress();
                Log.i("suhel", "btnPrint was clicked");
                Log.i("suhel", "new ip address to " + url);
            }
        });

        initSensors();
        return rootView;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        Log.d("sensor.v", String.format("x = %03.3f, y = %03.3f, z = %03.3f", x, y, z));

        MainActivity.command.sensorChanged(x, y, z);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("suhel", "onAccuracyChanged, sensor "
                + sensor.toString() + ", accuracy = " + accuracy);
    }

    private void registerSensor() {
        Log.d("suhel", "onstart register sensor listener");
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        MainActivity.command.setCommand(CommandType.stop);
    }

    private void unregisterSensor() {
        Log.d("suhel", "onstop unregister sensor listener");
        mSensorManager.unregisterListener(this);
        MainActivity.command.setCommand(CommandType.stop);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("suhel", "Not visible anymore.  Stopping sensors.");
                unregisterSensor();
                // TODO stop audio playback
            } else {
                registerSensor();
            }
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        unregisterSensor();
    }


}
