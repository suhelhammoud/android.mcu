package sy.edu.au.nodemcu;

import android.os.Environment;

import java.io.File;

public class VConstants {

    public static String[] models = {
//            "alexa",
            "left",
            "right",
            "forward",
            "backward",
            "stop"
    };

    public static String activeModel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < models.length; i++) {
            String model = models[i];
            sb.append(DEFAULT_WORK_SPACE + "commands/" +  model + ".pmdl");
            if (i < models.length - 1) {
                sb.append(", ");
            }

        }
        return sb.toString();
    }

    public static String sensitivity(String level){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < models.length; i++) {

            sb.append(level);
            if (i < models.length - 1) {
                sb.append(", ");
            }

        }
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
