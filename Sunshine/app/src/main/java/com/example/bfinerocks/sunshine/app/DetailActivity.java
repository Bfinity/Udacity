package com.example.bfinerocks.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {

    String weatherToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent settingsActivity = new Intent (DetailActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private String forecast;
        private static final String FORECAST_HASHTAG = "#SunshineApp";
        private ShareActionProvider mShareActionProvider;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Intent receivedInfo = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(receivedInfo != null && receivedInfo.hasExtra(Intent.EXTRA_TEXT)){
                forecast = receivedInfo.getStringExtra(Intent.EXTRA_TEXT).toString();}
            ((TextView) rootView.findViewById(R.id.frag_detail)).setText(forecast);


            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragment, menu);

            MenuItem shareItem = menu.findItem(R.id.share_selection);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

            if(mShareActionProvider != null){
                mShareActionProvider.setShareIntent(setShare());
            }
            else{
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }


        }

        public Intent setShare()
        {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                String weatherShare = forecast + FORECAST_HASHTAG;
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, weatherShare);

            return shareIntent;
        }




    }
}
