package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        View rootView = inflater.inflate(R.layout.account_fragment, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(intent.EXTRA_TEXT)) {
            String accountName = intent.getStringExtra(intent.EXTRA_TEXT);
            ((TextView) rootView.findViewById(R.id.account_name)).setText(accountName);
        }
        ArrayList<Post> friends = new ArrayList<>();
        String[] userNames = {
                "Jonah Starling", "Christina",
                "Daisy", "Fran",
                "Jake", "Ethan",
                "Courtney", "Shawn",
                "Nicole", "Ross",
                "Phillip", "Carla",
                "Molli", "Erik"
        };
        String[] userHandle = {
                "@ForeverJonah", "@Christina",
                "@Daisy", "@Fran",
                "@Jake", "@Ethan",
                "@Courtney", "@Shawn",
                "@Nicole", "@Ross",
                "@Phillip", "@Carla",
                "@Molli", "@Erik"
        };
        Uri[] userPic = {
                Uri.parse("https://pbs.twimg.com/profile_images/547588061216137216/5CL6N3VO.jpeg"),
                Uri.parse("https://lh5.googleusercontent.com/-egDEIsHX1mM/VPe0HjdymFI/AAAAAAAAAWQ/vqK_Q05F9As/w1840-h1836-no/IMG_3449.jpeg"),
                Uri.parse("https://lh5.googleusercontent.com/-RQCYGlQ2OW8/UvnFlO-lWZI/AAAAAAAAHcg/tZjOzAZ2pn0/s512-no/38e048fa-f98e-4ebb-a34b-f808ac138248"),
                Uri.parse("https://lh3.googleusercontent.com/-8XsJbk1fGE8/U9xZxGkKg8I/AAAAAAAAAL0/u-Nyv0DhihI/s1836-no/21a953ea-0d28-409b-8cc6-c7e71786f8c2"),
                null, null,
                null, null,
                null, null,
                null, null,
                null, null
        };
        Post newPost;
        for (int i = 0; i < 14; i++) {
            try {
                newPost = new Post(userNames[i], userHandle[i], userPic[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                //Create empty post
                newPost = new Post();
            }
            friends.add(newPost);
        }
        mFriendAdapter =
                new FriendAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.friend_list, // The name of the layout ID.
                        friends);
        ListView listView = (ListView) rootView.findViewById(R.id.friend_list);
        listView.setAdapter(mFriendAdapter);
        return rootView;
    }
}
