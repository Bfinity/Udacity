package com.example.bfinerocks.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ArrayAdapter<String> mForecastAdapter;
        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            ArrayList<String> fakeForecast;
            fakeForecast = new ArrayList<String>();
            fakeForecast.add("Today - Sunny - 88/63");
            fakeForecast.add("Tomorrow - Rainy - 75/66");
            fakeForecast.add("Monday - Cloudy - 67/60");
            fakeForecast.add("Tuesday - Chance of Meatballs - 80/70");
            fakeForecast.add("Wednesday - Chilly - 50/45");
            fakeForecast.add("Thursday - Hungry - 80/78");


            mForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,
                    R.id.list_item_forecast_textview, fakeForecast);

            ListView forecastListView = (ListView) rootView.findViewById (R.id.list_view_forecast);
            forecastListView.setAdapter(mForecastAdapter);


            return rootView;
        }
    }
}
