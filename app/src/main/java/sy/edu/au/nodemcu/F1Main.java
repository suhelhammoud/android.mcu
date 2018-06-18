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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class F1Main extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.f1_main, container, false);

        TextView textIP = (TextView) rootView.findViewById(R.id.txtIP);

        textIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("suhel", "txtIP has lost focus");
                    setIP((TextView) v);
                }

            }
        });
//        textView.setText(getString(R.string.section_format, getArguments().getInt("sn")));

        Button btnSetIP = (Button) rootView.findViewById(R.id.btnSetIp);
        btnSetIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("suhel", "btnSetIP was clicked");
                setIP((TextView) rootView.findViewById(R.id.txtIP));

            }
        });
        return rootView;
    }

    public void setIP(TextView vIPl) {
        String url = ((TextView) vIPl).getText().toString();
        Log.d("suhel", "setIP to " + url);
        MainActivity.ipAddress = url;
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
