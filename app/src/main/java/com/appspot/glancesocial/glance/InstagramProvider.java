package com.appspot.glancesocial.glance;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by yueqizhang on 8/8/15.
 */
public class InstagramProvider extends ContentProvider {

    private static final SQLiteQueryBuilder joinTablesBuilder;

    static{
        joinTablesBuilder = new SQLiteQueryBuilder();

        joinTablesBuilder.setTables(
                InstagramContract.PostEntry.TABLE_NAME + " INNER JOIN " +
                        InstagramContract.UserEntry.TABLE_NAME +
                        " ON " + InstagramContract.PostEntry.TABLE_NAME +
                        "." + InstagramContract.PostEntry.COLUMN_USER_KEY +
                        " = " + InstagramContract.UserEntry.TABLE_NAME +
                        "." + InstagramContract.UserEntry._ID);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
