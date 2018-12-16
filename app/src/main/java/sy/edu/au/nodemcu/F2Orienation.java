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
import android.widget.TextView;

import java.util.Arrays;

public class F2Orienation extends Fragment implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView lblCaption;

    public static F2Orienation of(int sectionNumber) {
        Log.d("ahmad", "F2Orientation of " + sectionNumber);

        F2Orienation fragment = new F2Orienation();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    void initSensors() {

        int sensorType = Sensor.TYPE_ORIENTATION;
        Log.d("ahmad", "init sensor of type : " + sensorType);
//        int sensorType = Sensor.TYPE_GYROSCOPE;
//        int sensorType = Sensor.TYPE_MOTION_DETECT;

        mSensorManager = (SensorManager) ((Activity) getActivity())
                .getSystemService(Context.SENSOR_SERVICE);


        if (mSensorManager.getDefaultSensor(sensorType) != null) {
            // Success! There's a gyroscope.
            Log.d("ahmad", "there's a gyroscope");
            mSensor = mSensorManager.getSensorList(sensorType).get(0);

        } else {
            Log.e("ahmad", "Found no sensor of type : " + sensorType);
        }
    }

    public void setCaption(String msg) {
        lblCaption.setText(msg);
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
                String url = WirelessCommunicator.getIpAddress();
                Log.i("ahmad", "btnPrint was clicked");
                Log.i("ahmad", "new ip address to " + url);
            }
        });

        initSensors();

        lblCaption = (TextView) rootView.findViewById(R.id.lblOrientationOut);
        return rootView;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        Log.d("sensor.v", String.format("x = %03.3f, y = %03.3f, z = %03.3f", x, y, z));

        CommandType resultCommand = MainActivity.command.sensorChanged(x, y, z);
        lblCaption.setText(resultCommand.toString());


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("ahmad", "onAccuracyChanged, sensor "
                + sensor.toString() + ", accuracy = " + accuracy);
    }

    private void registerSensor() {
        Log.d("ahmad", "onstart register sensor listener");
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        MainActivity.command.setCommand(CommandType.stop);
    }

    private void unregisterSensor() {
        Log.d("ahmad", "onstop unregister sensor listener");
        mSensorManager.unregisterListener(this);
        MainActivity.command.setCommand(CommandType.stop);
        setCaption("STOP");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("ahmad", "Not visible anymore.  Stopping sensors.");
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
