package com.appspot.glancesocial.glance;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yueqizhang on 8/5/15.
 */
public class FetchInstaPostTask extends AsyncTask<Void, Void, Void> {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = FetchInstaPostTask.class.getSimpleName();

    static Context mContext = null;

    public FetchInstaPostTask(Context context){
        mContext = context;
    }

    static long addUser(String userID, int rank) {
        long userId;
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder builtUri = new StringBuilder();
        BufferedReader reader = null;
        String userName = null;
        String proPic = null;
        JSONObject user;

        Cursor userCursor = mContext.getContentResolver().query(
                InstagramContract.UserEntry.CONTENT_URI,
                new String[]{InstagramContract.UserEntry._ID},
                InstagramContract.UserEntry.COLUMN_USER_ID + " = ?",
                new String[]{userID},
                null);

        builtUri.append(InstagramService.INSTA_BASE_URL)
                .append("users/")
                .append(userID);

        if (userCursor.moveToFirst()) {
            int userIdIndex = userCursor.getColumnIndex(InstagramContract.UserEntry._ID);
            userId = userCursor.getLong(userIdIndex);
        } else {
           ContentValues userValues = new ContentValues();

            try {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return -1;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                user = new JSONObject(buffer.toString());
                userName = user.getString("username");
                proPic = user.getString("profile_picture");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

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


    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
