package sy.edu.au.nodemcu;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class VConstants {

    public static String[] models = {
            "left",
            "right",
            "forward",
            "backward",
            "stop",
            "one",
            "two",
            "three",
            "four",
            "five"

    };

    public static String activeModel(String model) {
        return DEFAULT_WORK_SPACE + "commands/" + model + ".pmdl";
    }

    public static String activeModel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < models.length; i++) {
            String model = models[i];
            sb.append(activeModel(model));
            if (i < models.length - 1) {
                sb.append(",");
            }

        }
        Log.d("suhel", "activeModels : " + sb.toString());
        return sb.toString();
    }

    public static String sensitivity(float level) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < models.length; i++) {

            sb.append(Float.valueOf(level + i * 0.01f));
            if (i < models.length - 1) {
                sb.append(",");
            }

        }
        Log.d("suhel", "sensitivity : " + sb.toString());
        return sb.toString();
//        return "0.5";
    }


    public static final String ASSETS_RES_DIR = "snowboy";

    //    public static final String DEFAULT_WORK_SPACE =
//            Environment.getExternalStorageDirectory().getAbsolutePath() + "/snowboy/";
    public static final String DEFAULT_WORK_SPACE =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/snowboy/";
    public static final String ACTIVE_UMDL = "alexa.umdl";
    public static final String ACTIVE_RES = "common.res";
    public static final String SAVE_AUDIO = VConstants.DEFAULT_WORK_SPACE + File.separatorChar + "recording.pcm";
    public static final int SAMPLE_RATE = 16000;
}
