package com.appspot.glancesocial.glance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class LoginPage extends ActionBarActivity {

    public static final String INSTA_ID = "83f264e6915b4dceb166f6a0962dd57b";
    public static final String INSTA_REDIRECT = "http://glancesocial.appspot.com";
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "otG6EvQxlUAQmxqmdRmnsOKBi";
    private static final String TWITTER_SECRET = "Eh7kzCtbQHCY0Oxg5E60t0BgSzRLIMZRvthdgZEesAa2m96dY7";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.login_page);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                // TODO:
                    // Here we need to get the User's Favorites and Retweets and determine who
                    // they interact with the most. Twitter's API may actually be able to tell
                    // us who they interact with the most without us having to do any analysis
                // TODO:
                    // We need to take the list of the relevant users and store it in a database
                    // A simple database like the one we used in Sunshine will work
                // TODO:
                    // After this is all accomplished we keep them on the login page.
                    // We need to let them add all of their accounts and when they are finished
                    // They can press a continue button that will send them to the main page.
                    // There we will load the relevant posts using the information
                    // from the database as a filter for the posts.
                Context context = getApplicationContext();
                CharSequence text = "Login success. Welcome to Glance.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                // TODO:
                    // On failure we will simply notify the user that the authentication did
                    // not go through and they need to try again. Using a Toast will work.
                Context context = getApplicationContext();
                CharSequence text = "Login failed. Please try again later.";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // TODO:
                // Send them to the settings page. The settings page should allow them to manage
                // accounts and should allow them to adjust their preferences like how much
                // relevant information to show.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
