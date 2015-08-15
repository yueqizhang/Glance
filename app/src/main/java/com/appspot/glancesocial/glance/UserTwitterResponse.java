package com.appspot.glancesocial.glance;

/**
 * Created by christina on 8/14/15.
 */
public class UserTwitterResponse {

    TwitterUser user;

    public UserTwitterResponse() {
    }

    public TwitterUser getUser() {
        return user;
    }

    public String getID() {return user.getUserID();}

    public void setID(String i) {
        this.user.setUserID(i);
    }

    public void setUser(TwitterUser user) {
        this.user = user;
    }
}
