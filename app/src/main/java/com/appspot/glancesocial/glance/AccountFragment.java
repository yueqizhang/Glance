package com.appspot.glancesocial.glance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Fade mFade;
    private ViewGroup mRootViewGroup;
    public static FriendAdapter mFriendAdapter;

    //Default Constructor
    public AccountFragment() {
    }

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
            // Get the Owner ID so we can access information
            SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String ownerID = sharedPref.getString(getString(R.string.owner_id), "");
            // Create a Parse Query to get the best friends
            ParseQuery<ParseObject> query = new ParseQuery("InstagramUser");
            query.whereEqualTo("ownerID", ownerID).findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> object, ParseException e) {
                    Post newFriend;
                    if (e == null) {
                        // Create a new list item for every best friend they have
                        for (int i = 0; i < object.size(); i++) {
                            try {
                                newFriend = new Post(object.get(i).getString("fullName"),
                                        object.get(i).getString("userName"),
                                        Uri.parse(object.get(i).getString("profilePic")));
                            } catch (ArrayIndexOutOfBoundsException exc) {
                                //Create empty post
                                exc.printStackTrace();
                                newFriend = new Post();
                            }
                            AccountActivity.friends.add(newFriend);
                        }
                        updateTheFriendsList();
                    } else {
                        e.printStackTrace();
                        newFriend = new Post();
                        AccountActivity.friends.add(newFriend);
                    }
                }
            });
        }
        mFriendAdapter =
                new FriendAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.friend_list, // The name of the layout ID.
                        AccountActivity.friends);
        ListView listView = (ListView) rootView.findViewById(R.id.friend_list);
        listView.setAdapter(mFriendAdapter);
        mRootViewGroup = (ViewGroup) rootView.findViewById(R.id.friend_list);
        final Transition mFadeTransition =
                TransitionInflater.from(getActivity()).
                        inflateTransition(R.transition.fade_transition);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Doesn't get the correct handle value
                //Utility.deleteUserInstagram(view.findViewById(R.id.friend_handle).toString(), getActivity());
                //mFade = new Fade(Fade.OUT);
                // Start recording changes to the view hierarchy
                //TransitionManager.beginDelayedTransition(mRootViewGroup, mFade);
                CharSequence text = "Delete User";
                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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
