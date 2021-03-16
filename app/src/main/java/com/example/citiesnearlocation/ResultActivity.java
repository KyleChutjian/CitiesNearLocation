package com.example.citiesnearlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ResultActivity extends AppCompatActivity {

    private String[] cities = {"Test","Test2","Test3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int radius = Integer.valueOf(getIntent().getStringExtra("radius"));
        double latitude = getIntent().getDoubleExtra("latitude",0);
        double longitude = getIntent().getDoubleExtra("longitude",0);
        String latitudeLongitudeInput = null;
        if (longitude < 0) {
            latitudeLongitudeInput = latitude + "" + longitude;
        } else {
            latitudeLongitudeInput = latitude + "+" + longitude;
        }
        System.out.println(latitudeLongitudeInput);


        //TODO: Input latitudeLongitudeInput and radius into GeoDB


        cities = new String[]{"Radius: " + radius, "Latitude: " + latitude, "Longitude: " + longitude};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}