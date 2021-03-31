package com.example.citiesnearlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CityDetailActivity extends AppCompatActivity {

    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        cityId = getIntent().getExtras().getInt("cityId");
        System.out.println(cityId);

        CityDetailFragment frag = (CityDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_city_detail);
        //frag.setCityId(cityId);


    }
}