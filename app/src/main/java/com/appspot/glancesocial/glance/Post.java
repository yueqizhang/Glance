package com.appspot.glancesocial.glance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jonah on 8/2/15.
 */
public class Post {
    //Private Variables for the post
    private String userName;
    private Uri userPic;
    private String postText;
    private Uri postPic;
    final String LOG_TAG = Post.class.getSimpleName();

    // Default Constructor
    public Post() {
        this.userName = "User";
        this.userPic = null;
        this.postText = "Example Post";
        this.postPic = null;
    }

    // No Profile Picture Text Post Constructor
    public Post(String userName, String postText) {
        this.userName = userName;
        this.userPic = null;
        this.postText = postText;
        this.postPic = null;
    }

    // Text Post Constructor
    public Post(String userName, Uri userPic, String postText) {
        this.userName = userName;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = null;
    }

    // Image Post Constructor
    public Post(String userName, Uri userPic, Uri postPic) {
        this.userName = userName;
        this.userPic = userPic;
        this.postText = null;
        this.postPic = postPic;
    }

    // No Profile Picture Image Post Constructor
    public Post(String userName, Uri postPic) {
        this.userName = userName;
        this.userPic = null;
        this.postText = null;
        this.postPic = postPic;
    }

    // Image and Text Post Constructor
    public Post(String userName, Uri userPic, String postText, Uri postPic) {
        this.userName = userName;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = postPic;
    }

    // No Profile Picture Image and Text Post Constructor
    public Post(String userName, String postText, Uri postPic) {
        this.userName = userName;
        this.userPic = null;
        this.postText = postText;
        this.postPic = postPic;
    }


    // Get Functions
    public String getUserName() { return this.userName; }
    public Bitmap getUserPic() {
        Bitmap result = null;
        getUserPicTask task = new getUserPicTask();
        try {
            result = task.execute(this.userPic).get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return result;
    }
    public String getPostText() { return this.postText; }
    public Bitmap getPostPic() {
        Bitmap result = null;
        getUserPicTask task = new getUserPicTask();
        try {
            result = task.execute(this.postPic).get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return result;
    }

    // Set Functions
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserPic(Uri userPic) { this.userPic = userPic; }
    public void setPostText(String postText) { this.postText = postText; }
    public void setPostPic(Uri postPic) { this.postPic = postPic; }

    class getUserPicTask extends AsyncTask<Uri, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Uri... uris) {
            Bitmap userPicBitmap = null;
            if(uris[0] != null) {
                try {
                    URL picURL = new URL(uris[0].toString());
                    HttpURLConnection connection = (HttpURLConnection) picURL.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    userPicBitmap = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "Downloading Image Failed");
                }
            }
            return userPicBitmap;
        }
    }
}