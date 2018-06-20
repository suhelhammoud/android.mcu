package sy.edu.au.nodemcu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class F3Buttons extends Fragment implements View.OnTouchListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f3_buttons, container, false);

        rootView.findViewById(R.id.btnStop)
                .setOnTouchListener(this);

        rootView.findViewById(R.id.btnForward)
                .setOnTouchListener(this);

        rootView.findViewById(R.id.btnBackword)
                .setOnTouchListener(this);

        rootView.findViewById(R.id.btnLeft)
                .setOnTouchListener(this);

        rootView.findViewById(R.id.btnRight)
                .setOnTouchListener(this);


        return rootView;
    }


    public static F3Buttons of(int sectionNumber) {
        Log.d("suhel", "F3Buttons of " + sectionNumber);

        F3Buttons fragment = new F3Buttons();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private CommandType getCommandType(int id) {
        switch (id) {
            case R.id.btnStop:
                return CommandType.stop;
            case R.id.btnForward:
                return CommandType.forward;
            case R.id.btnBackword:
                return CommandType.backward;
            case R.id.btnLeft:
                return CommandType.left;
            case R.id.btnRight:
                return CommandType.right;
            default:
                return CommandType.stop;
        }


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("suhel", "touch button " + event.getAction());
        CommandType cmd = getCommandType(v.getId());

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
                Log.d("suhel", "release " + cmd);
                MainActivity.command.setCommand(CommandType.stop);

            case MotionEvent.ACTION_DOWN:
                Log.d("suhel", "press " + cmd);
                MainActivity.command.setCommand(cmd);

        }

        return true;
    }
}
