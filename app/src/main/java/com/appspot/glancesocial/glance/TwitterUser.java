package com.appspot.glancesocial.glance;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fbueti on 8/11/15.
 */
public class TwitterUser {

    @SerializedName("screen_name")
    String screenName;

    @SerializedName("id_str")
    String userID;


    public TwitterUser() {
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String i) {
        userID = i;
    }
}
