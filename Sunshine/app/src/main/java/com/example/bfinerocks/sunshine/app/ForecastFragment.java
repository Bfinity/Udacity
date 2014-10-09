package com.example.bfinerocks.sunshine.app;

/**
 * Created by BFineRocks on 10/7/14.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    private static final String LOG_TAG = "JSon";


    ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        ListView forecastListView = (ListView) rootView.findViewById(R.id.list_view_forecast);
        forecastListView.setAdapter(mForecastAdapter);




        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.

        inflater.inflate(R.menu.forecastfragment, menu);


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
        if(id == R.id.action_refresh)
        {
            new FetchWeatherTask().execute("48228");
        }
        return super.onOptionsItemSelected(item);
    }


    public static class FetchWeatherTask extends AsyncTask<String, Void, String>
    {
        private final String LOG_TAG  = FetchWeatherTask.class.getSimpleName();
        @Override
        protected String doInBackground(String... string) {

            // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String mode = "json";
            String units = "metric";
            int numOfDays = 7;

           try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

               final String URL_BASE = "http://api.openweathermap.org/data/2.5/forecast/daily?";
               final String QUERY_PARAM = "q";
               final String QUERY_MODE = "mode";
               final String QUERY_UNITS = "units";
               final String QUERY_COUNT = "cnt";

                Uri aBuilder = Uri.parse(URL_BASE).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, string[0])
                        .appendQueryParameter(QUERY_MODE, mode)
                        .appendQueryParameter(QUERY_UNITS, units)
                        .appendQueryParameter(QUERY_COUNT, Integer.toString(numOfDays))
                        .build();


                URL url = new URL(aBuilder.toString());

 /*              Uri.Builder uriBuilt = new Uri.Builder();
               uriBuilt.scheme("http").authority("api.openweathermap.org").appendPath("data")
                       .appendPath("2.5").appendPath("forecast").appendPath("daily")
                       .appendQueryParameter("q", string[0]).appendQueryParameter("mode", "json")
                       .appendQueryParameter("units", "metric").appendQueryParameter("cnt", "7");

               String urlString = uriBuilt.build().toString();

               URL url = new URL(urlString);*/

               /*"http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7"*/

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                   return null;
                }
                forecastJsonStr = buffer.toString();
               Log.v(LOG_TAG, "JSON Informoation" + forecastJsonStr);
            }
            catch (IOException e){
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return null;
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
            return forecastJsonStr;
        }


    }
}





