package com.appspot.glancesocial.glance;

import android.net.Uri;

/**
 * Created by Jonah on 8/2/15.
 */
public class Post {
    //Private Variables for the post
    private String userName;
    private Uri userPic;
    private String postText;
    private Uri postPic;

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
    public Uri getUserPic() { return this.userPic; }
    public String getPostText() { return this.postText; }
    public Uri getPostPic() { return this.postPic; }

    // Set Functions
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserPic(Uri userPic) { this.userPic = userPic; }
    public void setPostText(String postText) { this.postText = postText; }
    public void setPostPic(Uri postPic) { this.postPic = postPic; }
}
