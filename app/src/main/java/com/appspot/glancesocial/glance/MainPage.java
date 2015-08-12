package com.appspot.glancesocial.glance;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainPage extends ActionBarActivity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = MainPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Glance");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainPageFragment())
                    .commit();
        }
        // Check if the user doesn't have network connection and notify them
        if (! isNetworkAvailable()) {
            CharSequence text = "No Internet Connection";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
        }
        Intent testIntent = new Intent(this, InstagramService.class);
        startService(testIntent);
        Intent testIntent2 = new Intent(this, TwitterService.class);
        startService(testIntent2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the user doesn't have network connection and notify them
        if (! isNetworkAvailable()) {
            CharSequence text = "No Internet Connection";
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
        }
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // We override this function for one reason:
        // When the user is on the main screen and hits the back button
        // they shouldn't be sent to the intro page. The app should close.
        // This function call will finish all activities (Login and Main).
        this.finishAffinity();
    }

    // Gets whether the user has wifi/cellular connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openTwitterClickHandler(View target) {
        //TODO: Send user to the specific tweet
        Uri uri = Uri.parse("http://twitter.com/foreverjonah");
        Intent twitterIntent = new Intent(Intent.ACTION_VIEW, uri);
        twitterIntent.setPackage("com.twitter.android");

        if (isIntentAvailable(getApplicationContext(), twitterIntent)){
            startActivity(twitterIntent);
        } else{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitter.com")));
        }
    }

    public void favoriteTweetClickHandler(View target) {
        //TODO: Favorite the tweet associated to this view
        //https://dev.twitter.com/rest/reference/post/favorites/create
        CharSequence text = "Favorited";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void retweetTweetClickHandler(View target) {
        //TODO: Retweet the tweet associated to this view
        //https://dev.twitter.com/rest/reference/post/statuses/retweet/%3Aid
        CharSequence text = "Retweeted";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void openInstagramClickHandler(View target) {
        //TODO: Send user to the specific user page
        Uri uri = Uri.parse("http://instagram.com/_u/foreverjonah");
        Intent instagramIntent = new Intent(Intent.ACTION_VIEW, uri);
        instagramIntent.setPackage("com.instagram.android");

        if (isIntentAvailable(getApplicationContext(), instagramIntent)){
            startActivity(instagramIntent);
        } else{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com")));
        }
    }

    // Makes sure that the package and intent is legit and available
    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    // Sends the view to fullscreen immersive mode
    @Override
    @TargetApi(19)
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View view = getWindow().getDecorView().findViewById(R.id.gridview_posts);
        if (hasFocus) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }
}

