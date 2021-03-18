package com.example.citiesnearlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*

*  Developed by: Kyle Chutjian & Max Petruzziello
*  Date Submitted: 3/18/2021
*  CitiesNearLocation Application

 */

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<String> cityArrayList = new ArrayList<String>();
    private GoogleMap mMap;
    private ShareActionProvider shareActionProvider;
    private double latitude = 0;
    private double longitude = 0;
    private String url1 = "https://wft-geo-db.p.rapidapi.com/v1/geo/locations/";
    private String url2 = "/nearbyCities";
    private Intent intent;
    private boolean isDarkModeOn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // create action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Dark Mode Saving and Setting
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Map fragment creation
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // find button creation
        Button findCitiesButton = findViewById(R.id.button);
        findCitiesButton.setOnClickListener(new View.OnClickListener() {

            // called on Map click - when user selects location
            @Override
            public void onClick(View v) {
                EditText inputRadius = findViewById(R.id.inputRadius);
                intent = new Intent(LocationActivity.this,ResultActivity.class);
                if(inputRadius.getText().toString().trim().isEmpty() == true) {
                    Toast.makeText(LocationActivity.this,"Click on the map and enter a radius before continuing.", Toast.LENGTH_LONG).show();
                 } else if (latitude != 0) {
                    String latitudeLongitudeInput = null;
                    if (longitude < 0) {
                        latitudeLongitudeInput = latitude + "" + longitude;
                    } else {
                        latitudeLongitudeInput = latitude + "+" + longitude;
                    }
                    new getNearbyCities().execute(latitudeLongitudeInput,inputRadius.getText().toString());
                } else {
                    Toast.makeText(LocationActivity.this,"Click on the map and enter a radius before continuing.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // initial map
    public void onMapReady(GoogleMap googleMap) {

        // create map with camera focus near Hamden, CT
        mMap = googleMap;
        LatLng hamden = new LatLng(41.3839, -72.9026);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hamden));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            // called when map is clicked, stores new location values
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    // initialize Action Bar options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem helpItem = menu.findItem(R.id.app_bar_help);
        MenuItem colorItem = menu.findItem(R.id.app_bar_color);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem)menu.findItem(R.id.action_share));
        setShareActionIntent("Use this app to find cities near any location!");
        return super.onCreateOptionsMenu(menu);
    }

    // Set share intent for share button
    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    // called for corresponding options on click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // share intent when share button is clicked
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Check out this cool app!");
                shareActionProvider.setShareIntent(intent);
                break;

                // change app theme to dark mode or to day mode
            case R.id.app_bar_color:
                if(isDarkModeOn == false) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn",true);
                    editor.apply();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn",false);
                    editor.apply();
                }
                break;

                // open Help screen
            case R.id.app_bar_help:
                Intent helpIntent = new Intent(LocationActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    // called when find cities button is clicked - if radius or location not yet input, toast pops up
    public void findCities(View view) {
        Intent intent = new Intent(LocationActivity.this,ResultActivity.class);
        EditText inputRadius = findViewById(R.id.inputRadius);

        if (latitude != 0) {
            intent.putExtra("radius",inputRadius.getText().toString());
            intent.putExtra("longitude",longitude);
            intent.putExtra("latitude",latitude);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Click on the map and enter a radius before clicking this.", Toast.LENGTH_LONG).show();
        }

    }

    // called if no cities are found nearby
    public void oceanAlert(){
        Toast.makeText(this,"There are no cities near your selected location. Please increase your radius (up to 100) or choose a new location if no cities are found.", Toast.LENGTH_LONG).show();
    }

    // background thread to retrieve from RESTAPI
    private class getNearbyCities extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // if no cities are found, call oceanAlert to tell the user - does not open cities result
            if (cityArrayList.size() == 0){
                oceanAlert();
                return;
            }
            // else, if cities are found, open results!
            else {
                intent.putStringArrayListExtra("cityArray",cityArrayList);
                startActivity(intent);
            }


        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                String latitudeLongitudeInput = null;
                if (longitude < 0) {
                    latitudeLongitudeInput = latitude + "" + longitude;
                } else {
                    latitudeLongitudeInput = latitude + "+" + longitude;
                }

                // Opens a connection to the database
                URL url = new URL(url1 + latitudeLongitudeInput + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key", "e4c0313cc4msh924249226a4e9bcp187dc6jsnc0721a4eaa04");
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                if (in==null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(in));
                getStringFromBuffer(reader);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return null;
        }
    }

    // retrieves Array of cities given values for api
    public ArrayList<String> getStringFromBuffer(BufferedReader bufferedReader) {
        cityArrayList = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer();
        String line;

        if (bufferedReader != null) {
            try {
                while((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + '\n');
                }
                bufferedReader.close();
                // retrieves city data in the form of a JSON Object, converts to JSON Array
                JSONObject JSONObj = new JSONObject(buffer.toString());
                JSONArray jsonArray = JSONObj.getJSONArray("data");

                // Loops through entire JSON Array, adding each city value to the cityArrayList
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject JSONObject = new JSONObject(jsonArray.get(i).toString());
                    cityArrayList.add(JSONObject.getString("city"));
                }
                return cityArrayList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }
}

