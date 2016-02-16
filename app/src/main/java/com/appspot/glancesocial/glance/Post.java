package com.appspot.glancesocial.glance;

import android.net.Uri;

/**
 * Created by Jonah on 8/2/15.
 */
public class Post {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = Post.class.getSimpleName();

    //Private Variables for the post
    private String postType;
    private String userName;
    private String userHandle;
    private int postTime;
    private Uri userPic;
    private String postText;
    private Uri postPic;

    // Default Constructor - Should NOT be used
    public Post() {
        this.postType = null;
        this.userName = "User";
        this.userHandle = "@User";
        this.postTime = -1;
        this.userPic = null;
        this.postText = "Example Post";
        this.postPic = null;
    }

    // Constructor used for the friends list in the account management page
    public Post(String userName, String userHandle, Uri userPic) {
        this.postType = null;
        if (userName.isEmpty()) {
            this.userName = userHandle;
        } else {
            this.userName = userName;
        }
        this.userHandle = userHandle;
        this.postTime = -1;
        this.userPic = userPic;
        this.postText = null;
        this.postPic = null;
    }

    // Full Constructor
    public Post(String postType, String userName, String userHandle, int postTime, Uri userPic, String postText, Uri postPic) {
        this.postType = postType;
        if (userName.isEmpty()) {
            this.userName = userHandle;
        } else {
            this.userName = userName;
        }
        this.userHandle = userHandle;
        this.postTime = postTime;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = postPic;
    }

    // Get Functions
    public String getPostType() { return this.postType; }
    public String getUserName() { return this.userName; }
    public String getUserHandle() { return this.userHandle; }
    public int getPostTime() { return  this.postTime; }
    public Uri getUserPic() { return this.userPic; }
    public String getPostText() { return this.postText; }
    public Uri getPostPic() { return this.postPic; }

    // Set Functions
    public void setPostType(String postType) { this.postType = postType; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserHandle(String userHandle) { this.userHandle = userHandle; }
    public void setPostTime(int postTime) { this.postTime = postTime; }
    public void setUserPic(Uri userPic) { this.userPic = userPic; }
    public void setPostText(String postText) { this.postText = postText; }
    public void setPostPic(Uri postPic) { this.postPic = postPic; }
}