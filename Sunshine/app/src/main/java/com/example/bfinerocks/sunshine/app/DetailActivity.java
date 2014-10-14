package com.example.bfinerocks.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {
    private ShareActionProvider mShareActionProvider;
    public static String forecast;
    String weatherToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);

        MenuItem shareItem = menu.findItem(R.id.share_selection);
        mShareActionProvider = (ShareActionProvider)shareItem.getActionProvider();
        setShareIntent();

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

    public void setShareIntent()
    {
        if(mShareActionProvider != null){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String weatherShare = forecast + "#SunshineApp";
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, weatherShare);
        mShareActionProvider.setShareIntent(shareIntent);}

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ShareActionProvider mShareActionProvider;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            Intent receivedInfo = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if(receivedInfo != null && receivedInfo.hasExtra(Intent.EXTRA_TEXT)){
                DetailActivity.forecast = receivedInfo.getStringExtra(Intent.EXTRA_TEXT).toString();}
            ((TextView) rootView.findViewById(R.id.frag_detail)).setText(forecast);


            return rootView;
        }






    }
}
