package com.appspot.glancesocial.glance;

/**
 * Created by fbueti on 8/11/15.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.twitter.sdk.android.Twitter;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
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

        MyTwitterClient myClient = new MyTwitterClient(Twitter.getSessionManager().getActiveSession());
        TwitterInterface myInterface = myClient.getFavoriteListService();
        List<FavoriteResponse> response = myInterface.getFavoritesList(100);

        ArrayList<String> allUsers = new ArrayList<String>();
        for (FavoriteResponse f : response) {
            allUsers.add(f.getUser().getScreenName());
        }

        Collections.sort(allUsers);

        for (int i=0; i<allUsers.size(); i++){
            Log.v(LOG_TAG, allUsers.get(i));
        }

        int i, count;
        Hashtable<String, Integer> favUsersCount = new Hashtable<String,Integer>();

        for (i = 0; i < allUsers.size()-1; i++) {
            count = 1;
            while (allUsers.get(i).equals(allUsers.get(i+1))){
                count++;
                i++;
            }
            favUsersCount.put(allUsers.get(i), count);
        }

        Log.v(LOG_TAG, "HASHTABLE: " + favUsersCount.toString());

        Object[] a = favUsersCount.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        ArrayList<String> orderedFavUsers = new ArrayList<String>();
        for (Object e : a) {
            String id = ((Map.Entry<String, Integer>) e).getKey();
            orderedFavUsers.add(id);
            if (orderedFavUsers.size() == 10){
                break;
            }
        }

        Log.v(LOG_TAG, "orderedFavUsers: " + orderedFavUsers.size());

//        for (int j = 0; j < orderedFavUsers.size(); j++) {
//            Log.v(LOG_TAG, orderedFavUsers.get(j));
//        }

//        for (int z = 0; z < orderedFavUsers.size(); z++) {
//            UtilityTwitter.AddUserToParseTwitter addUserTask = new UtilityTwitter()
//                    .new AddUserToParseTwitter(orderedFavUsers.get(z), z);
//            addUserTask.execute();
//        }
    }
}
