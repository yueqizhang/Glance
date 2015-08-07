package com.appspot.glancesocial.glance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by yueqizhang on 8/5/15.
 */
public class FetchInstaPostTask extends AsyncTask<Void, Void, Void> {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = FetchInstaPostTask.class.getSimpleName();

    static Context mContext = null;

    public FetchInstaPostTask(Context context){
        mContext = context;
    }

    private void getWeatherDataFromJson(String feedJsonStr, SQLiteDatabase db) throws JSONException {

        try {
            JSONObject feedJson = new JSONObject(feedJsonStr);
            JSONArray feedArray = feedJson.getJSONArray("data");
            InstagramService instaService = new InstagramService();
            String postID = null;
            String thumbnail = null;
            String lowImage = null;
            String caption = null;
            long createdTime = -1;
            String location= "";
            int comments = -1;
            int likes = -1;
            long userIdInDB = -1;

            Vector<ContentValues> feedVector = new Vector<ContentValues>(feedArray.length());

            for(int i = 0 ;i < feedArray.length(); i++){
                JSONObject post = feedArray.getJSONObject(i);
                JSONObject user = post.getJSONObject("user");
                Cursor userCursor = mContext.getContentResolver().query(
                        InstagramContract.UserEntry.CONTENT_URI,
                        new String[]{InstagramContract.UserEntry._ID},
                        InstagramContract.UserEntry.COLUMN_USER_ID + " = ?",
                        new String[]{user.getString("id")},
                        null);

                if(userCursor.moveToFirst()){ //user is in database, aka is a best friends
                    userIdInDB = instaService.addUser(user.getString("id"), 0); //get user id in database
                    postID = post.getString("id");
                    JSONObject image = post.getJSONObject("images");
                    JSONObject thumbnailObj = image.getJSONObject("thumbnail");
                    thumbnail = thumbnailObj.getString("url");
                    JSONObject lowImageObj = image.getJSONObject("low_resolution");
                    JSONObject captionObj = post.getJSONObject("caption");
                    caption = captionObj.getString("text");
                    createdTime = Integer.parseInt(captionObj.getString("created_time"));
                    JSONObject locationObj = post.getJSONObject("location");
                    if(locationObj.has("name") && !locationObj.isNull("name")){
                        location = locationObj.getString("name");
                    }
                    JSONObject commentsObj = post.getJSONObject("comments");
                    comments = Integer.parseInt(commentsObj.getString("count"));
                    JSONObject likesObj = post.getJSONObject("likes");
                    likes = Integer.parseInt(likesObj.getString("count"));
                }
                ContentValues postValues = new ContentValues();

                postValues.put(InstagramContract.PostEntry.COLUMN_USER_KEY, userIdInDB);
                postValues.put(InstagramContract.PostEntry.COLUMN_THUMBNAIL, thumbnail);
                postValues.put(InstagramContract.PostEntry.COLUMN_CAPTION, caption);
                postValues.put(InstagramContract.PostEntry.COLUMN_COMMENTS, comments);
                postValues.put(InstagramContract.PostEntry.COLUMN_CREATED_TIME, createdTime);
                postValues.put(InstagramContract.PostEntry.COLUMN_LIKES, likes);
                postValues.put(InstagramContract.PostEntry.COLUMN_LOCATION, location);
                postValues.put(InstagramContract.PostEntry.COLUMN_LOW_IMAGE, lowImage);
                postValues.put(InstagramContract.PostEntry.COLUMN_POST_ID, postID);

                feedVector.add(postValues);
            }

            // add to database
            int inserted = 0;
            if ( feedVector.size() > 0 ) {
                // Student: call bulkInsert to add the weatherEntries to the database here
                ContentValues[] cVArray = new ContentValues[feedVector.size()];
                feedVector.toArray(cVArray);
                inserted = mContext.getContentResolver().bulkInsert(InstagramContract.PostEntry.CONTENT_URI, cVArray);

            }

            Log.d(LOG_TAG, "fetchInstaPost Complete. " + inserted + " Inserted");


        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
