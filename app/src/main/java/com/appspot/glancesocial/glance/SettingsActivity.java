package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Jonah on 7/30/15.
 */
public class SettingsActivity extends ActionBarActivity {
    // Use LOG_TAG when logging anything
    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Glance");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SettingsFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // We override this function for one reason:
        // We always want to send the user back to the main page
        // when they leave the settings page.
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
    }
}