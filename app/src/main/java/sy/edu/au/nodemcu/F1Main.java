package sy.edu.au.nodemcu;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class F1Main extends Fragment {
    Activity activity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f1_main, container, false);
        activity = (Activity) getArguments().get("activity");

//        TextView textView = (TextView) rootView.findViewById(R.id.lbl);
//        textView.setText(getString(R.string.section_format, getArguments().getInt("sn")));
        return rootView;
    }


    public static F1Main of(int sectionNumber) {
        Log.d("suhel", "F1Main of " + sectionNumber);
        F1Main fragment = new F1Main();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
