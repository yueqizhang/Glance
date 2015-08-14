package com.appspot.glancesocial.glance;

/**
 * Created by fbueti on 8/11/15.
 */

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
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
import android.os.Handler;
import java.util.concurrent.ExecutionException;

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


public class TwitterService extends IntentService {
    // Use LOG_TAG when logging anything
    final String LOG_TAG = TwitterService.class.getSimpleName();
    final static String TWITTER_BASE_URL = "https://api.twitter.com/1.1/favorites/list.json?count=100";
    final int MAX_FRIENDS = 10;
    final Context context = this;
    ArrayList<String> orderedFavUsers = new ArrayList<String>();
    int z;
    Handler mainHandler;
    Runnable myRunnable;
    int g;

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

        for (Object e : a) {
            String id = ((Map.Entry<String, Integer>) e).getKey();
            orderedFavUsers.add(id);
            if (orderedFavUsers.size() == 10){
                break;
            }
        }

        Log.v(LOG_TAG, "orderedFavUsers: " + orderedFavUsers.size());

        for (int j = 0; j < orderedFavUsers.size(); j++) {
            Log.v(LOG_TAG, orderedFavUsers.get(j));
        }

        // Get a handler that can be used to post to the main thread
        final int pos = (MAX_FRIENDS < orderedFavUsers.size()) ? MAX_FRIENDS : orderedFavUsers.size();

        getBestFriends(orderedFavUsers);





//        for (g = 0; g < orderedFavUsers.size(); g++) {
//             ParseQuery<ParseObject> screen_name = new ParseQuery("TwitterUsers");
//            screen_name.whereEqualTo("screen_name", orderedFavUsers.get(g))
//                .findInBackground(new FindCallback<ParseObject>() {
//                    public void done(List<ParseObject> objects, ParseException ex) {
//                        if (ex == null && g < pos) {
//                            UtilityTwitter.AddUserToParseTwitter addUserTask = new UtilityTwitter()
//                                    .new AddUserToParseTwitter(orderedFavUsers.get(g), g);
//                            addUserTask.execute();
//                            Log.v(LOG_TAG, "Runnable #: " + g);
//                        }
//                    }
//                });
//        }


        //mainHandler = new Handler(context.getMainLooper());

//    for (Object e : orderedFavUsers) {
//        g++;
//        final Object post = e;
//        ParseQuery<ParseObject> userQuery = new ParseQuery("TwitterUsers");
//        String id = orderedFavUsers.get(g);
//        userQuery.whereEqualTo("userName", id)
//                .findInBackground(new FindCallback<ParseObject>() {
//                    public void done(List<ParseObject> objects, ParseException ex) {
//                        if (ex == null) {
//                            String userId = orderedFavUsers.get(g);
//                            int rank = g;
//                            UtilityTwitter.AddUserToParseTwitter addUserTask = new UtilityTwitter()
//                                    .new AddUserToParseTwitter(userId, rank);
//                            addUserTask.execute();
//                        }
//                    }
//                });


//        myRunnable = new Runnable() {
//            @Override
//            public void run() {
//                g = 0;
//                int pos = MAX_FRIENDS;
//                try {
//                    g++;
//                    for (z = 0; z < orderedFavUsers.size(); z++) {
//                        if (g < pos) {
//                            Log.v(LOG_TAG, "the #: " + z);
//                            ParseQuery<ParseObject> userQuery = new ParseQuery("TwitterUser");
//                            userQuery.whereEqualTo("userName", orderedFavUsers.get(z))
//                                    .findInBackground(new FindCallback<ParseObject>() {
//                                        public void done(List<ParseObject> objects, ParseException ex) {
//                                            Log.v(LOG_TAG, "error is: " + ex + " num is: " + z);
//                                            if (ex == null) {
//                                                Log.v(LOG_TAG, "a #: " + z);
//                                                UtilityTwitter.AddUserToParseTwitter addUserTask = new UtilityTwitter()
//                                                        .new AddUserToParseTwitter(orderedFavUsers.get(z), z);
//                                                Log.v(LOG_TAG, "a2 #: " + z);
//
//                                                addUserTask.execute();
//                                                Log.v(LOG_TAG, "Runnable #: " + z);
//                                            }
//                                        }
//                                    });
//                        }
//                    };
//                }
//                catch (Exception e) {
//                    Log.v(LOG_TAG, "Doesn't work to add to Twitter database: " + e);
//                }
//            }
//        };
//        mainHandler.post(myRunnable);
    }

    void getBestFriends(ArrayList<String> likedUsers) {
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
//        if(InstaWebViewActivity.getID != null) {
//            try { //waits for thread to finish, if not done
//                InstaWebViewActivity.getID.get();
//                Log.d(LOG_TAG, "Owner ID = " + Utility.ownerID);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        add:
        for (Object e : a) {
            i++;
            final Object post = e;
            ParseQuery<ParseObject> userQuery = new ParseQuery("TwitterUser");
            String id = ((Map.Entry<String, Integer>) e).getKey();
//            userQuery.whereEqualTo("userName", id)
//                    .findInBackground(new FindCallback<ParseObject>() {
//                        public void done(List<ParseObject> objects, ParseException ex) {
                            //if (ex == null) {
                                Log.v(LOG_TAG, "IN HERE" + i);
                                String userId = ((Map.Entry<String, Integer>) post).getKey();
                                int rank = ((Map.Entry<String, Integer>) post).getValue();
                                UtilityTwitter.AddUserToParseTwitter addUserTask = new UtilityTwitter()
                                        .new AddUserToParseTwitter(userId, rank);
                                addUserTask.execute();
                            //}
                        //}
                    //});
            if (i == pos) break add;
        }
    }
}
