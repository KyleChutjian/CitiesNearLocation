package com.example.citiesnearlocation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityListFragment extends ListFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ResultActivity resultActivity;

    public CityListFragment() {
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        resultActivity.setCityOnClick(position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        resultActivity = (ResultActivity) context;
    }

    public static CityListFragment newInstance(String param1, String param2) {
        CityListFragment fragment = new CityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] from = {"cityName"};
        int[] to = {R.id.cityName};

        ArrayList<JSONObject> newArray = LocationActivity.fullCityArray;
        System.out.println(newArray.size());

        ResultActivity.cities = new Cities();
        ResultActivity.cities.setAdaptorList(newArray);
        System.out.println("2nd SET ADAPTOR LIST");


        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), ResultActivity.cities.getAdaptorList(),R.layout.activity_city_detail,from,to);
        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}