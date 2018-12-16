package sy.edu.au.nodemcu;

import android.util.Log;


public class WirelessCommunicator {

    private static String ipAddress = "http://192.168.1.40";


    public static void setIpAddress(String ipAddress) {
        WirelessCommunicator.ipAddress = ipAddress.endsWith("/") ? ipAddress : ipAddress + "/";
    }

    public static String getIpAddress() {
        return ipAddress;
    }


    public static void sendCommand(String command) {
        Log.d("ahmad", "sendCommand (" + command + ")");
        String url = ipAddress + command;
        new HttpGetRequest().execute(url);
    }

}
