package com.lau.realtimecurrencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView value, error, print, rate_text;
    ImageView logo;
    double rate, input_value, converted_value;
    String post_url, lbp, usd, input_str,last_rate = "";
    DecimalFormat formatter;
    Spinner spinner;

    PostRequest post = new PostRequest();

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
                String current_rate = obj.getString("omt");

                String[] rates = current_rate.split("]");

                String[] last_string = rates[rates.length-1].substring(2).split(",");

                last_rate = last_string[1];

                Log.i("Rate", last_rate);

                rate_text.setText(last_rate+" LBP");


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
        error = (TextView) findViewById(R.id.error_msg);
        rate_text = (TextView) findViewById(R.id.rate);
        spinner = (Spinner) findViewById(R.id.spinner);

        formatter = new DecimalFormat(".##");

        logo = (ImageView) findViewById(R.id.logo);
        logo.setTranslationY(-3000);

        //Creating arrayList for the Spinner
        ArrayList<String> conversion_list = new ArrayList<String>();
        conversion_list.add("LBP to USD");
        conversion_list.add("USD to LBP");

        //Assigning an adapter and the list as dropdown
        ArrayAdapter<String> my_adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, conversion_list);
        spinner.setAdapter(my_adapter);

        String url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t20224312";
        post_url = "http://192.168.0.101/Mobile%20Computing/Team%20Project/Backend/rates.php";


        DownloadTask task = new DownloadTask();
        task.execute(url);

    }

    public void convert(View view){
//        rate = Double.parseDouble(last_rate);
        rate = 20000;
        String conversion_type = spinner.getSelectedItem().toString();
        input_str = input.getText().toString();
        if(input_str.isEmpty()){
            error.setVisibility(View.VISIBLE);
        }else{
            error.setVisibility(View.GONE);
            input_value = Double.parseDouble(input_str);
            switch (conversion_type){
                case "LBP to USD":
                    converted_value = input_value/rate;
                    lbp = input_str;
                    usd = "" + converted_value;
                    logo.setImageResource(R.drawable.img);
                    break;
                case "USD to LBP":
                    converted_value = input_value*rate;
                    usd = input_str;
                    lbp = "" + converted_value;
                    logo.setImageResource(R.drawable.lbp);
                    break;
            }
            value.setText("" + formatter.format(converted_value));
            logo.animate().translationYBy(3000).rotation(3600).setDuration(600);
            post.execute(last_rate, conversion_type, lbp, usd, post_url);
        }

    }



}