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
    String userID;

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

        TwitterInterface myInterface2 = myClient.getUserConService();
        UserTwitterResponse responseUserID = myInterface2.getUserCredentials(null);
        userID = responseUserID.getUser().getUserID();
        Log.v(LOG_TAG, "USERID: " + userID);

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

        add:
        for (Object e : a) {
            i++;
            final Object post = e;
            ParseQuery<ParseObject> userQuery = new ParseQuery("TwitterUser");
            //String id = ((Map.Entry<String, Integer>) e).getKey();
//            userQuery.whereEqualTo("userName", id)
//                    .findInBackground(new FindCallback<ParseObject>() {
//                        public void done(List<ParseObject> objects, ParseException ex) {
                            //if (ex == null) {
                                String userName = ((Map.Entry<String, Integer>) post).getKey();
                                //int rank = ((Map.Entry<String, Integer>) post).getValue();
                                UtilityTwitter.AddUserToParseTwitter addUserTask = new UtilityTwitter()
                                        .new AddUserToParseTwitter(userName, i, userID);
                                addUserTask.execute();
                            //}
                        //}
                    //});
            if (i == pos) break add;
        }
    }

    // deletes entries of database named entry
//    public void deleteEntries(String entry){
//        ParseQuery<ParseObject> userQuery = new ParseQuery(entry);
//        UserTwitterResponse userTwitter = new UserTwitterResponse();
//        String userID = userTwitter.getUser().getUserID();
//        userQuery.whereEqualTo("userID", userID);
//        userQuery.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> objects, ParseException ex) {
//                if (objects != null) {
//                    for(ParseObject p : objects){
//                        p.deleteInBackground();
//                        p.saveInBackground();
//                    }
//                }
//            }
//        });
//    }
}
