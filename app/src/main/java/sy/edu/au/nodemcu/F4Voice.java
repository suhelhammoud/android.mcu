package sy.edu.au.nodemcu;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import sy.edu.au.nodemcu.audio.AudioDataSaver;
import sy.edu.au.nodemcu.audio.PlaybackThread;
import sy.edu.au.nodemcu.audio.RecordingThread;

public class F4Voice extends Fragment {
    private View rootView;
    private Button record_button;
    private Button play_button;
    private TextView log;
    private ScrollView logView;
    static String strLog = null;

    private int preVolume = -1;
    private static long activeTimes = 0;

    private RecordingThread recordingThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f4_voice, container, false);
        setUI();

        setProperVolume();

//        AppResCopy.copyResFromAssetsToSD(getActivity());

        activeTimes = 0;
        recordingThread = new RecordingThread(handle,
                VConstants.activeModel("left"));
        Log.i("suhel", "alexa activity created");
        return rootView;
    }

    void showToast(CharSequence msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setUI() {
        record_button = (Button) rootView.findViewById(R.id.btn_test1);
        record_button.setOnClickListener(record_button_handle);
        record_button.setEnabled(true);

        play_button = (Button) rootView.findViewById(R.id.btn_test2);
        play_button.setOnClickListener(play_button_handle);
        play_button.setEnabled(true);

        log = (TextView) rootView.findViewById(R.id.log);
        logView = (ScrollView) rootView.findViewById(R.id.logView);
    }

    private void setMaxVolume() {
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        preVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> preVolume = " + preVolume, "green");
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> maxVolume = " + maxVolume, "green");
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> currentVolume = " + currentVolume, "green");
    }

    private void setProperVolume() {
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        preVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> preVolume = " + preVolume, "green");
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> maxVolume = " + maxVolume, "green");
        int properVolume = (int) ((float) maxVolume * 0.2);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, properVolume, 0);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateLog(" ----> currentVolume = " + currentVolume, "green");
    }

    private void restoreVolume() {
        if (preVolume >= 0) {
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, preVolume, 0);
            updateLog(" ----> set preVolume = " + preVolume, "green");
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            updateLog(" ----> currentVolume = " + currentVolume, "green");
        }
    }

    private void startRecording() {
        recordingThread.startRecording();
        updateLog(" ----> recording started ...", "green");
    }

    private void stopRecording() {
        recordingThread.stopRecording();
        updateLog(" ----> recording stopped ", "green");
    }


    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    private View.OnClickListener record_button_handle = new View.OnClickListener() {
        // @Override
        public void onClick(View arg0) {
            sleep();
            startRecording();
        }
    };

    private View.OnClickListener play_button_handle = new View.OnClickListener() {
        // @Override
        public void onClick(View arg0) {
                stopRecording();
                sleep();
        }
    };


    public Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MsgEnum message = MsgEnum.getMsgEnum(msg.what);
            switch (message) {
                case MSG_ACTIVE:
                    activeTimes++;
                    updateLog(" ----> Detected " + activeTimes + " times, "+ msg.obj , "green");
                    // Toast.makeText(Demo.this, "Active "+activeTimes, Toast.LENGTH_SHORT).show();
                    showToast("Active " + activeTimes);
                    break;
                case MSG_INFO:
                    updateLog(" ----> " + message);
                    break;
                case MSG_VAD_SPEECH:
                    updateLog(" ----> normal voice", "blue");
                    break;
                case MSG_VAD_NOSPEECH:
                    updateLog(" ----> no speech", "blue");
                    break;
                case MSG_ERROR:
                    updateLog(" ----> " + msg.toString(), "red");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    public void updateLog(final String text) {

        log.post(new Runnable() {
            @Override
            public void run() {
                if (currLogLineNum >= MAX_LOG_LINE_NUM) {
                    int st = strLog.indexOf("<br>");
                    strLog = strLog.substring(st + 4);
                } else {
                    currLogLineNum++;
                }
                String str = "<font color='white'>" + text + "</font>" + "<br>";
                strLog = (strLog == null || strLog.length() == 0) ? str : strLog + str;
                log.setText(Html.fromHtml(strLog));
            }
        });
        logView.post(new Runnable() {
            @Override
            public void run() {
                logView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    static int MAX_LOG_LINE_NUM = 200;
    static int currLogLineNum = 0;

    public void updateLog(final String text, final String color) {
        log.post(new Runnable() {
            @Override
            public void run() {
                if (currLogLineNum >= MAX_LOG_LINE_NUM) {
                    int st = strLog.indexOf("<br>");
                    strLog = strLog.substring(st + 4);
                } else {
                    currLogLineNum++;
                }
                String str = "<font color='" + color + "'>" + text + "</font>" + "<br>";
                strLog = (strLog == null || strLog.length() == 0) ? str : strLog + str;
                log.setText(Html.fromHtml(strLog));
            }
        });
        logView.post(new Runnable() {
            @Override
            public void run() {
                logView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void emptyLog() {
        strLog = null;
        log.setText("");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("suhel", "Not visible anymore.  Stopping recording.");
                recordingThread.stopRecording();
                // TODO stop audio playback
            } else {
                Log.d("suhel", " visible user visible fragment.");

            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        recordingThread.stopRecording();
    }


    @Override
    public void onDestroy() {
        restoreVolume();
        recordingThread.stopRecording();
        super.onDestroy();
    }

    public static F4Voice of(int sectionNumber) {
        Log.d("suhel", "F4Voice of " + sectionNumber);

        F4Voice fragment = new F4Voice();
        Bundle args = new Bundle();
        args.putInt("sn", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
