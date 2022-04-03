package com.lau.realtimecurrencyconverter;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        //The method take String parameters and send data to the received url.

        //Storing data in String objects
        String rate = params[0];
        String conversion_type = params[1];
        String lbp = params[2];
        String usd = params[3];
        String str_url = params[4];

        try {
            // Creating a new URL connection with PHP.
            URL url = new URL(str_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream out = urlConnection.getOutputStream(); //Initializing OutputStream Object.

            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out, "UTF-8")); //Initializing BufferedWriter Object

            // Setting the variables to be sent to the URL
            String post_data = URLEncoder.encode("rate", "UTF-8")+"="+URLEncoder.encode(rate, "UTF-8")+"&"
                    +URLEncoder.encode("conversion", "UTF-8")+"="+URLEncoder.encode(conversion_type, "UTF-8")+"&"
                    +URLEncoder.encode("lbp", "UTF-8")+"="+URLEncoder.encode(lbp, "UTF-8")+"&"
                    +URLEncoder.encode("usd", "UTF-8")+"="+URLEncoder.encode(usd, "UTF-8");

            br.write(post_data); //Writing and sending data.
            br.flush();
            br.close();
            out.close();

            InputStream is = urlConnection.getInputStream();

            urlConnection.disconnect();

            //Catching exceptions
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
