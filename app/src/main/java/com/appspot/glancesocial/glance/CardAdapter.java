package com.appspot.glancesocial.glance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            //Get the views that we will be editing
            holder.userNameView = (TextView)row.findViewById(R.id.user_name);
            holder.userPicView = (ImageView)row.findViewById(R.id.user_image);
            holder.postTextView = (TextView)row.findViewById(R.id.post_caption);
            holder.postPicView = (ImageView)row.findViewById(R.id.post_image);

            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        Post post = posts.get(position);

        //Set the text for username
        holder.userNameView.setText(post.getUserName());

        //Check to see if the user has a profile picture
        if (post.getUserPic() != null) {
            //If there is a picture then try to load it
            holder.userPicView.setImageBitmap(post.getUserPic());
        } else {
            //If there isn't an image for the user then we use the default
            holder.userPicView.setImageResource(R.mipmap.temp);
        }

        //Set the text for the post content
        holder.postTextView.setText(post.getPostText());

        //Check to see if the post has a picture
        if (post.getPostPic() != null) {
            //If there is a picture then try to load it and round the edges
            Bitmap roundedPostPic = Utility.getRoundedCornerBitmap(post.getPostPic(),15);
            holder.postPicView.setImageBitmap(roundedPostPic);
        } else {
            Utility.setMargins(holder.postPicView,0,0,0,0);
        }

        return row;
    }

    static class ViewHolder {
        TextView userNameView;
        ImageView userPicView;
        TextView postTextView;
        ImageView postPicView;
    }
}
