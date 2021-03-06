package com.example.bfinerocks.sunshine.app.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bfinerocks.sunshine.app.ApplicationTest;
import com.example.bfinerocks.sunshine.app.data.WeatherContract.LocationEntry;
import com.example.bfinerocks.sunshine.app.data.WeatherContract.WeatherEntry;
import com.example.bfinerocks.sunshine.app.data.WeatherDbHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by BFineRocks on 10/15/14.
 */
public class TestDb extends ApplicationTest {
    public static final String LOG_TAG = ApplicationTest.class.getSimpleName();
    public void testCreateDb() throws Throwable{
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();

    }

    public void testInsertReadDb()
    {
        //If there is an error in db string it will be thrown here.

        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Create a new map of values, where column names are the keys

        ContentValues values = new ContentValues();


        long locationRowId;
        locationRowId = db.insert(LocationEntry.TABLE_NAME, null, values);

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
        validateCursor(cursor, values);

        ContentValues weatherValues = new ContentValues();

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

       validateCursor(weatherCursor, weatherValues);

        dbHelper.close();

    }

    static ContentValues createWeatherValues(long locationRowId){
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, locationRowId);
        weatherValues.put(WeatherEntry.COLUMN_DATETEXT, "20141205");
        weatherValues.put(WeatherEntry.COLUMN_DEGREES, 1.1);
        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, 1.2);
        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, 1.3);
        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, 75);
        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, 65);
        weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, 321);

        return weatherValues;
    }
    static ContentValues createNorthPoleLocationValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(LocationEntry.COLUMN_LOCATION_SETTING, "99705");
        testValues.put(LocationEntry.COLUMN_CITY_NAME, "North Pole");
        testValues.put(LocationEntry.COLUMN_COORD_LAT, 64.7488);
        testValues.put(LocationEntry.COLUMN_COORD_LONG, -147.353);

        return testValues;
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }
}
