package com.appspot.glancesocial.glance;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by yueqizhang on 7/30/15.
 */
public class InstagramService extends IntentService {
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
    ArrayList likedUsers = new ArrayList<String>();

    public InstagramService() {
        super(InstagramService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getLikedUsers();
        getBestFriends(likedUsers); //adds best friends to database
        getInstagramFeed();
    }

    public void getInstagramFeed() {
        try {
            Log.d(LOG_TAG, "Instagram feed called");
            StringBuilder builtUri = new StringBuilder();
            String feedJsonStr;
            BufferedReader reader;
            builtUri.append(INSTA_BASE_URL)
                    .append("users/self/feed?")
                    .append(ACCESS + "=")
                    .append(InstaWebViewActivity.accessToken);
            String newLastLikedID = null;
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
            feedJsonStr = buffer.toString();
            JSONObject feedJson = new JSONObject(feedJsonStr);
            final JSONArray feedArray = feedJson.getJSONArray("data");

            for (int i = 0; i < feedArray.length(); i++) {
                final int cur = i;
                JSONObject post = feedArray.getJSONObject(i);
                JSONObject user = post.getJSONObject("user");
                ParseQuery postQuery = new ParseQuery("InstagramPosts");
                postQuery.whereEqualTo("userId", user.getString("id")).findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && !objects.isEmpty()) {
                            try {
                                ParseObject user = objects.get(0);
                                String userId = (String) user.get("userId");
                                Utility.AddPostToParse addPost = new Utility()
                                .new AddPostToParse(feedArray.getJSONObject(cur), userId);
                                Log.d(LOG_TAG, "Executing asynctask ***********");
                                addPost.execute();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.stopSelf();
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
        if(InstaWebViewActivity.getID != null) {
            try { //waits for thread to finish, if not done
                InstaWebViewActivity.getID.get();
                Log.d(LOG_TAG, "Owner ID = " + Utility.ownerID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        add:
        for (Object e : a) {
            i++;
            final Object post = e;
            ParseQuery<ParseObject> userQuery = new ParseQuery("InstagramUser");
            String id = ((Map.Entry<String, Integer>) e).getKey();
            Log.d(LOG_TAG, "UserID " + id);
            userQuery.whereEqualTo("userId", id);
            userQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException ex) {
                    if (object == null) {
                        String userId = ((Map.Entry<String, Integer>) post).getKey();
                        int rank = ((Map.Entry<String, Integer>) post).getValue();
                        Utility.AddUserToParse addUserTask = new Utility()
                                .new AddUserToParse(userId, rank);
                        addUserTask.execute();
                    } else {
                        Log.d(LOG_TAG, "Found Objects " + object.toString());
                    }
                }
            });
            if (i == pos) break add;
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
                if (imageEntry.getString("id").equals(newLastLikedID))
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

}
