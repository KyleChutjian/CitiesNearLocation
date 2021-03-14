package com.example.citiesnearlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int radius = Integer.valueOf(getIntent().getStringExtra("radius"));
        double latitude = getIntent().getDoubleExtra("latitude",0);
        double longitude = getIntent().getDoubleExtra("longitude",0);
        System.out.println("Radius: " + radius);
        System.out.println("LA: " + latitude);
        System.out.println("LO: " + longitude);

    }
}