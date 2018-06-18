package sy.edu.au.nodemcu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class F2Orienation extends Fragment {

    public static F2Orienation of(int sectionNumber) {
        Log.d("suhel", "F2Orientation of " + sectionNumber);

        F2Orienation fragment = new F2Orienation();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
                String url = MainActivity.ipAddress;
                Log.i("suhel", "btnPrint was clicked");
                Log.i("suhel", "new ip address to " + url);
            }
        });
        return rootView;
    }
}
