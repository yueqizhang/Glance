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
    private static final String PARSE_APPLICATION_ID = "6O4jMqC1ayeD7s5ndDh2i3x2iKu0xSVjiQS1xLHn";
    private static final String PARSE_CLIENT_KEY = "MYCqitNiiuUpHiEHi1VcIA4tOjWiR42Ilc4rSrzY";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }
}
