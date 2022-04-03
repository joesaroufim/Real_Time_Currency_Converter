package com.lau.realtimecurrencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandingPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }

    public void launch (View view){
        // This method redirect te user to the MainActivity page.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); //Creating an Intent object.
        startActivity(intent); //Calling this method to activate the intent and change page.
    }
}