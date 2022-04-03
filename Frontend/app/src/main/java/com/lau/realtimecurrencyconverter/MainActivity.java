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

    //Initializing instance Variables
    EditText input;
    TextView value, error,rate_text;
    ImageView logo1, logo2;
    double rate,input_value, converted_value;
    String post_url, lbp, usd, input_str,last_rate = "";
    DecimalFormat formatter;
    Spinner spinner;

    PostRequest post = new PostRequest();

    public class DownloadTask extends AsyncTask<String, Void, String> {
        // This class contains methods that enable url connection to an API to retrieve data stored in it.

        protected String doInBackground(String... urls){
            //The method takes String parameter and gets a required data from an external URL API.
            String result = "";
            URL url;
            HttpURLConnection http; //Initializing the url connection object

            try{
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection(); //Declaring the Url connection object

                InputStream inputStream = http.getInputStream(); //initializing InputStream Object to pass data.

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); //Initializing BufferedReader Object to Read data.
                String line = reader.readLine(); //Get the data ad store it in a String.

                while( line  != null){

                    result += line;
                    line = reader.readLine(); //Concatenate each line
                }

            }catch(Exception e){
                Log.i("exeDOin",e.getMessage());
                last_rate = "23000";
                return null;
            }
            return result;
        }

        protected void onPostExecute(String s) {
            // This method converts the JSON Object received into a String.
            super.onPostExecute(s);
            rate_text.setText("1$ = " + last_rate+" LBP");
            try{

                Log.i("String", s);
                JSONObject obj = new JSONObject(s);
                String current_rate = obj.getString("buy");

                String[] rates = current_rate.split("]");

                String[] last_string = rates[rates.length-1].substring(2).split(",");

                last_rate = last_string[1];

                Log.i("Rate", last_rate);

                rate_text.setText("1$ = " + last_rate+" LBP");


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

        logo1 = (ImageView) findViewById(R.id.logo);
        logo2 = (ImageView) findViewById(R.id.entered);


        //Creating arrayList for the Spinner
        ArrayList<String> conversion_list = new ArrayList<String>();
        conversion_list.add("LBP to USD");
        conversion_list.add("USD to LBP");

        //Assigning an adapter and the list as dropdown
        ArrayAdapter<String> my_adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, conversion_list);
        spinner.setAdapter(my_adapter);

        String url = "https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP";
        post_url = "http://192.168.1.139/Mobile%20Computing/Team%20Project/Backend/rates.php";


        DownloadTask task = new DownloadTask();
        task.execute(url);

    }

    public void convert(View view){
        rate = Double.parseDouble(last_rate);
        String conversion_type = spinner.getSelectedItem().toString();
        input_str = input.getText().toString();
        if(input_str.isEmpty()){
            error.setVisibility(View.VISIBLE);
        }else{
            error.setVisibility(View.GONE);
            input_value = Double.parseDouble(input_str);
            switch (conversion_type){
                case "LBP to USD":
                    converted_value = (input_value/rate);
                    lbp = input_str;
                    usd = "" + converted_value;
                    logo1.setImageResource(R.drawable.img);
                    logo2.setImageResource(R.drawable.lbp);
                    value.setText("" + formatter.format(converted_value) + " USD");
                    break;
                case "USD to LBP":
                    converted_value = input_value*rate;
                    usd = input_str;
                    lbp = "" + converted_value;
                    logo1.setImageResource(R.drawable.lbp);
                    logo2.setImageResource(R.drawable.img);
                    value.setText("" + formatter.format(converted_value) + " LBP");
                    break;
            }
            post = new PostRequest();
            post.execute(last_rate, conversion_type, lbp, usd, post_url);
        }

    }



}