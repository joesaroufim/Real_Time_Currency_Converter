package com.lau.realtimecurrencyconverter;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequest extends AsyncTask<String, Void, String> {

    String result;

    @Override
    protected String doInBackground(String... params) {
        String data = params[0];
        String str_url = params[1];
        try {
            URL url = new URL(str_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream out = urlConnection.getOutputStream();
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            String post_data = URLEncoder.encode("data", "UTF-8")+"="+URLEncoder.encode(data, "UTF-8");
            br.write(post_data);
            br.flush();
            br.close();
            out.close();

            urlConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
