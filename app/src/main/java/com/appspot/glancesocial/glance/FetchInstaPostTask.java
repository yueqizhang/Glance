package com.appspot.glancesocial.glance;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by yueqizhang on 8/5/15.
 */
public class FetchInstaPostTask extends AsyncTask<Void, Void, Void> {

    final String LOG_TAG = FetchInstaPostTask.class.getSimpleName();
    final Context mContext;

    public FetchInstaPostTask(Context context){
        mContext = context;
    }

    long addUser(String userName, long userID, String proPic, int rank) {
        long userId;

        Cursor userCursor = mContext.getContentResolver().query(
                InstagramContract.UserEntry.CONTENT_URI,
                new String[]{InstagramContract.UserEntry._ID},
                InstagramContract.UserEntry.COLUMN_USER_NAME + " = ?",
                new String[]{userName},
                null);

        if (userCursor.moveToFirst()) {
            int userIdIndex = userCursor.getColumnIndex(InstagramContract.UserEntry._ID);
            userId = userCursor.getLong(userIdIndex);
        } else {
           ContentValues userValues = new ContentValues();

            userValues.put(InstagramContract.UserEntry.COLUMN_USER_NAME, userName);
            userValues.put(InstagramContract.UserEntry.COLUMN_USER_ID, userID);
            userValues.put(InstagramContract.UserEntry.COLUMN_PROFILE_PIC, proPic);
            userValues.put(InstagramContract.UserEntry.COLUMN_FRIEND_RANK, rank);

            Uri insertedUri = mContext.getContentResolver().insert(
                    InstagramContract.UserEntry.CONTENT_URI,
                    userValues
            );
            userId = ContentUris.parseId(insertedUri);
        }

        userCursor.close();
        return userId;
    }

    private void getPostDataFromJson(String postJsonStr, String userName) throws JSONException {
        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.

        // user information
        final String OWM_NAME = "user";
        final String OWM_CITY_NAME = "name";
        final String OWM_COORD = "coord";

        // Location coordinate
        final String OWM_LATITUDE = "lat";
        final String OWM_LONGITUDE = "lon";

        // Weather information.  Each day's forecast info is an element of the "list" array.
        final String OWM_LIST = "list";

        final String OWM_PRESSURE = "pressure";
        final String OWM_HUMIDITY = "humidity";
        final String OWM_WINDSPEED = "speed";
        final String OWM_WIND_DIRECTION = "deg";

        // All temperatures are children of the "temp" object.
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";
        final String OWM_WEATHER_ID = "id";

        try {
            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            JSONObject cityJson = forecastJson.getJSONObject(OWM_CITY);
            String cityName = cityJson.getString(OWM_CITY_NAME);

            JSONObject cityCoord = cityJson.getJSONObject(OWM_COORD);
            double cityLatitude = cityCoord.getDouble(OWM_LATITUDE);
            double cityLongitude = cityCoord.getDouble(OWM_LONGITUDE);

            long locationId = addLocation(locationSetting, cityName, cityLatitude, cityLongitude);

            // Insert the new weather information into the database
            Vector<ContentValues> cVVector = new Vector<ContentValues>(weatherArray.length());

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            for(int i = 0; i < weatherArray.length(); i++) {
                // These are the values that will be collected.
                long dateTime;
                double pressure;
                int humidity;
                double windSpeed;
                double windDirection;

                double high;
                double low;

                String description;
                int weatherId;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay+i);

                pressure = dayForecast.getDouble(OWM_PRESSURE);
                humidity = dayForecast.getInt(OWM_HUMIDITY);
                windSpeed = dayForecast.getDouble(OWM_WINDSPEED);
                windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION);

                // Description is in a child array called "weather", which is 1 element long.
                // That element also contains a weather code.
                JSONObject weatherObject =
                        dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);
                weatherId = weatherObject.getInt(OWM_WEATHER_ID);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                high = temperatureObject.getDouble(OWM_MAX);
                low = temperatureObject.getDouble(OWM_MIN);

                ContentValues weatherValues = new ContentValues();

                weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, locationId);
                weatherValues.put(WeatherEntry.COLUMN_DATE, dateTime);
                weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, humidity);
                weatherValues.put(WeatherEntry.COLUMN_PRESSURE, pressure);
                weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, windSpeed);
                weatherValues.put(WeatherEntry.COLUMN_DEGREES, windDirection);
                weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, high);
                weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, low);
                weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, description);
                weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, weatherId);

                cVVector.add(weatherValues);
            }

            // add to database
            int inserted = 0;
            if ( cVVector.size() > 0 ) {
                // Student: call bulkInsert to add the weatherEntries to the database here
                ContentValues[] cVArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cVArray);
                inserted = mContext.getContentResolver().bulkInsert(WeatherEntry.CONTENT_URI, cVArray);

            }

            Log.d(LOG_TAG, "FetchWeatherTask Complete. " + inserted + " Inserted");


        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


}
