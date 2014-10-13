package com.example.bfinerocks.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MyActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(MyActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;}

            if(id == R.id.action_viewmap)
            {
                viewLocationOnMap();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    public void viewLocationOnMap()
    {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = mSharedPreferences.getString(getString(R.string.location_key),
                getString(R.string.pref_location_default));
        Uri uriBuilt = new Uri.Builder().scheme("geo").encodedOpaquePart("0,0?=" + location).build();
/*        uriBuilt.scheme("https").authority("www.google.com").appendPath("maps")
                .appendPath("place").appendPath(location).build();*/
 /*       Uri uriBuilt = Uri.parse("geo:0,0?=").buildUpon().appendQueryParameter("q", location).build();*/


        Intent seeLocation = new Intent(Intent.ACTION_VIEW);
        seeLocation.setData(uriBuilt);
        if(seeLocation.resolveActivity(getPackageManager()) != null)
        {
            startActivity(seeLocation);
        }
    }



}
