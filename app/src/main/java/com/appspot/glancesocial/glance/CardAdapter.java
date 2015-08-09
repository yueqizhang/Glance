package com.appspot.glancesocial.glance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jonah on 8/2/15.
 */
public class CardAdapter extends ArrayAdapter<Post> {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = CardAdapter.class.getSimpleName();

    private final Context context;
    private final ArrayList<Post> posts;
    private final int layoutResourceId;

    //Constructor
    public CardAdapter(Context context, int layoutResourceId, ArrayList<Post> posts) {
        super(context, layoutResourceId, posts);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.posts = posts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            //Get the views that we will be editing on the front side
            holder.userNameView = (TextView)row.findViewById(R.id.user_name);
            holder.userHandleView = (TextView)row.findViewById(R.id.user_handle);
            holder.userPicView = (ImageView)row.findViewById(R.id.user_image);
            holder.postTextView = (TextView)row.findViewById(R.id.post_caption);
            holder.postPicView = (ImageView)row.findViewById(R.id.post_image);
            //Get the view that we will be editing on thw back side
            holder.infoUserPicTwitterView = (ImageView)row.findViewById(R.id.info_user_image_twitter);
            holder.infoUserPicInstagramView = (ImageView)row.findViewById(R.id.info_user_image_instagram);

            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        Post post = posts.get(position);

        //Set the text for username
        holder.userNameView.setText(post.getUserName());

        //Set the text for user handle
        holder.userHandleView.setText(post.getUserHandle());

        //Load in the user image
        Utility.loadImage(getContext(), holder.userPicView, post.getUserPic());

        //Set the text for the post content
        holder.postTextView.setText(post.getPostText());

        //Check to see if the post has a picture
        if (post.getPostPic() != null) {
            //If there is a picture then try to load it and round the edges
            holder.postPicView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(post.getPostPic()).into(holder.postPicView);
        } else {
            holder.postPicView.setVisibility(View.GONE);
        }

        if (post.getPostType() != null && post.getPostType().equals("twitter")) {
            //Load in the user image
            Utility.loadImage(getContext(), holder.infoUserPicTwitterView, post.getUserPic());
        } else if (post.getPostType() != null && post.getPostType().equals("instagram")) {
            //Load in the user image
            Utility.loadImage(getContext(), holder.infoUserPicInstagramView, post.getUserPic());
        } else {
            holder.infoUserPicTwitterView.setVisibility(View.GONE);
            holder.infoUserPicInstagramView.setVisibility(View.GONE);
        }

        return row;
    }

    static class ViewHolder {
        //Used on the front side (content side) of the cards
        TextView userNameView;
        TextView userHandleView;
        ImageView userPicView;
        TextView postTextView;
        ImageView postPicView;

        // Used on the back side (info side) of the cards
        ImageView infoUserPicTwitterView;
        ImageView infoUserPicInstagramView;
    }


}
