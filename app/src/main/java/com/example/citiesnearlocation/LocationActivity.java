package com.example.citiesnearlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.database.CharArrayBuffer;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ShareActionProvider shareActionProvider;
    private double latitude = 0;
    private double longitude = 0;
    private String url1 = "https://numbersapi.p.rapidapi.com/v1/geo/locations/";
    private String url2 = "/nearbyCities";
    //33.832213-118.387099

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
        TextView background = (TextView) findViewById(R.id.backgroundTextview);
        background.setBackgroundColor(Color.argb(255, 225, 226, 232));
        background.setTextColor(Color.argb(255, 225, 226, 232));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button findCitiesButton = findViewById(R.id.button);
        findCitiesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this,ResultActivity.class);
                EditText inputRadius = findViewById(R.id.inputRadius);

                if (latitude != 0) {
                    intent.putExtra("radius",inputRadius.getText().toString());
                    intent.putExtra("longitude",longitude);
                    intent.putExtra("latitude",latitude);
                    startActivity(intent);
                } else {
                    Toast.makeText(LocationActivity.this,"Click on the map and enter a radius before clicking this.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(41.3839, -72.9026);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                System.out.println("---------------------------------");
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                System.out.println(latitude + ", " + longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem helpItem = menu.findItem(R.id.app_bar_help);
        MenuItem colorItem = menu.findItem(R.id.app_bar_color);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem) menu.findItem(R.id.action_share));

        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(text);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
                shareActionProvider.setShareIntent(intent);
                break;

            case R.id.app_bar_color:
                TextView background =(TextView) findViewById(R.id.backgroundTextview);
                if (background.getCurrentTextColor()==Color.argb(255,121, 122, 128 )) {
                    background.setBackgroundColor(Color.argb(255, 225, 226, 232));
                    background.setTextColor(Color.argb(255, 225, 226, 232));
                } else if (background.getCurrentTextColor()==Color.argb(255,225, 226, 232)) {
                    background.setBackgroundColor(Color.argb(255, 121, 122, 128));
                    background.setTextColor(Color.argb(255, 121, 122, 128));
                }
                break;

            case R.id.app_bar_help:
                Intent helpIntent = new Intent(LocationActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

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
    private class getNearbyCities extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String[] cityArray = null;
            try {
                URL url = new URL(url1 + latitude + "-" + longitude + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key", "e4c0313cc4msh924249226a4e9bcp187dc6jsnc0721a4eaa04");
                InputStream in = urlConnection.getInputStream();
                if (in==null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(in));
                

            } catch (Exception e) {
                return null;
            }

            return null;
        }

    }


}

