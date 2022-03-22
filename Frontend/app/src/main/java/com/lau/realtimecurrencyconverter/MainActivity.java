package com.lau.realtimecurrencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView result;
    double  amount,rate, result_amount;
    String amount_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        result = (TextView) findViewById(R.id.result);
        rate = 22000;
    }

    public void toLbp(View view){

        String amount_str  = input.getText().toString();

        if(amount_str.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_LONG).show();
        }
        else{
            amount = Double.parseDouble(amount_str);
            result_amount = amount*rate;

            result.setText(""+result_amount);
        }
    }


}