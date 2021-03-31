package com.example.citiesnearlocation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), new Cities().getAdaptorList(),R.layout.activity_city_detail,from,to);
        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }
}