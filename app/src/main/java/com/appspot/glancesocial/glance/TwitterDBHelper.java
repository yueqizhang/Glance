package com.appspot.glancesocial.glance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by christina on 8/5/15.
 */
public class TwitterDBHelper extends SQLiteOpenHelper {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = TwitterDBHelper.class.getSimpleName();

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "twitter.db";
    private static final int DATABASE_VERSION = 1;


    public TwitterDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + TwitterContract.UserEntry.TABLE_NAME + " (" +
                InstagramContract.UserEntry._ID + " INTEGER PRIMARY KEY, " +
                InstagramContract.UserEntry.COLUMN_USER_NAME + " TEXT UNIQUE NOT NULL, " +
                InstagramContract.UserEntry.COLUMN_PROFILE_PIC + " TEXT NOT NULL, " +
                InstagramContract.UserEntry.COLUMN_FRIEND_RANK + " INTEGER NOT NULL, " +
                InstagramContract.UserEntry.COLUMN_USER_ID + " INTEGER NOT NULL " +
                " );";

        final String SQL_CREATE_POST_TABLE = "CREATE TABLE " + TwitterContract.PostEntry.TABLE_NAME + " (" +
                InstagramContract.PostEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InstagramContract.PostEntry.COLUMN_USER_KEY + " INTEGER NOT NULL, " +
                InstagramContract.PostEntry.COLUMN_POST_ID + " INTEGER NOT NULL, " +

                InstagramContract.PostEntry.COLUMN_THUMBNAIL + " TEXT NOT NULL, " +
                InstagramContract.PostEntry.COLUMN_LOW_IMAGE + " TEXT NOT NULL," +
                InstagramContract.PostEntry.COLUMN_CAPTION + " TEXT NOT NULL, " +
                InstagramContract.PostEntry.COLUMN_CREATED_TIME + " INTEGER NOT NULL, " +
                InstagramContract.PostEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +

                InstagramContract.PostEntry.COLUMN_COMMENTS + " TEXT NOT NULL, " +
                InstagramContract.PostEntry.COLUMN_LIKES + " INTEGER NOT NULL, " +
                // Set up the user column as a foreign key to user table.
                " FOREIGN KEY (" + TwitterContract.PostEntry.COLUMN_USER_KEY + ") REFERENCES " +
                InstagramContract.UserEntry.TABLE_NAME + " (" + TwitterContract.UserEntry._ID + ") )";

        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_POST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TwitterContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TwitterContract.PostEntry.TABLE_NAME);
        onCreate(db);
    }
}
