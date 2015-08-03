package com.appspot.glancesocial.glance;

import android.net.Uri;

/**
 * Created by Jonah on 8/2/15.
 */
public class Post {
    public String userName;
    public Uri userPic;
    public String postText;
    public Uri postPic;

    public Post() {
        this.userName = "User";
        this.userPic = null;
        this.postText = "Example Post";
        this.postPic = null;
    }

    public Post(String userName, String postText) {
        this.userName = userName;
        this.userPic = null;
        this.postText = postText;
        this.postPic = null;
    }

    public Post(String userName, Uri userPic, String postText) {
        this.userName = userName;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = null;
    }

    public Post(String userName, Uri userPic, String postText, Uri postPic) {
        this.userName = userName;
        this.userPic = userPic;
        this.postText = postText;
        this.postPic = postPic;
    }

    public String getUserName() {
        return this.userName;
    }

    public Uri getUserPic() {
        return this.userPic;
    }

    public String getPostText() {
        return this.postText;
    }

    public Uri getPostPic() {
        return this.postPic;
    }
}
