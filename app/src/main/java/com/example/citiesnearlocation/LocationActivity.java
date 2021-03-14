package com.example.citiesnearlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ShareActionProvider shareActionProvider;
    private double latitude = 0;
    private double longitude = 0;

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


}