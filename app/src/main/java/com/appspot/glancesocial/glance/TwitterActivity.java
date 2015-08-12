package com.appspot.glancesocial.glance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by Jonah on 8/1/15.
 */
public class TwitterActivity extends Activity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = TwitterActivity.class.getSimpleName();


    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_auth);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {

            SharedPreferences sharedPref = getSharedPreferences("accountsAdded",Context.MODE_PRIVATE);
            @Override
            public void success(Result<TwitterSession> result) {

                //Twitter.getApiClient().getListService().
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
                Log.v(LOG_TAG, "hello");

                Intent testIntent2 = new Intent(context, TwitterService.class);
                startService(testIntent2);

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
        Log.v(LOG_TAG, "In onActivityResult: \nresultCode: " + resultCode + "\nrequestCode = " + requestCode + "\ndata: " + data);
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
