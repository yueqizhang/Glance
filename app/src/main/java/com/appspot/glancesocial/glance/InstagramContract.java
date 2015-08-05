package com.appspot.glancesocial.glance;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yueqizhang on 8/4/15.
 */
public class InstagramContract {

    public static final String CONTENT_AUTHORITY = "com.appspot.glancesocial.glance";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";
    public static final String PATH_POST = "post";


    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_PROFILE_PIC = "profile_pic";
        public static final String COLUMN_FRIEND_RANK = "friend_rank";
        public static final String COLUMN_USER_ID = "user_id"; //used in api to get info about the user

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PostEntry implements BaseColumns{
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_USER_KEY = "user_id";
        public static final String COLUMN_POST_ID = "post_id";
        public static final String COLUMN_LOW_IMAGE = "low_image"; //low-res image
        public static final String COLUMN_HIGH_IMAGE = "high_image"; //high-res image for when post is expanded
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_LOCATION = "location"; // ditto for database, but we can just use text for now
        public static final String COLUMN_COMMENTS = "comments"; //TODO: we might have to make another database to hold these
        public static final String COLUMN_LIKES = "likes"; //ditto ^

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POST).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POST;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POST;

        public static Uri buildFeedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUserFeed(String user) {
            return CONTENT_URI.buildUpon().appendPath(user).build();
        }
    }


}
