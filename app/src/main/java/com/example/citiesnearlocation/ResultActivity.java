package com.example.citiesnearlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*

 *  Developed by: Kyle Chutjian & Max Petruzziello
 *  Date Submitted: 3/18/2021
 *  CitiesNearLocation Application

 */

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ShareActionProvider shareActionProvider;
    private boolean isDarkModeOn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ArrayList<String> cityArrayList = null;
    ArrayList<JSONObject> fullCityArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

        // retrieve cities
            //cityArrayList = getIntent().getStringArrayListExtra("cityArray");

        // retrieve all information about each city
        fullCityArray = LocationActivity.fullCityArray;
        cityArrayList = new ArrayList<String>();


        try {
            for (int i = 0; i < fullCityArray.size(); i++) {
                System.out.println("\nCity Name: " + fullCityArray.get(i).getString("city") + ". Distance: " + fullCityArray.get(i).getString("distance"));
                cityArrayList.add(fullCityArray.get(i).getString("city"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // populate recycle view
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Adapter(this, cityArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    public void fragmentClicked(View view){
//        Fragment fragment = new Fragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("City", 5);
//        bundle.putString("CityName", "City");
//        fragment.setArguments(bundle);
//
//    }

    // CODE FOR ACTION BAR - Same from LocationActivity
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
                Intent helpIntent = new Intent(ResultActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }




}