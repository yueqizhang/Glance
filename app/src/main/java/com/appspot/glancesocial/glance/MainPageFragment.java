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
        String[] postTime = {
                "Tue Aug 11 18:26:07 +0000 2015", "1439287204",
                "Tue Aug 11 16:26:07 +0000 2015", "1439287204",
                "Tue Aug 11 11:26:07 +0000 2015", "Tue Aug 11 11:26:07 +0000 2015",
                "Mon Aug 10 18:26:07 +0000 2015", "Tue Aug 11 18:26:07 +0000 2015",
                "Mon Aug 10 20:26:07 +0000 2015", "Tue Aug 11 Dec 23 18:26:07 +0000 2015",
                "Mon Aug 10 18:26:07 +0000 2015", "Mon Aug 10 18:26:07 +0000 2015",
                "Mon Aug 10 1:26:07 +0000 2015", "Mon Aug 10 18:26:07 +0000 2015"
        };
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
        String[] postText = {
                "I wish life was always this beautiful",
                "Just California things",
                "So typical", "#Blessed",
                "Glad to be back", "Sometimes people are really nice",
                "Can I go home?? #worksucks", "Look at that",
                "Wooooohooooo", "Beauty is in the eye of the beholder",
                "Alphabet.. I can't get over the name", "Someone needs to clean",
                "Love these kids #timeforcollege", "Why not"
        };
        Uri[] postPic = {
                null, Uri.parse("https://pbs.twimg.com/media/CKiJzfHWcAApWty.jpg:large"),
                null,
                Uri.parse("https://pbs.twimg.com/media/CFnOsvYWIAA8TvL.jpg:large"),
                Uri.parse("https://pbs.twimg.com/profile_images/619883932696322048/L1N-eP3y.jpg"), null,
                null, null,
                null,
                null,
                null, null,
                Uri.parse("https://pbs.twimg.com/media/CDeZoQ7UkAA7xC1.jpg:large"), null
        };
        Post newPost;
        for (int i = 0; i < 14; i++) {
            try {
                newPost = new Post(postType[i], userNames[i], userHandle[i], postTime[i], userPic[i], postText[i], postPic[i]);
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


