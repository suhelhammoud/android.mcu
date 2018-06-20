package sy.edu.au.nodemcu;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

public class WirelessCommunicator {

    private static String ipAddress = "http://192.168.1.103:3000/";

    /*
    final private static ResponseHandlerInterface responseHandler = new ResponseHandlerInterface() {
        @Override
        public void sendResponseMessage(HttpResponse response) throws IOException {

        }

        @Override
        public void sendStartMessage() {

        }

        @Override
        public void sendFinishMessage() {

        }

        @Override
        public void sendProgressMessage(long bytesWritten, long bytesTotal) {

        }

        @Override
        public void sendCancelMessage() {

        }

        @Override
        public void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBody) {

        }

        @Override
        public void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }

        @Override
        public void sendRetryMessage(int retryNo) {

        }

        @Override
        public URI getRequestURI() {
            return null;
        }

        @Override
        public void setRequestURI(URI requestURI) {

        }

        @Override
        public Header[] getRequestHeaders() {
            return new Header[0];
        }

        @Override
        public void setRequestHeaders(Header[] requestHeaders) {

        }

        @Override
        public boolean getUseSynchronousMode() {
            return false;
        }

        @Override
        public void setUseSynchronousMode(boolean useSynchronousMode) {

        }

        @Override
        public boolean getUsePoolThread() {
            return false;
        }

        @Override
        public void setUsePoolThread(boolean usePoolThread) {

        }

        @Override
        public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

        }

        @Override
        public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

        }

        @Override
        public Object getTag() {
            return null;
        }

        @Override
        public void setTag(Object TAG) {

        }
    };
    */
    public static void setIpAddress(String ipAddress) {
        WirelessCommunicator.ipAddress = ipAddress.endsWith("/") ? ipAddress : ipAddress + "/";
    }

    public static String getIpAddress() {
        return ipAddress;
    }

//    private static AsyncHttpClient client = new AsyncHttpClient();

//    public static void sendCommand2(String command) {
//        client.get(ipAddress + command, responseHandler);
//    }

    public static void sendCommand(String command) {
        Log.d("suhel", "sendCommand (" + command + ")");
        String url = ipAddress + command;
        new HttpGetRequest().execute(url);
//        httpGetRequest.doInBackground(url);
    }

}
