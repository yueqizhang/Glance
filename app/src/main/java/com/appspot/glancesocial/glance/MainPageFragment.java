package com.appspot.glancesocial.glance;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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
        ListView listView = (ListView) rootView.findViewById(R.id.listview_posts);
        ParseQuery<ParseObject> query = new ParseQuery("InstagramPosts");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String ownerID = sharedPref.getString(getString(R.string.owner_id), "");
        query.whereEqualTo("ownerID", ownerID).findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                Post newPost;
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        try {
                            newPost = new Post("instagram",
                                    // TODO: Need to get the user name/handle from ID
                                    object.get(i).getString("fullName"),
                                    object.get(i).getString("username"),
                                    object.get(i).getInt("createdTime"),
                                    // TODO: Need to get the user's profile picture
                                    Uri.parse(object.get(i).getString("thumbnail")),
                                    object.get(i).getString("caption"),
                                    Uri.parse(object.get(i).getString("standardImage")));
                            posts.add(newPost);
                        } catch (ArrayIndexOutOfBoundsException exc) {
                            //Create empty post
                            exc.printStackTrace();
                            newPost = new Post();
                        }
                    }
                } else {
                    e.printStackTrace();
                    newPost = new Post();
                    posts.add(newPost);
                }
                mPostAdapter.notifyDataSetChanged();
                updateTimeLine();
            }
        });
        updateListView(listView, posts, rootView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                } else if (currentlyFlipped != viewFlipper && posts.get(position).getPostType().equals("instagram")) {
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

    public void updateTimeLine() {
        mPostAdapter =
                new CardAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.card, // The name of the layout ID.
                        posts);
        ListView listView = (ListView) getActivity().findViewById(R.id.listview_posts);
        listView.setAdapter(mPostAdapter);
    }

    public void updateListView(ListView listView, ArrayList<Post> posts, View rootView) {
        mPostAdapter =
                new CardAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.card, // The name of the layout ID.
                        posts);
        listView = (ListView) rootView.findViewById(R.id.listview_posts);
        listView.setAdapter(mPostAdapter);
    }
}


