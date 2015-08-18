package com.appspot.glancesocial.glance;

import android.app.Application;

import com.parse.Parse;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by fbueti on 8/11/15.
 */
public class GlanceApp extends Application {

    private static final String TWITTER_KEY = "otG6EvQxlUAQmxqmdRmnsOKBi";
    private static final String TWITTER_SECRET = "Eh7kzCtbQHCY0Oxg5E60t0BgSzRLIMZRvthdgZEesAa2m96dY7";
    private static final String PARSE_APPLICATION_ID = "t7aZ9iBlFtU4JOXtvm2F3Hb5A0zqOXh2uysEeZ3X";
    private static final String PARSE_CLIENT_KEY = "mUp7lGpKhfhHOuJ0DZCjvJOzDt8wQouo1wWsRHM9";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }
}
