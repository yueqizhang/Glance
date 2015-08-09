package com.appspot.glancesocial.glance;

import android.net.Uri;

/**
 * Created by Jonah on 8/2/15.
 */
public class Post {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = TwitterActivity.class.getSimpleName();

    //Private Variables for the post
    private String postType;
    private String userName;
    private String userHandle;
    private Uri userPic;
    private String postText;
    private Uri postPic;

    // Default Constructor
    public Post() {
        this.postType = null;
        this.userName = "User";
        this.userHandle = "@User";
        this.userPic = null;
        this.postText = "Example Post";
        this.postPic = null;
    }

    // No Profile Picture Text Post Constructor
    public Post(String userName, String userHandle, Uri userPic) {
        this.postType = null;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = userPic;
        this.postText = null;
        this.postPic = null;
    }

    // No Profile Picture Text Post Constructor
    public Post(String postType, String userName, String userHandle, String postText) {
        this.postType = postType;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = null;
        this.postText = postText;
        this.postPic = null;
    }

    // Text Post Constructor
    public Post(String postType, String userName, String userHandle, Uri userPic, String postText) {
        this.postType = postType;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = null;
    }

    // Image Post Constructor
    public Post(String postType, String userName, String userHandle, Uri userPic, Uri postPic) {
        this.postType = postType;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = userPic;
        this.postText = null;
        this.postPic = postPic;
    }

    // No Profile Picture Image Post Constructor
    public Post(String postType, String userName, String userHandle, Uri postPic) {
        this.postType = postType;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = null;
        this.postText = null;
        this.postPic = postPic;
    }

    // Image and Text Post Constructor
    public Post(String postType, String userName, String userHandle, Uri userPic, String postText, Uri postPic) {
        this.postType = postType;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = postPic;
    }

    // No Profile Picture Image and Text Post Constructor
    public Post(String postType, String userName, String userHandle, String postText, Uri postPic) {
        this.postType = postType;
        this.userName = userName;
        this.userHandle = userHandle;
        this.userPic = null;
        this.postText = postText;
        this.postPic = postPic;
    }

    // Get Functions
    public String getPostType() { return this.postType; }
    public String getUserName() { return this.userName; }
    public String getUserHandle() { return this.userHandle; }
    public Uri getUserPic() { return this.userPic; }
    public String getPostText() { return this.postText; }
    public Uri getPostPic() { return this.postPic; }

    // Set Functions
    public void setPostType(String postType) { this.postType = postType; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserHandle(String userHandle) { this.userHandle = userHandle; }
    public void setUserPic(Uri userPic) { this.userPic = userPic; }
    public void setPostText(String postText) { this.postText = postText; }
    public void setPostPic(Uri postPic) { this.postPic = postPic; }
}