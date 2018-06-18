package sy.edu.au.nodemcu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class F3Voice extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f3_voice , container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.lbl);
//        textView.setText(getString(R.string.section_format, getArguments().getInt("sn")));
        return rootView;
    }


    public static F3Voice of(int sectionNumber) {
        Log.d("suhel", "F3Voice of " + sectionNumber);

        F3Voice fragment = new F3Voice();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
