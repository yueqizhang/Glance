package com.appspot.glancesocial.glance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by ctobias44
 */
public class UtilityTwitter {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = Utility.class.getSimpleName();
    static String ownerID;

    public class AddUserToParseTwitter extends AsyncTask<Void, Void, Void> {
        String userID;
        int rank;
        public AddUserToParseTwitter(String userID, int rank) {
            this.userID = userID;
            this.rank = rank;
        }

        void AddBestFriendsToParse(ArrayList<String> friends){
            for (int i = 0; i < friends.size(); i++) {
                ParseObject parseUser = new ParseObject("TwitterUser");
                parseUser.put("userName", friends.get(i));
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            ParseObject parseUser = new ParseObject("TwitterUser");

            parseUser.put("userName", userName);
            parseUser.put("userId", userID);
            parseUser.put("profilePic", proPic);
            parseUser.put("rank", rank);
            parseUser.put("ownerID", ownerID);
            parseUser.saveInBackground();
            return null;
        }
    }

    public class AddPostToParseTwitter extends AsyncTask<Void, Void, Void> {
        JSONObject post;
        String userIdInDB;

        public AddPostToParseTwitter(JSONObject post, String userIdInDB) {
            this.post = post;
            this.userIdInDB = userIdInDB;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String postID;
            String thumbnail;
            String lowImage;
            String media;
            String displayUrl; //display_url
            String caption; //text
            int createdTime; //created_at
            String location = null;
            //int comments;
            int likes;
            int retweets; //retweet_count
            ParseObject postParse = new ParseObject("TwitterPosts");

            try {
                postID = post.getString("id");
                //image
                JSONObject image = post.getJSONObject("images");
                JSONObject thumbnailObj = image.getJSONObject("thumbnail");
                //url thumbnail
                thumbnail = thumbnailObj.getString("url");
                JSONObject lowImageObj = image.getJSONObject("low_resolution");
                //url lowimage
                lowImage = lowImageObj.getString("url");
                JSONObject mediaGroup = image.getJSONObject("media");
                //url media
                media = mediaGroup.getString("url");
                JSONObject dUrl = image.getJSONObject("display_url");
                displayUrl = dUrl.getString("url");

                JSONObject captionObj = post.getJSONObject("text");
                caption = captionObj.getString("text");
                createdTime = Integer.parseInt(captionObj.getString("created_time"));
                JSONObject locationObj = post.getJSONObject("location");
                if (locationObj.has("name") && !locationObj.isNull("name")) {
                    location = locationObj.getString("name");
                }
                //# of retweets
//                JSONObject retweetObj = post.getJSONObject("retweets");
//                retweetObj = Integer.getString("count");
//                //# of likes
//                JSONObject likesObj = post.getJSONObject("likes");
//                likes = Integer.parseInt(likesObj.getString("count"));
//
//                Log.d(LOG_TAG, userIdInDB);
//                Log.d(LOG_TAG, thumbnail);
//                Log.d(LOG_TAG, caption);
//                Log.d(LOG_TAG, lowImage);
//
//                postParse.put("userId", userIdInDB);
//                postParse.put("thumbnail", thumbnail);
//                postParse.put("caption", caption);
//                postParse.put("comments", comments);
//                postParse.put("createdTime", createdTime);
//                postParse.put("likes", likes);
                postParse.put("location", location);
                postParse.put("lowImage", lowImage);
                postParse.put("postId", postID);
                postParse.put("ownerID", ownerID);
                postParse.saveInBackground();

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }

}

