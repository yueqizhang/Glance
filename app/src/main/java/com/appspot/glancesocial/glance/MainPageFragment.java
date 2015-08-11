package com.appspot.glancesocial.glance;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ViewFlipper;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = MainPageFragment.class.getSimpleName();

    public ViewFlipper viewFlipper;
    public ViewFlipper currentlyFlipped;
    public ArrayList<Post> posts = new ArrayList<Post>();

    // Default Constructor
    public MainPageFragment() {
    }

    CardAdapter mPostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        StaggeredGridView gridView = (StaggeredGridView) rootView.findViewById(R.id.gridview_posts);
        // Create some dummy data for the GridView.
        String[] postType = {
                "twitter", "instagram",
                "twitter", "instagram",
                "twitter", "twitter",
                "twitter", "twitter",
                "twitter", "twitter",
                "twitter", "twitter",
                "twitter", "twitter"
        };
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
        String[] postText = {
                "Hello world. This is an example of a really long text post that would " +
                        "originally screw up our entire app, but since Jonah is a bona fide " +
                        "genius we no longer have the problem.",
                "Woah this is crazy",
                "So typical", "#Blessed",
                "Get me out of here", "This is coming along nice",
                "Can I go home", "Look at that",
                "Wooooohooooo", "Yeah I mean that is cool too",
                "He was a good actor", "Someone needs to clean",
                "Why", "Why not"
        };
        Uri[] postPic = {
                null,
                Uri.parse("https://lh5.googleusercontent.com/-egDEIsHX1mM/VPe0HjdymFI/AAAAAAAAAWQ/vqK_Q05F9As/w1840-h1836-no/IMG_3449.jpeg"),
                null,
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
                newPost = new Post(postType[i], userNames[i], userHandle[i], "test", userPic[i], postText[i], postPic[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                //Create empty post
                newPost = new Post();
            }
            posts.add(newPost);
        }
        updateGridView(gridView, posts, rootView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                viewFlipper = (ViewFlipper) v;
                // There may not be a card currently flipped so check for that
                if (currentlyFlipped != null) {
                    currentlyFlipped.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
                    currentlyFlipped.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right));
                }
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right));
                // Flip the card that is currently flipped over
                if (currentlyFlipped != null && currentlyFlipped != viewFlipper) {
                    currentlyFlipped.setDisplayedChild(0);
                } else {
                    viewFlipper.setDisplayedChild(0);
                }
                // Flip the card to the correct child view (Ex. Twitter or Instagram)
                if (currentlyFlipped != viewFlipper && posts.get(position).getPostType().equals("twitter")) {
                    viewFlipper.setDisplayedChild(1);
                } else if (currentlyFlipped != viewFlipper &&  posts.get(position).getPostType().equals("instagram")) {
                    viewFlipper.setDisplayedChild(2);
                }
                // Update which card is currently flipped over
                if (currentlyFlipped == viewFlipper) {
                    currentlyFlipped = null;
                } else {
                    currentlyFlipped = viewFlipper;
                }
            }
        });
        return rootView;
    }

    // Takes the Array of Post Objects and updates the Staggered Grid View
    public void updateGridView(StaggeredGridView gridView, ArrayList<Post> posts, View rootView) {
        mPostAdapter =
                new CardAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.card, // The name of the layout ID.
                        posts);
        gridView = (StaggeredGridView) rootView.findViewById(R.id.gridview_posts);
        gridView.setAdapter(mPostAdapter);
    }
}


