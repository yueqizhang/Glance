package com.appspot.glancesocial.glance;

/**
 * Created by fbueti on 8/11/15.
 */

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.twitter.sdk.android.Twitter;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TwitterService extends IntentService {
    // Use LOG_TAG when logging anything
    final String LOG_TAG = TwitterService.class.getSimpleName();
    final static String TWITTER_BASE_URL = "https://api.twitter.com/1.1/favorites/list.json?count=100";
    final int MAX_FRIENDS = 10;

    URL url;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String favoriteTweets = null;

    //TODO: make this arraylist into a database
    ArrayList favoriteUsers = new ArrayList<String>();

    public TwitterService() {
        super("TwitterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

//        MyTwitterClient myClient = new MyTwitterClient(Twitter.getSessionManager().getActiveSession());
//        TwitterInterface myInterface = myClient.getFavoriteListService();
//        List<FavoriteResponse> response = myInterface.getFavoritesList(100);

//        ArrayList<String> allUsers = new ArrayList<String>();
//        for (FavoriteResponse f : response) {
//            allUsers.add(f.getUser().getScreenName());
//        }
//
//        Collections.sort(allUsers);
//
//        for (int i=0; i<allUsers.size(); i++){
//            Log.v(LOG_TAG, allUsers.get(i));
//        }
//
//        int i, count;
//        Hashtable<String, Integer> favUsersCount = new Hashtable<String,Integer>();
//
//        for (i = 0; i < allUsers.size()-1; i++) {
//            count = 1;
//            while (allUsers.get(i).equals(allUsers.get(i+1))){
//                count++;
//                i++;
//            }
//            favUsersCount.put(allUsers.get(i), count);
//        }
//
//        Log.v(LOG_TAG, "HASHTABLE: " + favUsersCount.toString());

//        Object[] a = favUsersCount.entrySet().toArray();
//        Arrays.sort(a, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
//                        ((Map.Entry<String, Integer>) o1).getValue());
//            }
//        });

        }



//        ArrayList<String> a = new ArrayList<String>();
//
//        try {
//            InputStream inputStream = response.getBody().in();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                return;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                //buffer.append(line + "\n");
//                a.add(line);
//                Log.v(LOG_TAG, "Line length: " + line.length());
//            }
//
//            favoriteTweets = buffer.toString();
//            JSONObject jObj = new JSONObject(favoriteTweets);
//            JSONArray data = jObj.optJSONArray("data");
//            JSONObject latestFavorite = data.getJSONObject(0);
//            JSONObject latestUser = latestFavorite.getJSONObject("user");
//
//
//        }
//        catch (JSONException e) {
//            Log.e(LOG_TAG, "JSON err: ", e);
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        //Log.v(LOG_TAG, "FAV TWEETS: " + favoriteTweets);
//        Log.v(LOG_TAG, "Number tweets: " + a.size());
//        for (int i = 0; i < a.size(); i++) {
//            Log.v(LOG_TAG, "Fav tweet " + (i+1) + ": " + a.get(i));
//        }

//    public void getLikedUsers() {
//        String newLastLikedID = null;
//        try {
//            Log.v(LOG_TAG, "START OF TRY");
//            url = new URL(TWITTER_BASE_URL);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                return;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
//
//            favoriteTweets = buffer.toString();
//
//            JSONObject jObj = new JSONObject(favoriteTweets);
//            //gets the most recent likes and adds them to list
//            JSONArray data = jObj.optJSONArray("data");
//            JSONObject latestLike = data.getJSONObject(0);
//            newLastLikedID = latestLike.getString("id");
//            JSONObject latestUser = latestLike.getJSONObject("user");
//            Log.v(LOG_TAG, "Latest User: " + latestUser.toString());
//
//            //likedUsers.add(latestUser.getString("id"));
//            for (int i = 1; i < data.length(); i++) {
//                JSONObject imageEntry = data.getJSONObject(i);
//                if(imageEntry.getString("id").equals(newLastLikedID))
//                    break;
//                JSONObject user = imageEntry.getJSONObject("user");
//             //   likedUsers.add(user.getString("id"));
//            }
//            //Log.d(LOG_TAG, likedUsers.toString());
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, "JSON err: ", e);
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            Log.e(LOG_TAG, "malURL err: ", e);
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "IOEx err:", e);
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                Log.e(LOG_TAG, "URL conn");
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e(LOG_TAG, "Error closing stream", e);
//                }
//            }
//        }
//        //lastLikeID = newLastLikedID;
//    }
//
//    long addUser(String userID, int rank) {
//        long userId;
//        URL url;
//        HttpURLConnection urlConnection = null;
//        StringBuilder builtUri = new StringBuilder();
//        BufferedReader reader = null;
//        String userName = null;
//        String proPic = null;
//        JSONObject user;
//
//        Cursor userCursor = this.getContentResolver().query(
//                InstagramContract.UserEntry.CONTENT_URI,
//                new String[]{InstagramContract.UserEntry._ID},
//                InstagramContract.UserEntry.COLUMN_USER_ID + " = ?",
//                new String[]{userID},
//                null);
//
//        builtUri.append(InstagramService.INSTA_BASE_URL)
//                .append("users/")
//                .append(userID);
//
//        if (userCursor.moveToFirst()) {
//            int userIdIndex = userCursor.getColumnIndex(InstagramContract.UserEntry._ID);
//            userId = userCursor.getLong(userIdIndex);
//        } else {
//            ContentValues userValues = new ContentValues();
//
//            try {
//                url = new URL(builtUri.toString());
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    return -1;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                }
//                user = new JSONObject(buffer.toString());
//                userName = user.getString("username");
//                proPic = user.getString("profile_picture");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            userValues.put(InstagramContract.UserEntry.COLUMN_USER_NAME, userName);
//            userValues.put(InstagramContract.UserEntry.COLUMN_USER_ID, userID);
//            userValues.put(InstagramContract.UserEntry.COLUMN_PROFILE_PIC, proPic);
//            userValues.put(InstagramContract.UserEntry.COLUMN_FRIEND_RANK, rank);
//
//            Uri insertedUri = this.getContentResolver().insert(
//                    InstagramContract.UserEntry.CONTENT_URI,
//                    userValues
//            );
//            userId = ContentUris.parseId(insertedUri);
//        }
//
//        userCursor.close();
//        return userId;
//    }
}
