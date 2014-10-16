package com.example.bfinerocks.sunshine.app.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by BFineRocks on 10/15/14.
 */
public class WeatherProvider extends ContentProvider {

    //content://com.example.bfinerocks.sunshine.app/weather
    private static final int WEATHER = 100;

    //content://com.example.bfinerocks.sunshine.app/weather/[location_query]
    private static final int WEATHER_WITH_LOCATION = 101;

    //content://com.example.bfinerocks.sunshine.app/weather/[location_query]/[date]
    private static final int WEATHER_WITH_LOCATION_AND_DATE = 102;

    //content://com.example.bfinerocks.sunshine.app/location
    private static final int LOCATION = 300;

    //content://com.example.bfinerocks.sunshine.app/location/[location_id]
    private static final int LOCATION_ID = 301;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private WeatherDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher(){


    final String authority = WeatherContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, WeatherContract.PATH_WEATHER, WEATHER);
        uriMatcher.addURI(authority, WeatherContract.PATH_WEATHER +"/*", WEATHER_WITH_LOCATION);
        uriMatcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/*", WEATHER_WITH_LOCATION_AND_DATE);
        uriMatcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);
        uriMatcher.addURI(authority, WeatherContract.PATH_LOCATION + "/#", LOCATION_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
