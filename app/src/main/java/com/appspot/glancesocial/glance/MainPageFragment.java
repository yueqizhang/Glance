package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageFragment extends Fragment{

    public MainPageFragment() {

    }

    // TODO:
    // This only will fill in the usernames with dummy data.
    // We have to create a custom adapter to fill in all of our data.

    ArrayAdapter<String> mPostAdapter;

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
        List<String> users = new ArrayList<String>(Arrays.asList(userNames));

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
