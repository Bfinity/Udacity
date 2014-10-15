package com.example.bfinerocks.sunshine.app.data;

import android.provider.BaseColumns;

/**
 * Created by BFineRocks on 10/14/14.
 */
public class WeatherContract {

    public static final class LocationEntry implements BaseColumns{
        /*
        must contain a city name, contain the table name and name of each colmn
         */

        public static final String TABLE_NAME = "location";
        public static final String COLUMN_CITY_NAME = 'city_name';
        public static final String COLUMN_LOCATION_SETTING = "location_setting";
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";
    }

  public static final class WeatherEntry implements BaseColumns{
      public static final String TABLE_NAME = "weather";
      public static final String COLUMN_LOC_KEY = "location_id";
      public static final String COLUMN_DATETEXT = "date";
      public static final String COLUMN_WEATHER_ID = "weather_id";
      public static final String COLUMN_SHORT_DESC = "short_desc";
      public static final String COLUMN_MIN_TEMP = "min";
      public static final String COLUMN_MAX_TEMP = "max";
      public static final String COLUMN_HUMIDITY = "humidity";
      public static final String COLUMN_PRESSURE = "pressure";
      public static final String COLUMN_WIND_SPEED = "wind";
      public static final String COLUMN_DEGREES = "degrees";

  }

}
