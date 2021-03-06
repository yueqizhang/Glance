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
import android.widget.Toast;

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

/**
 * Created by yueqizhang on 7/30/15.
 */
public class Utility {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = Utility.class.getSimpleName();
    public static String ownerID;

    //Can be used to set the margins of a given view
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    // Takes in a bitmap rounds the corners of it and returns it
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

    // Tries to load the image, if it can't find it then it will load a temp image
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
                Log.v(LOG_TAG, "USER ID: " + ownerID);
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

        public AddUserToParse(String userToAdd) {
            // TODO: Get user name from the user ID
            String userID = userToAdd;
            this.userID = "209932474";
            this.rank = 100;
        }

        @Override
        protected Void doInBackground(Void... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            StringBuilder builtUri = new StringBuilder();
            BufferedReader reader = null;
            String userName = "";
            String fullName = "";
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
                fullName = user.getString("full_name");
                Log.d(LOG_TAG,fullName);
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

            parseUser.put("fullName", fullName);
            parseUser.put("userName", userName);
            parseUser.put("userId", userID);
            parseUser.put("profilePic", proPic);
            parseUser.put("rank", rank);
            parseUser.put("ownerID", ownerID);

            parseUser.saveInBackground();
            return null;
        }
    }

    public static void deleteUserInstagram(String userId, Context context) {
        //TODO: Actually delete the user from Parse
        CharSequence text = userId + " has been deleted!";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
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
            //ParseQuery postExistsQuery = new ParseQuery("InstagramPosts");
            try {
                //postExistsQuery.whereEqualTo("userId", post.getJSONObject("user").getString("id"));
                //postExistsQuery.whereEqualTo("postId", post.getString("id"));
                //Log.d(LOG_TAG,post.getString("id"));
                //postExistsQuery.findInBackground(new FindCallback<ParseObject>() {
                    //@Override
                    //public void done(List<ParseObject> objects, ParseException exc) {
                        String postID = null;
                        String thumbnail = null;
                        String standardImage = null;
                        String caption = null;
                        String userName = null;
                        String fullName = null;
                        int createdTime = -1;
                        String location = null;
                        int comments = -1;
                        int likes = -1;

                        try {
                            postID = post.getString("id");
                            JSONObject user = post.getJSONObject("user");
                            userName = user.getString("username");
                            fullName = user.getString("full_name");
                            JSONObject image = post.getJSONObject("images");
                            thumbnail = user.getString("profile_picture");
                            JSONObject standardImageObj = image.getJSONObject("standard_resolution");
                            standardImage = standardImageObj.getString("url");
                            Log.d(LOG_TAG,"IMAGE URL: "+standardImage);
                            Log.d(LOG_TAG,"WIDTH: "+standardImageObj.getString("width"));
                            Log.d(LOG_TAG,"HEIGHT: "+standardImageObj.getString("height"));
                            JSONObject captionObj = post.getJSONObject("caption");
                            caption = captionObj.getString("text");
                            createdTime = Integer.parseInt(captionObj.getString("created_time"));
                            if (post.has("location") && !post.isNull("location")) {
                                JSONObject locationObj = post.getJSONObject("location");
                                if (locationObj.has("name") && !locationObj.isNull("name")) {
                                    location = locationObj.getString("name");
                                }
                            }
                            JSONObject commentsObj = post.getJSONObject("comments");
                            comments = Integer.parseInt(commentsObj.getString("count"));
                            JSONObject likesObj = post.getJSONObject("likes");
                            likes = Integer.parseInt(likesObj.getString("count"));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        ParseObject postParse = new ParseObject("InstagramPosts");

                        postParse.put("userId", userIdInDB);
                        postParse.put("username", userName);
                        postParse.put("fullName", fullName);
                        postParse.put("thumbnail", thumbnail);
                        postParse.put("caption", caption);
                        postParse.put("comments", comments);
                        postParse.put("createdTime", createdTime);
                        postParse.put("likes", likes);
                        if (location != null)
                            postParse.put("location", location);
                        postParse.put("standardImage", standardImage);
                        postParse.put("postId", postID);
                        postParse.put("ownerID", ownerID);
                        postParse.saveInBackground();
                    //}
                //});
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    // Twitter Date Formatting
    // --------------------------------------------------------------------
    // LEAVE THIS IN HERE - WE WILL BE USING IT WHEN WE ADD TWITTER BACK IN
    // --------------------------------------------------------------------
    public static String formatTwitterDate(int unformattedDate) {
        String howLongAgo = "";
        if (unformattedDate != -1) {
            String unformattedStringDate = String.valueOf(unformattedDate);
            String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
            Calendar postTimestamp = Calendar.getInstance();
            try {
                Date date = new SimpleDateFormat(LARGE_TWITTER_DATE_FORMAT, Locale.ENGLISH)
                        .parse(unformattedStringDate);
                postTimestamp.setTime(date);
                Calendar rightNow = Calendar.getInstance();
                howLongAgo = getTimeDifference(rightNow, postTimestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            howLongAgo = "N/A";
        }

        return howLongAgo;
    }

    // Instagram Date Formatting
    public static String formatInstagramDate(int unformattedDate) {
        String howLongAgo = "";
        if (unformattedDate != -1) {
            long parsedTimestamp = (long) unformattedDate * 1000;
            Calendar postTimestamp = Calendar.getInstance();

            try {
                Date date = new Date(parsedTimestamp);
                postTimestamp.setTime(date);
                Calendar rightNow = Calendar.getInstance();
                howLongAgo = getTimeDifference(rightNow, postTimestamp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            howLongAgo = "N/A";
        }
        return howLongAgo;
    }

    // Takes two calendar objects and gets the time difference between the two
    public static String getTimeDifference(Calendar currentTime, Calendar postTime) {
        String howLongAgo = "";
        int week = currentTime.get(Calendar.WEEK_OF_YEAR);
        int postWeek = postTime.get(Calendar.WEEK_OF_YEAR);
        int day = currentTime.get(Calendar.DAY_OF_YEAR);
        int postDay = postTime.get(Calendar.DAY_OF_YEAR);
        int hour = currentTime.get(Calendar.HOUR);
        int postHour = postTime.get(Calendar.HOUR);
        int minute = currentTime.get(Calendar.MINUTE);
        int postMinute = postTime.get(Calendar.MINUTE);
        int second = currentTime.get(Calendar.SECOND);
        int postSecond = postTime.get(Calendar.SECOND);
        if (week != postWeek) {
            if ((week - postWeek) >= 0) {
                howLongAgo = "" + (week - postWeek) + "w";
            } else {
                howLongAgo = "" + (week - postWeek + 52) + "w";
            }
        } else if (day != postDay) {
            if ((day - postDay) >= 0) {
                howLongAgo = "" + (day - postDay) + "d";
            } else {
                howLongAgo = "" + (day - postDay + 7) + "d";
            }
        } else if (hour != postHour) {
            if ((hour - postHour) >= 0) {
                howLongAgo = "" + (hour - postHour) + "h";
            } else {
                howLongAgo = "" + (hour - postHour + 24) + "h";
            }
        } else if (minute != postMinute) {
            if ((minute - postMinute) >= 0) {
                howLongAgo = "" + (minute - postMinute) + "m";
            } else {
                howLongAgo = "" + (minute - postMinute + 60) + "m";
            }
        } else if (second != postSecond) {
            if ((second - postSecond) >= 0) {
                howLongAgo = "" + (second - postSecond) + "s";
            } else {
                howLongAgo = "" + (second - postSecond + 60) + "s";
            }
        }
        return howLongAgo;
    }
}

