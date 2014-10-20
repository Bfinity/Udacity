package com.example.bfinerocks.sunshine.app.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bfinerocks.sunshine.app.ApplicationTest;
import com.example.bfinerocks.sunshine.app.data.WeatherContract.LocationEntry;
import com.example.bfinerocks.sunshine.app.data.WeatherContract.WeatherEntry;
import com.example.bfinerocks.sunshine.app.data.WeatherDbHelper;

/**
 * Created by BFineRocks on 10/15/14.
 */
public class TestProvider extends ApplicationTest {
    public static final String LOG_TAG = ApplicationTest.class.getSimpleName();
    public void testDeleteDb() throws Throwable{
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
    }

    public void testInsertReadDb()
    {

        //If there is an error in db string it will be thrown here.

        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Create a new map of values, where column names are the keys

        ContentValues testValues = TestDb.createNorthPoleLocationValues();

        long locationRowId;
        locationRowId = db.insert(LocationEntry.TABLE_NAME, null, testValues);

        assertTrue(locationRowId != -1);


        //A cursor is your primary interface to query results.

        Cursor cursor = db.query(
                LocationEntry.TABLE_NAME, // table to query
                null,
                null, // columns for the "where" clause
                null, // values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        TestDb.validateCursor(cursor, testValues);

        ContentValues weatherValues = TestDb.createWeatherValues(locationRowId);

        long weatherRowId = db.insert(WeatherEntry.TABLE_NAME, null, weatherValues);

        assertTrue(weatherRowId != -1);

        Cursor weatherCursor = db.query(
                WeatherEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

    TestDb.validateCursor(weatherCursor, weatherValues);
        dbHelper.close();}


    public void testGetType(){
        String type = mContext.getContentResolver().getType(WeatherEntry.CONTENT_URI);
        assertEquals(WeatherEntry.CONTENT_TYPE, type);

        String testLocation = "94074";
        type = mContext.getContentResolver().getType(WeatherEntry.buildWeatherLocation(testLocation));
        assertEquals(WeatherEntry.CONTENT_TYPE, type);

        String testDate = "20140612";
        type = mContext.getContentResolver().getType(
                WeatherEntry.buildWeatherLocationWithDate(testLocation, testDate));
        assertEquals(WeatherEntry.CONTENT_TYPE_ITEM, type);

        type = mContext.getContentResolver().getType(LocationEntry.CONTENT_URI);
        assertEquals(LocationEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(LocationEntry.buildLocationUri(1L));
        assertEquals(LocationEntry.CONTENT_ITEM_TYPE, type);
    }
}
