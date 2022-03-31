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
    TextView result, error;
    double rate, input_value, converted_value;
    String input_str;


    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            return null;
        }

        protected void onPostExecute(String s) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        result = (TextView) findViewById(R.id.result);
        error = (TextView) findViewById(R.id.error_msg);

        String url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t202233113";

        DownloadTask task = new DownloadTask();
        task.execute(url);
    }

    public void toLbp(View view){
        input_str = input.getText().toString();


        if(input_str.isEmpty()){
            error.setVisibility(View.VISIBLE);
        }
        else{
            error.setVisibility(View.GONE);
            input_value = Double.parseDouble(input_str);
            converted_value = input_value * rate;
            result.setText("" + converted_value + " LBP");
        }
    }

    public void toUsd(View view){
        input_str = input.getText().toString();

        if(input_str.isEmpty()){
            error.setVisibility(View.VISIBLE);
        }
        else{
            error.setVisibility(View.GONE);
            input_value = Double.parseDouble(input_str);
            converted_value = input_value / rate;
            result.setText("" + converted_value + " $");
        }
    }


}