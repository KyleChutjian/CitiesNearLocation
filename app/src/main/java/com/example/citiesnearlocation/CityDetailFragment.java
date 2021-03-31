package com.example.citiesnearlocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityDetailFragment extends Fragment {

    public int cityId = -1;

    public CityDetailFragment() {}

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater,container,savedInstanceState);
//    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void test() {

    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("test");
        View view = getView();
        if (view != null && cityId != -1) {
            TextView city = (TextView) view.findViewById(R.id.cityName);
            try {
                city.setText(LocationActivity.fullCityArray.get(cityId).getString("city"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }





}