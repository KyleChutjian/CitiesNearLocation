package com.example.citiesnearlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;

public class CityDetailFragment extends AppCompatActivity {

    private ArrayList<JSONObject> fullCityArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail_fragment);
        fullCityArray = LocationActivity.fullCityArray;
    }

    public void fragmentClicked(View view) {
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("City", 5);
        bundle.putString("CityName", "City");
        fragment.setArguments(bundle);
        System.out.print(fullCityArray);

    }
}