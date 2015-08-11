package com.appspot.glancesocial.glance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                    .add(R.id.container, new PlaceholderFragment())
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
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ArrayAdapter<String> mAccountAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Create some dummy data for the ListView.
            final String[] acc = {
                    "Twitter",
                    "Instagram",
                    "Facebook - Coming Soon",
                    "Google+ - Coming Soon"
            };
            List<String> accounts = new ArrayList<String>(Arrays.asList(acc));

            mAccountAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.settings_account, // The name of the layout ID.
                            R.id.settings_account_textview, // The ID of the textview to populate.
                            accounts);

            View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.listview_accounts);
            listView.setAdapter(mAccountAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //TODO: Use getSharedPreferences() instead of getPreferences()
                    SharedPreferences sharedPref =  getActivity().getSharedPreferences("accountsAdded", Context.MODE_PRIVATE);
                    switch (position) {
                        case (0): // Twitter
                            if (sharedPref.getString(getString(R.string.twitter_added), "false").equals("true")) {
                                String clickedAccount = mAccountAdapter.getItem(position);
                                Intent intent = new Intent(getActivity(), AccountActivity.class)
                                        .putExtra(Intent.EXTRA_TEXT, clickedAccount);
                                startActivity(intent);
                            } else {
                                Intent intentTwitter = new Intent(getActivity(), TwitterActivity.class);
                                intentTwitter.putExtra("SettingsActivity", true);
                                startActivity(intentTwitter);
                            }
                            break;
                        case (1): // Instagram
                            if (sharedPref.getString(getString(R.string.instagram_added), "false").equals("true")) {
                                String clickedAccount = mAccountAdapter.getItem(position);
                                Intent intent = new Intent(getActivity(), AccountActivity.class)
                                        .putExtra(Intent.EXTRA_TEXT, clickedAccount);
                                startActivity(intent);
                            } else {
                                Intent intentInstagram = new Intent(getActivity(), InstaWebViewActivity.class);
                                intentInstagram.putExtra("SettingsActivity", true);
                                Log.d(LOG_TAG, "INSTAGRAM called from settings *******");
                                startActivity(intentInstagram);
                            }
                            break;
                        default:
                            //Do nothing for our list of coming soon social media accounts
                            break;
                    }
                }
            });
            return rootView;
        }
    }
}
