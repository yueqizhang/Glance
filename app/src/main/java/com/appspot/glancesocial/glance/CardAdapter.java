package com.appspot.glancesocial.glance;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
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

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.userNameView = (TextView)row.findViewById(R.id.user_name);
            holder.userPicView = (ImageView)row.findViewById(R.id.user_image);
            holder.postTextView = (TextView)row.findViewById(R.id.post_caption);
            holder.postPicView = (ImageView)row.findViewById(R.id.post_image);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        Post post = posts.get(position);

        holder.userNameView.setText(post.getUserName());
        if (post.getUserPic() != null) {
            try {
                //This code will not load the image for some reason
                //Needs to be fixed
                holder.userPicView.setImageDrawable(Drawable.createFromStream(
                        context.getContentResolver().openInputStream(post.getUserPic()),
                        null));
            } catch (FileNotFoundException e) {
                //If the user's image doesn't load then we use the default
                holder.userPicView.setImageResource(R.mipmap.ic_launcher);
                e.printStackTrace();
            }
        } else {
            //If there isn't an image for the user then we use the default
            holder.userPicView.setImageResource(R.mipmap.ic_launcher);
        }
        holder.postTextView.setText(post.getPostText());
        if (post.getPostPic() != null) {
            try {
                //This code will not load the image for some reason
                //Needs to be fixed
                holder.userPicView.setImageDrawable(Drawable.createFromStream(
                        context.getContentResolver().openInputStream(post.getPostPic()),
                        null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return row;
    }

    static class ViewHolder
    {
        TextView userNameView;
        ImageView userPicView;
        TextView postTextView;
        ImageView postPicView;
    }
}
