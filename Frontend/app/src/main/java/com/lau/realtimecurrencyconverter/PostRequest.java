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

    @Override
    protected String doInBackground(String... params) {
        String rate = params[0];
        String currency = params[1];
        String amount = params[2];
        String str_url = params[3];
        try {
            URL url = new URL(str_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream out = urlConnection.getOutputStream();
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            String post_data = URLEncoder.encode("rate", "UTF-8")+"="+URLEncoder.encode(rate, "UTF-8")+"&"
                    +URLEncoder.encode("currency", "UTF-8")+"="+URLEncoder.encode(currency, "UTF-8")+"&"
                    +URLEncoder.encode("amount", "UTF-8")+"="+URLEncoder.encode(amount, "UTF-8");
            br.write(post_data);
            br.flush();
            br.close();
            out.close();

            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}