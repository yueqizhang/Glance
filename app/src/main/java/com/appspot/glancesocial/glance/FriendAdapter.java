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
 * Created by Jonah on 8/9/15.
 */
public class FriendAdapter extends ArrayAdapter<Post> {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = FriendAdapter.class.getSimpleName();

    private final Context context;
    private final ArrayList<Post> posts;
    private final int layoutResourceId;

    //Constructor
    public FriendAdapter(Context context, int layoutResourceId, ArrayList<Post> posts) {
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
            holder.friendNameView = (TextView)row.findViewById(R.id.friend_name);
            holder.friendHandleView = (TextView)row.findViewById(R.id.friend_handle);
            holder.friendPicView = (ImageView)row.findViewById(R.id.friend_picture);

            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        Post post = posts.get(position);

        //Set the text for username
        holder.friendNameView.setText(post.getUserName());

        //Set the text for user handle
        holder.friendHandleView.setText(post.getUserHandle());

        //Check to see if the user has a profile picture
        if (post.getUserPic() != null) {
            //If there is a picture then try to load it
            Picasso.with(getContext()).load(post.getUserPic()).into(holder.friendPicView);
        } else {
            //If there isn't an image for the user then we use the default
            holder.friendPicView.setImageResource(R.mipmap.temp);
        }

        return row;
    }

    static class ViewHolder {
        TextView friendNameView;
        TextView friendHandleView;
        ImageView friendPicView;
    }
}
