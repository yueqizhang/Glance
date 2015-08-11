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

/**
 * Created by yueqizhang on 7/30/15.
 */
public class Utility {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = Utility.class.getSimpleName();
    String ownerID;

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static void loadImage(Context context, ImageView imageView, Uri uri) {
        //Check to see if there is an image to load
        if (uri != null) {
            //If there is a image then try to load it
            Picasso.with(context).load(uri).into(imageView);
        } else {
            //If there isn't an image for the user then we use the default
            imageView.setImageResource(R.mipmap.temp);
        }
    }

    public class GetOwnerId extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            StringBuilder builtUri = new StringBuilder();
            BufferedReader reader = null;

            builtUri.append(InstagramService.INSTA_BASE_URL)
                    .append("users/self")
                    .append("/?access_token=")
                    .append(InstaWebViewActivity.accessToken);
            try {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                JSONObject obj = new JSONObject(buffer.toString());
                JSONObject user = obj.getJSONObject("data");
                ownerID = user.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    public class AddUserToParse extends AsyncTask<Void, Void, Void> {
        String userID;
        int rank;
        public AddUserToParse(String userID, int rank) {
            this.userID = userID;
            this.rank = rank;
        }

        @Override
        protected Void doInBackground(Void... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            StringBuilder builtUri = new StringBuilder();
            BufferedReader reader = null;
            String userName = "";
            String proPic = "";

            builtUri.append(InstagramService.INSTA_BASE_URL)
                    .append("users/")
                    .append(userID)
                    .append("/?access_token=")
                    .append(InstaWebViewActivity.accessToken);
            try {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                JSONObject obj = new JSONObject(buffer.toString());
                JSONObject user = obj.getJSONObject("data");
                userName = user.getString("username");
                proPic = user.getString("profile_picture");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            ParseObject parseUser = new ParseObject("InstagramUser");


            parseUser.put("userName", userName);
            parseUser.put("userId", userID);
            parseUser.put("profilePic", proPic);
            parseUser.put("rank", rank);
            parseUser.put("ownerID", ownerID);
            parseUser.saveInBackground();
            return null;
        }
    }

    public class AddPostToParse extends AsyncTask<Void, Void, Void> {
        JSONObject post;
        String userIdInDB;

        public AddPostToParse(JSONObject post, String userIdInDB) {
            this.post = post;
            this.userIdInDB = userIdInDB;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String postID;
            String thumbnail;
            String lowImage;
            String caption;
            int createdTime;
            String location = null;
            int comments;
            int likes;
            ParseObject postParse = new ParseObject("InstagramPosts");

            try {
                postID = post.getString("id");
                JSONObject image = post.getJSONObject("images");
                JSONObject thumbnailObj = image.getJSONObject("thumbnail");
                thumbnail = thumbnailObj.getString("url");
                JSONObject lowImageObj = image.getJSONObject("low_resolution");
                lowImage = lowImageObj.getString("url");
                JSONObject captionObj = post.getJSONObject("caption");
                caption = captionObj.getString("text");
                createdTime = Integer.parseInt(captionObj.getString("created_time"));
                JSONObject locationObj = post.getJSONObject("location");
                if (locationObj.has("name") && !locationObj.isNull("name")) {
                    location = locationObj.getString("name");
                }
                JSONObject commentsObj = post.getJSONObject("comments");
                comments = Integer.parseInt(commentsObj.getString("count"));
                JSONObject likesObj = post.getJSONObject("likes");
                likes = Integer.parseInt(likesObj.getString("count"));

                Log.d(LOG_TAG, userIdInDB);
                Log.d(LOG_TAG, thumbnail);
                Log.d(LOG_TAG, caption);
                Log.d(LOG_TAG, lowImage);

                postParse.put("userId", userIdInDB);
                postParse.put("thumbnail", thumbnail);
                postParse.put("caption", caption);
                postParse.put("comments", comments);
                postParse.put("createdTime", createdTime);
                postParse.put("likes", likes);
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