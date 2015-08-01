package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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

    private final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        // Currently the user will see a list of different accounts they can add
        //TODO:
                // We need to do one of two things:
                    // 1) Each list item be a button that sends them
                    //    to the authorization page for that account
                    // OR
                    // 2) Each list item when selected will send them
                    //    to a unique individual page for that account

        ArrayAdapter<String> mAccountAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Create some dummy data for the ListView.
            String[] acc = {
                    "Twitter",
                    "Instagram",
                    "Facebook",
                    "Google+"
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
                    String clickedAccount = mAccountAdapter.getItem(position);
                    Intent intent = new Intent(getActivity(), AccountActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, clickedAccount);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }
}
