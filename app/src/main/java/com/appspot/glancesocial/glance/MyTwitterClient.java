package com.appspot.glancesocial.glance;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

/**
 * Created by fbueti on 8/11/15.
 */
public class MyTwitterClient extends TwitterApiClient {

    public MyTwitterClient(Session session) {
        super(session);
    }

    public TwitterInterface getFavoriteListService() {
        return  getService(TwitterInterface.class);
    }

}