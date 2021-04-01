package com.example.citiesnearlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.JSONException;

public class CityDetailActivity extends AppCompatActivity {

    private int cityId;
    private ShareActionProvider shareActionProvider;
    private boolean isDarkModeOn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        // Enable Action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Dark Mode Saving and Setting
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        cityId = getIntent().getExtras().getInt("cityId");
        System.out.println(cityId);

        //CityDetailFragment frag = (CityDetailFragment) getSupportFragmentManager().findFragmentById(R.id.activity_city_detail_fragment);
        //frag.test();
        //frag.setCityId(cityId);
        //System.out.println(frag.cityId);


        // Get TextView IDs to edit later
        TextView cityName = (TextView) findViewById(R.id.cityName);
        TextView cityRegion = (TextView) findViewById(R.id.cityRegion);
        TextView cityLongitude = (TextView) findViewById(R.id.cityLongitude);
        TextView cityLatitude = (TextView) findViewById(R.id.cityLatitude);
        TextView cityDistance = (TextView) findViewById(R.id.cityDistance);

        // Set TextViews with corresponding data
        try {
            cityName.setText("City: " + LocationActivity.fullCityArray.get(cityId).getString("city"));
            cityRegion.setText("Region: " + LocationActivity.fullCityArray.get(cityId).getString("region"));
            cityLongitude.setText("Longitude: " + LocationActivity.fullCityArray.get(cityId).getString("longitude"));
            cityLatitude.setText("Latitude: " +LocationActivity.fullCityArray.get(cityId).getString("latitude"));
            cityDistance.setText("Distance: " +LocationActivity.fullCityArray.get(cityId).getString("distance") + " miles");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // CODE FOR ACTION BAR - Same as other classes
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

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
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

            case R.id.app_bar_help:
                Intent helpIntent = new Intent(CityDetailActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



}