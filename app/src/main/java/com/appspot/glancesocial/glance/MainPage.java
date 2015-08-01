package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainPage extends ActionBarActivity {
    private final String LOG_TAG = MainPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
        Intent testIntent = new Intent(this, InstagramService.class);
        startService(testIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainFragment extends Fragment {

        // TODO:
            // This only will fill in the usernames with dummy data.
            // We have to create a custom adapter to fill in all of our data.

        ArrayAdapter<String> mPostAdapter;

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Create some dummy data for the ListView.
            String[] userNames = {
                    "Jonah",
                    "Christina",
                    "Daisy",
                    "Fran",
                    "Jake",
                    "Ethan",
                    "Courtney",
                    "Shawn",
                    "Nicole",
                    "Ross"
            };
            List<String>  users = new ArrayList<String>(Arrays.asList(userNames));

            mPostAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.card, // The name of the layout ID.
                            R.id.user_name, // The ID of the textview to populate.
                            users);

            View rootView = inflater.inflate(R.layout.main_fragment, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.listview_posts);
            listView.setAdapter(mPostAdapter);

            return rootView;
        }
    }
}
