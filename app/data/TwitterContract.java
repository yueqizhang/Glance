package com.appspot.glancesocial.glance;

import android.provider.BaseColumns;
import android.text.format.Time;

public class TwitterContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /*
        Inner class that defines the contents of the post table
     */
    public static final class PostEntry implements BaseColumns {

        public static final String TABLE_NAME = "posts";

        // The location setting string is what will be sent to openweathermap
        // as the location query.
        public static final String COLUMN_POST_SETTING = "post_setting";

    }

    /* Inner class that defines the contents of the Twitter table */
    public static final class TwitterEntry implements BaseColumns {

        public static final String TABLE_NAME = "twitter";

        // Column with the foreign key into the post table.
        public static final String COLUMN_LOC_KEY = "post_id";
        // Time posted, stored as long in milliseconds since the epoch
        public static final String COLUMN_DATE = "date";

        // Post caption
        public static final String COLUMN_CAPTION = "post_caption";

        // Likes is stored as a float representing number of likes
        public static final String COLUMN_LIKES = "likes";
    }
}