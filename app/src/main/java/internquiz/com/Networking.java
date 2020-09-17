package internquiz.com;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class Networking extends AsyncTask<URL, Void, String> {
    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) urls[0].openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in).toString();
        }
        catch (IOException e)
        {
            Log.e("Error", e.toString());
        }
        return null;
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
