package sy.edu.au.nodemcu;

import android.util.Log;

import java.util.Arrays;

enum CommandType {
    stop, forward, backward, right, left;

    public String val() {
        switch (this) {
            case forward:
                return "f";
            case backward:
                return "b";
            case right:
                return "r";
            case left:
                return "l";
            default:
                return "s";
        }
    }
}

public class Command {
    public static Long timeThreshold = 1000L;
    public static float threshold = 15f;


    private CommandType currentCommand = CommandType.stop;
    private long commandTimeStamp = 0;

    private boolean isConnected = false;

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void sensorChanged(float x, float y, float z) {
        if (Math.abs(y) >= threshold || Math.abs(z) > threshold) {

            Log.d("sensor", "sensor values: " + Arrays.toString(new float[]{x, y, z}));

            if (Math.abs(y) > Math.abs(z)) {
                setCommand(y > 0 ? CommandType.forward : CommandType.backward);
            } else {
                setCommand(z > 0 ? CommandType.left : CommandType.right);
            }

        } else {
            setCommand(CommandType.stop);

        }
//        Log.d("suhel", "sensor changed " + event.sensor.getName());

    }

    public void setCommand(CommandType cmd) {
        long time = System.currentTimeMillis();

        if (cmd.equals(this.currentCommand)
                && time - commandTimeStamp < timeThreshold)
            return;

        this.currentCommand = cmd;
        commandTimeStamp = time;
        Log.d("command", "send command via the wire " + cmd);
        //send throuhg the wire
        if (isConnected) {

            WirelessCommunicator.sendCommand(currentCommand.val());
        }


    }

}
