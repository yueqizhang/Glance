package com.appspot.glancesocial.glance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Jonah on 8/1/15.
 */
public class TwitterActivity extends ActionBarActivity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = TwitterActivity.class.getSimpleName();

    private static final String TWITTER_KEY = "otG6EvQxlUAQmxqmdRmnsOKBi";
    private static final String TWITTER_SECRET = "Eh7kzCtbQHCY0Oxg5E60t0BgSzRLIMZRvthdgZEesAa2m96dY7";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.twitter_auth);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            @Override
            public void success(Result<TwitterSession> result) {
                Context context = getApplicationContext();
                CharSequence text = "Login success. Twitter added.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Update that the user has added twitter
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.twitter_added), "true");
                editor.apply();
                // Calling finish instead of an intent so you don't see the login screen
                // again when you press the back button
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Context context = getApplicationContext();
                CharSequence text = "Login failed. Please try again later.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Update that the user has not added twitter
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.twitter_added), "false");
                editor.apply();
                // Calling finish instead of an intent so you don't see the login screen
                // again when you press the back button
                finish();
            }
        });
        loginButton.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
