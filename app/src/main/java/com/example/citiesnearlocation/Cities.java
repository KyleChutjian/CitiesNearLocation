package com.example.citiesnearlocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cities {

    String cityName,region;
    double longitude,latitude,distance;
    public static Cities[] cities = new Cities[5];
    private List<HashMap<String,String>> adaptorList;

    public Cities() {}

    public Cities(String cityName, String region, double longitude, double latitude, double distance) {
        this.cityName = cityName;
        this.region = region;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public List<HashMap<String,String>> getAdaptorList() {
        System.out.println("------------------------");
        return adaptorList;
    }

    public void setAdaptorList(ArrayList<JSONObject> fullCityArray) {

        System.out.println("SET ADAPTOR LIST START");
        try {
            adaptorList = new ArrayList<HashMap<String, String>>();
            System.out.println("------------------------" + fullCityArray.get(0).getString("city"));
            for (int i = 0; i < fullCityArray.size(); i++) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("cityName", fullCityArray.get(i).getString("city"));
                adaptorList.add(hashMap);

            }
        } catch (JSONException e) {
            System.out.println("-----------------------------");
            e.printStackTrace();
        }


    }

    public String getCityName() {
        return cityName;
    }

    public String getRegion() {
        return region;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getDistance() {
        return distance;
    }




}
