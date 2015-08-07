package com.appspot.glancesocial.glance;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yueqizhang on 7/30/15.
 */
public class InstagramService extends IntentService{
    // Use LOG_TAG when logging anything
    final String LOG_TAG = InstagramService.class.getSimpleName();

    final static String INSTA_BASE_URL = "https://api.instagram.com/v1/";
    final String ACCESS = "access_token";
    final int MAX_FRIENDS = 10;
    String lastLikeID = null;
    URL url;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String mediaLiked = null;
    //TODO: make this arraylist into a database
    ArrayList likedUsers = new ArrayList<String>();

    public InstagramService() {
        super("InstagramService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getLikedUsers();
        getBestFriends(likedUsers); //adds best friends to database
    }

    public void getBestFriends(ArrayList<String> likedUsers) {
        Map<String, Integer> usersMap = new HashMap<String, Integer>();
        for (String user : likedUsers) {
            if (usersMap.containsKey(user)) {
                usersMap.put(user, usersMap.get(user) + 1);
            } else {
                usersMap.put(user, 1);
            }
        }

        Object[] a = usersMap.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        int pos = (MAX_FRIENDS < usersMap.size()) ? MAX_FRIENDS : usersMap.size();
        int i = 0;
        add:
            for(Object e : a){
                i++;
                addUser(((Map.Entry<String, Integer>) e).getKey(), ((Map.Entry<String, Integer>) e).getValue());
                if(i == pos) break add;
            }
    }

    public void getLikedUsers() {
        StringBuilder builtUri = new StringBuilder();
        builtUri.append(INSTA_BASE_URL)
                .append("users/self/media/liked?")
                .append(ACCESS + "=")
                .append(InstaWebViewActivity.accessToken);
        String newLastLikedID = null;
        try {
            //TODO: add functionality to go to the next page in JSON
            url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            mediaLiked = buffer.toString();
            JSONObject jObj = new JSONObject(mediaLiked);
            //gets the most recent likes and adds them to list
            JSONArray data = jObj.optJSONArray("data");
            JSONObject latestLike = data.getJSONObject(0);
            newLastLikedID = latestLike.getString("id");
            JSONObject latestUser = latestLike.getJSONObject("user");
            likedUsers.add(latestUser.getString("id"));
            for (int i = 1; i < data.length(); i++) {
                JSONObject imageEntry = data.getJSONObject(i);
                if(imageEntry.getString("id").equals(newLastLikedID))
                    break;
                JSONObject user = imageEntry.getJSONObject("user");
                likedUsers.add(user.getString("id"));
            }
            Log.d(LOG_TAG, likedUsers.toString());
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
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        lastLikeID = newLastLikedID;
    }

    long addUser(String userID, int rank) {
        long userId;
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder builtUri = new StringBuilder();
        BufferedReader reader = null;
        String userName = null;
        String proPic = null;
        JSONObject user;

        Cursor userCursor = this.getContentResolver().query(
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

            Uri insertedUri = this.getContentResolver().insert(
                    InstagramContract.UserEntry.CONTENT_URI,
                    userValues
            );
            userId = ContentUris.parseId(insertedUri);
        }

        userCursor.close();
        return userId;
    }
}
