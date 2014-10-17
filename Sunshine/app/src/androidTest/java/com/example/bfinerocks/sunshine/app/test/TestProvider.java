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
        String testLocationSetting = "99705";
        String testCityName = "North Pole";
        double testLatitude = 64.7488;
        double testLongitude = -147.353;
        //If there is an error in db string it will be thrown here.

        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Create a new map of values, where column names are the keys

        ContentValues values = new ContentValues();
        values.put(LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(LocationEntry.COLUMN_CITY_NAME, testCityName);
        values.put(LocationEntry.COLUMN_COORD_LAT, testLatitude);
        values.put(LocationEntry.COLUMN_COORD_LONG, testLongitude);

        long locationRowId;
        locationRowId = db.insert(LocationEntry.TABLE_NAME, null, values);


        //Data's inserted. In theory.
        //specify which columns you want.

        String[] columns = {
                LocationEntry._ID,
                LocationEntry.COLUMN_LOCATION_SETTING,
                LocationEntry.COLUMN_CITY_NAME,
                LocationEntry.COLUMN_COORD_LAT,
                LocationEntry.COLUMN_COORD_LONG
        };


        //A cursor is your primary interface to query results.

        Cursor cursor = db.query(
                LocationEntry.TABLE_NAME, // table to query
                columns,
                null, // columns for the "where" clause
                null, // values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );


        //If possible, move to the first row of the query results.
        if(cursor.moveToFirst()){

            //Get the value in each column by finding the appropriate column index by name.
            int locationIndex = cursor.getColumnIndex(LocationEntry.COLUMN_LOCATION_SETTING);
            String location = cursor.getString(locationIndex);

            int nameIndex = cursor.getColumnIndex(LocationEntry.COLUMN_CITY_NAME);
            String name = cursor.getString(nameIndex);

            int latIndex = cursor.getColumnIndex(LocationEntry.COLUMN_COORD_LAT);
            double latitude = cursor.getDouble(latIndex);

            int longIndex = cursor.getColumnIndex(LocationEntry.COLUMN_COORD_LONG);
            double longitude = cursor.getDouble(longIndex);

            //data should be returned. Assert that it's the right data

            assertEquals(testCityName, name);
            assertEquals(testLocationSetting, location);
            assertEquals(testLatitude, latitude);
            assertEquals(testLongitude, longitude);
        }
        else{
            fail("No values returned :(");
        }

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

        if(!weatherCursor.moveToFirst()){
            fail("No weather data returned!");
        }

            int colLocKeyIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_LOC_KEY);
            int colLocKey = weatherCursor.getInt(colLocKeyIndex);

            int dateIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_DATETEXT);
            String date = weatherCursor.getString(dateIndex);

            int degreesIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_DEGREES);
            double degrees = weatherCursor.getDouble(degreesIndex);

            int humidityIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_HUMIDITY);
            double humidity = weatherCursor.getDouble(humidityIndex);

            int pressureIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_PRESSURE);
            double pressure = weatherCursor.getDouble(pressureIndex);

            int maxIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_MAX_TEMP);
            double max = weatherCursor.getDouble(maxIndex);

            int minIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_MIN_TEMP);
            double min = weatherCursor.getDouble(minIndex);

            int descIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_SHORT_DESC);
            String description = weatherCursor.getString(descIndex);

            int windIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_WIND_SPEED);
            double wind = weatherCursor.getDouble(windIndex);

            int idIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_WEATHER_ID);
            int id = weatherCursor.getInt(idIndex);

            assertEquals(locationRowId, colLocKey);
            assertEquals("20141205", date);
            assertEquals(1.1, degrees);
            assertEquals(1.2, humidity);
            assertEquals(1.3, pressure);
            assertEquals(75.0, max);
            assertEquals(65.0, min);
            assertEquals("Asteroids", description);
            assertEquals(5.5, wind);
            assertEquals(321, id);

        weatherCursor.close();
        dbHelper.close();

    }

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
