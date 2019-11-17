

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MySql {
    // Listener defined earlier
    public interface OnDataLoadListener {

        public void onDataLoaded(String data);
    }

    // Member variable was defined earlier
    private OnDataLoadListener listener;
    private Context context;
    private String PHP_URL;
    private HashMap<String, String> hashMap;
    private boolean SHOW_PROGRESS;

    // Constructor where listener events are ignored
    public MySql(Context mContext, String url, HashMap<String, String> map, boolean 
show_progress) {
        // set null or default listener or accept as argument to constructor
        this.listener = null;
        this.context = mContext;
        this.PHP_URL = url;
        this.hashMap = map;
        this.SHOW_PROGRESS = show_progress;
        checkInternet();

    }

    // Assign the listener implementing events interface that will receive the events
    public void setOnDataLoadListener(OnDataLoadListener listener) {
        this.listener = listener;
    }

    private void loadDataAsync() {

        class LoadDataAsync extends AsyncTask<Void, Void, String> {

            private ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (SHOW_PROGRESS)
                    loading = ProgressDialog.show(context, "", "Please wait...", false, 
false);
            }

            @Override
            protected String doInBackground(Void... params) {

                //Creating a URL
                URL url;

                //StringBuilder object to store the message retrieved from the server
                StringBuilder sb = new StringBuilder();
                try {
                    //Initializing Url
                    url = new URL(PHP_URL);

                    //Creating an httmlurl connection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    //Configuring connection properties
                    conn.setReadTimeout(60000);
                    conn.setConnectTimeout(60000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);//download
                    conn.setDoOutput(true);//upload

                    //Creating an output stream
                    OutputStream os = conn.getOutputStream();

                    //Writing parameters to the request
                    //We are using a method getPostDataString which is defined below
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(hashMap));

                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader br = new BufferedReader(new InputStreamReader
(conn.getInputStream()));
                        sb = new StringBuilder();
                        String response;
                        //Reading server response
                        while ((response = br.readLine()) != null) {
                            sb.append(response);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return sb.toString();

            }

            @Override
            protected void onPostExecute(String json_string) {
                super.onPostExecute(json_string);

                if (SHOW_PROGRESS)
                    loading.dismiss();

                if (json_string.equals("")) {
                    Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show();
                } else {

                    // Now let's trigger the event
                    if (listener != null) {
                        listener.onDataLoaded(json_string); // <---- fire listener here
                    }

                }
            }

        }
        LoadDataAsync loadDataAsync = new LoadDataAsync();
        loadDataAsync.execute();
    }

    private String getPostDataString(HashMap<String, String> params) throws 
UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");


            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private void checkInternet() {
        if (isNetworkAvailable()) {
            loadDataAsync();
        } else {
            Toast.makeText(context, "There is no internet available", 
Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) 
context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
