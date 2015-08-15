package com.appspot.glancesocial.glance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonah on 8/9/15.
 */
public class AccountFragment extends Fragment {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = AccountFragment.class.getSimpleName();



    public AccountFragment() {
    }

    FriendAdapter mFriendAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.account_fragment, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(intent.EXTRA_TEXT)) {
            String accountName = intent.getStringExtra(intent.EXTRA_TEXT);
            ((TextView) rootView.findViewById(R.id.account_name)).setText(accountName);
        }

        if (AccountActivity.friends == null || AccountActivity.friends.isEmpty()) {
            AccountActivity.friends = new ArrayList<>();
            ParseQuery<ParseObject> query = new ParseQuery("InstagramUser");
            SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String ownerID = sharedPref.getString(getString(R.string.owner_id), "");
            Log.v(LOG_TAG, "Owner ID: " + ownerID);
            query.whereEqualTo("ownerID", ownerID).findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> object, ParseException e) {
                    Post newPost;
                    if (e == null) {
                        for (int i = 0; i < object.size(); i++) {
                            try {
                                Log.v(LOG_TAG, "Adding a user to the list");
                                newPost = new Post(object.get(i).getString("userName"),
                                        object.get(i).getString("userName"),
                                        Uri.parse(object.get(i).getString("profilePic")));
                            } catch (ArrayIndexOutOfBoundsException exc) {
                                //Create empty post
                                exc.printStackTrace();
                                newPost = new Post();
                            }
                            AccountActivity.friends.add(newPost);
                        }
                        updateTheFriendsList();
                    } else {
                        e.printStackTrace();
                        Log.v(LOG_TAG, "object: " + object);
                        newPost = new Post();
                        AccountActivity.friends.add(newPost);
                    }
                }
            });
        }
        Log.v(LOG_TAG, "Friends " + AccountActivity.friends);
        mFriendAdapter =
                new FriendAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.friend_list, // The name of the layout ID.
                        AccountActivity.friends);
        ListView listView = (ListView) rootView.findViewById(R.id.friend_list);
        listView.setAdapter(mFriendAdapter);
        return rootView;
    }

    public void updateTheFriendsList() {
        mFriendAdapter =
                new FriendAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.friend_list, // The name of the layout ID.
                        AccountActivity.friends);
        ListView listView = (ListView) getActivity().findViewById(R.id.friend_list);
        listView.setAdapter(mFriendAdapter);
    }
}
