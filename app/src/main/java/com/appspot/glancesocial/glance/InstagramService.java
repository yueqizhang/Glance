package com.appspot.glancesocial.glance;

import android.app.IntentService;
import android.content.Intent;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yueqizhang on 7/30/15.
 */
public class InstagramService extends IntentService{
    // Use LOG_TAG when logging anything
    final String LOG_TAG = InstagramService.class.getSimpleName();

    final String INSTA_BASE_URL = "https://api.instagram.com/v1/";
    final String ACCESS = "access_token";
    final int MAX_FRIENDS = 10;
    String lastLikeID = null;
    URL url;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String mediaLiked = null;
    //TODO: make this arraylist into a database
    ArrayList likedUsers = new ArrayList<String>();
    List bestFriends = new ArrayList<String>();

    public InstagramService() {
        super("InstagramService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getLikedUsers();

        bestFriends = getBestFriends(likedUsers);
        Log.d(LOG_TAG, bestFriends.toString());
    }

    public List getBestFriends(ArrayList<String> likedUsers) {
        Map<String, Integer> usersMap = new HashMap<String, Integer>();
        for (String user : likedUsers) {
            if (usersMap.containsKey(user)) {
                usersMap.put(user, usersMap.get(user) + 1);
            } else {
                usersMap.put(user, 1);
            }
        }

        List list = new LinkedList(usersMap.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                return ((Comparable) ((Map.Entry) (obj2)).getValue())
                        .compareTo(((Map.Entry) (obj1)).getValue());
            }
        });
        int pos = (MAX_FRIENDS < list.size()) ? MAX_FRIENDS : list.size();
        List<String> topTen = new ArrayList<String>(list.subList(0, pos));
        return topTen;
    }

    public void getLikedUsers() {
        StringBuilder builtUri = new StringBuilder();
        builtUri.append(INSTA_BASE_URL)
                .append("users/self/media/liked?")
                .append(ACCESS + "=")
                .append(InstaWebViewActivity.accessToken);
        String newLastLikedID = null;
        try {
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
            likedUsers.add(latestUser.getString("username"));
            for (int i = 1; i < data.length(); i++) {
                JSONObject imageEntry = data.getJSONObject(i);
                if(imageEntry.getString("id").equals(newLastLikedID))
                    break;
                JSONObject user = imageEntry.getJSONObject("user");
                likedUsers.add(user.getString("username"));
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
}
