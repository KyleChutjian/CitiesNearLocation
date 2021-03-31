package com.example.citiesnearlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

public class CityDetailActivity extends AppCompatActivity {

    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        cityId = getIntent().getExtras().getInt("cityId");
        System.out.println(cityId);

        CityDetailFragment frag = (CityDetailFragment) getSupportFragmentManager().findFragmentById(R.id.activity_city_detail_fragment);
        //frag.test();
        //frag.setCityId(cityId);
        //System.out.println(frag.cityId);

        TextView cityName = (TextView) findViewById(R.id.cityName);
        TextView cityRegion = (TextView) findViewById(R.id.cityRegion);
        TextView cityLongitude = (TextView) findViewById(R.id.cityLongitude);
        TextView cityLatitude = (TextView) findViewById(R.id.cityLatitude);
        TextView cityDistance = (TextView) findViewById(R.id.cityDistance);

        try {
            cityName.setText(LocationActivity.fullCityArray.get(cityId).getString("city"));
            cityRegion.setText(LocationActivity.fullCityArray.get(cityId).getString("region"));
            cityLongitude.setText(LocationActivity.fullCityArray.get(cityId).getString("longitude"));
            cityLatitude.setText(LocationActivity.fullCityArray.get(cityId).getString("latitude"));
            cityDistance.setText(LocationActivity.fullCityArray.get(cityId).getString("distance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}