package sy.edu.au.nodemcu.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import sy.edu.au.nodemcu.VConstants;
import sy.edu.au.nodemcu.MsgEnum;
import ai.kitt.snowboy.SnowboyDetect;

import sy.edu.au.nodemcu.VModels;
public class RecordingThread {
    static {
        System.loadLibrary("snowboy-detect-android");
    }

    private static final String ACTIVE_RES = VConstants.ACTIVE_RES;

    private boolean shouldContinue;
    private Handler handler = null;
    private Thread thread;

    private static String strEnvWorkSpace = VConstants.DEFAULT_WORK_SPACE;
    private String commonRes = strEnvWorkSpace + ACTIVE_RES;


    private SnowboyDetect detector = null;
    private MediaPlayer player = null;
    private MediaPlayer sPlayer = null;



    public RecordingThread(Handler handler) {
        this(handler, VConstants.activeModel());
    }

    public RecordingThread(Handler handler, String activeModel) {
        this.handler = handler;

        detector = new SnowboyDetect(commonRes, activeModel);

        player = new MediaPlayer();
        sPlayer = new MediaPlayer();

        Log.i("ahmad", "activeModel :" + activeModel);

        detector.SetSensitivity(VConstants.sensitivity(0.49f));
        detector.SetAudioGain(1);
        detector.ApplyFrontend(true);
        try {
            player.setDataSource(strEnvWorkSpace + "ding.wav");
            player.prepare();
        } catch (IOException e) {
            Log.e("ahmad", "Playing ding sound error", e);
        }
    }

    private void sendMessage(MsgEnum what, Object obj) {
        if (null != handler) {
            Message msg = handler.obtainMessage(what.ordinal(), obj);
            handler.sendMessage(msg);
        }
    }

    public void startRecording() {
        if (thread != null)
            return;

        shouldContinue = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                record();
            }
        });
        thread.start();
    }

    public void stopRecording() {
        if (thread == null)
            return;

        shouldContinue = false;
        thread = null;
    }

    private void record() {
        Log.v("ahmad", "Start Recording");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        // Buffer size in bytes: for 0.1 second of audio
        int bufferSize = (int) (VConstants.SAMPLE_RATE * 0.1 * 2);
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = VConstants.SAMPLE_RATE * 2;
        }

        byte[] audioBuffer = new byte[bufferSize];
        AudioRecord record = new AudioRecord(
                MediaRecorder.AudioSource.DEFAULT,
                VConstants.SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e("ahmad", "Audio Record can't initialize!");
            return;
        }
        record.startRecording();

        Log.v("ahmad", "Start recording");

        long shortsRead = 0;
        detector.Reset();
        while (shouldContinue) {
            record.read(audioBuffer, 0, audioBuffer.length);


            // Converts to short array.
            short[] audioData = new short[audioBuffer.length / 2];
            ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioData);

            shortsRead += audioData.length;

            // Snowboy hotword detection.
            int result = detector.RunDetection(audioData, audioData.length);

            if (result == -2) {
                // post a higher CPU usage:
                // sendMessage(MsgEnum.MSG_VAD_NOSPEECH, null);
            } else if (result == -1) {
                sendMessage(MsgEnum.MSG_ERROR, "Unknown Detection Error");
            } else if (result == 0) {
                // post a higher CPU usage:
                // sendMessage(MsgEnum.MSG_VAD_SPEECH, null);
            } else if (result > 0) {
                sendMessage(MsgEnum.MSG_ACTIVE, VModels.get(result-1));
                Log.i("ahmad", "Hotword " + Integer.toString(result) + " detected! " + VModels.get(result-1).name());
                player.start();
            }
        }

        record.stop();
        record.release();


        Log.v("ahmad", String.format("Recording stopped. Samples read: %d", shortsRead));
    }
}
