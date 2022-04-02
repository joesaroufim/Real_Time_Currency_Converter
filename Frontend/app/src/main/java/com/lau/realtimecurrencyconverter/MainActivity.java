package com.lau.realtimecurrencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView value, error, print;
    double rate, input_value, converted_value;
    String input_str;
    String last_rate = "";

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream inputStream = http.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();

                while( line  != null){

                    result += line;
                    line = reader.readLine();
                }

            }catch(Exception e){
                Log.i("exeDOin",e.getMessage());
                return null;
            }
            return result;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{

                Log.i("String", s);
                JSONObject obj = new JSONObject(s);
                String syarafa_rate = obj.getString("buy");

                String[] rates = syarafa_rate.split("]");

                String[] last_string = rates[rates.length-1].substring(2).split(",");

                last_rate = last_string[1];

                Log.i("Rate", last_rate);
                print.setText(last_rate);


            }catch(Exception e){
                Log.i("exeOnPost",e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        value = (TextView) findViewById(R.id.result);
        print = (TextView) findViewById(R.id.print);
        error = (TextView) findViewById(R.id.error_msg);

        String url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP";

        DownloadTask task = new DownloadTask();
        task.execute(url);

    }

    public void toLbp(View view){
        rate = Double.parseDouble(last_rate);
        input_str = input.getText().toString();


        if(input_str.isEmpty()){
            error.setVisibility(View.VISIBLE);
        }
        else{
            error.setVisibility(View.GONE);
            input_value = Double.parseDouble(input_str);
            converted_value = input_value * rate;
            value.setText("" + converted_value + " LBP");
        }
    }

    public void toUsd(View view){
        rate = Double.parseDouble(last_rate);
        input_str = input.getText().toString();

        if(input_str.isEmpty()){
            error.setVisibility(View.VISIBLE);
        }
        else{
            error.setVisibility(View.GONE);
            input_value = Double.parseDouble(input_str);
            converted_value = input_value / rate;
            value.setText("" + converted_value + " $");
        }
    }


}