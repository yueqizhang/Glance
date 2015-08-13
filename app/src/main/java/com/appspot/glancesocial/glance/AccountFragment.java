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
        Uri[] userPic = {
                Uri.parse("https://pbs.twimg.com/profile_images/1231038234/page.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/508007776061100032/fH5_qPfZ.jpeg"),
                Uri.parse("https://pbs.twimg.com/profile_images/378800000380894329/b065d66b754cd0025fb7f185788a4a26.jpeg"),
                Uri.parse("https://pbs.twimg.com/profile_images/625819931976822785/eZBZE-r_.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/1231031781/brin.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/547588061216137216/5CL6N3VO.jpeg"),
                Uri.parse("https://pbs.twimg.com/profile_images/378800000804593019/dfca1b32e435a872b4745317895d41eb.jpeg"),
                Uri.parse("https://pbs.twimg.com/profile_images/481231649128980480/9hpv14pc.jpeg"),
                Uri.parse("https://pbs.twimg.com/profile_images/625873607047168001/LGOjZNnr.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/543090941759070208/rAbJF8FE.jpeg"),
                Uri.parse("https://pbs.twimg.com/profile_images/615976088229941249/hVsbM-MD.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/625486284505653248/HAYHOkLR.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/616437125362380800/cntVa6Nj.jpg"),
                Uri.parse("https://pbs.twimg.com/profile_images/477397164453527552/uh2w1u1o.jpeg")
        };
        String[] userNames = {
                "Jonah", "Christina",
                "Daisy3214", "Fran",
                "Jake", "Ethan",
                "Courtney", "Shawn",
                "Nicole", "Ross",
                "Phillip", "Carla",
                "Mollie", "Erik"
        };
        String[] userHandle = {
                "@ForeverJonah", "@ctobias44",
                "@Daisy91", "@fbueti2277",
                "@theManJake", "@et31",
                "@dianaH", "@sHaWn0",
                "@nic0le", "@ross_h",
                "@phillipyu32", "@12carla",
                "@mollie5t", "@erik212"
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
