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
<<<<<<< HEAD
=======

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
                    "Instagram",
                    "Twitter - Coming Soon",
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
                        case (0): // Instagram
                            // Checks if you have gave instagram permission
                            if (sharedPref.getString(getString(R.string.instagram_added), "false").equals("true")) {
                                // Sends the user to the account Management Page
                                String clickedAccount = mAccountAdapter.getItem(position);
                                Intent intent = new Intent(getActivity(), AccountActivity.class)
                                        .putExtra(Intent.EXTRA_TEXT, clickedAccount);
                                startActivity(intent);
                            } else {
                                // Sends you to the instagram Authentication Page
                                Intent intentInstagram = new Intent(getActivity(), InstaWebViewActivity.class);
                                intentInstagram.putExtra("SettingsActivity", true);
                                startActivity(intentInstagram);
                            }
                            break;
                        case (1): // Twitter
                            // Checks if you have gave twitter permission
//                            if (sharedPref.getString(getString(R.string.twitter_added), "false").equals("true")) {
//                                // Sends the user to the account Management Page
//                                String clickedAccount = mAccountAdapter.getItem(position);
//                                Intent intent = new Intent(getActivity(), AccountActivity.class)
//                                        .putExtra(Intent.EXTRA_TEXT, clickedAccount);
//                                startActivity(intent);
//                            } else {
//                                // Sends you to the twitter Authentication Page
//                                Intent intentTwitter = new Intent(getActivity(), TwitterActivity.class);
//                                intentTwitter.putExtra("SettingsActivity", true);
//                                startActivity(intentTwitter);
//                            }
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
>>>>>>> master
}