package com.appspot.glancesocial.glance;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class MainPageFragment extends Fragment{
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = MainPageFragment.class.getSimpleName();

    public MainPageFragment() {
    }

    // TODO:
    // This only will fill in the usernames with dummy data.
    // We have to create a custom adapter to fill in all of our data.

    CardAdapter mPostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<Post> posts = new ArrayList<Post>();
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
        String[] postText = {
                "Hello world", "Woah this is crazy",
                "So typical", "#Blessed",
                "Get me out of here", "This is coming along nice",
                "Can I go home", "Look at that",
                "Wooooohooooo", "Yeah I mean that is cool too",
                "He was a good actor", "Someone needs to clean",
                "Why", "Why not"
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
        for (int i = 0; i < 14; i++) {
            Post newPost = new Post(userNames[i], userPic[i], postText[i]);
            posts.add(newPost);
        }

        mPostAdapter =
                new CardAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.card, // The name of the layout ID.
                        posts);

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_posts);
        gridView.setAdapter(mPostAdapter);

        return rootView;
    }

}
