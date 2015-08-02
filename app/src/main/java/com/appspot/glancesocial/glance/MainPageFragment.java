package com.appspot.glancesocial.glance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageFragment extends Fragment{
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = MainPageFragment.class.getSimpleName();

    public MainPageFragment() {
    }

    // TODO:
    // This only will fill in the usernames with dummy data.
    // We have to create a custom adapter to fill in all of our data.

    ArrayAdapter<String> mPostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the GridView.
        String[] userNames = {
                "Jonah", "Christina",
                "Daisy", "Fran",
                "Jake", "Ethan",
                "Courtney", "Shawn",
                "Nicole", "Ross",
                "Phillip", "Carla",
                "Molli", "Erik"
        };
        List<String> users = new ArrayList<String>(Arrays.asList(userNames));

        mPostAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.card, // The name of the layout ID.
                        R.id.user_name, // The ID of the textview to populate.
                        users);

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_posts);
        gridView.setAdapter(mPostAdapter);

        return rootView;
    }

}
