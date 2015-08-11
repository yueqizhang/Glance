package com.appspot.glancesocial.glance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yueqizhang on 7/30/15.
 */
public class Utility {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = Utility.class.getSimpleName();

    //Can be used to set the margins of a given view
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    // Takes in a bitmap rounds the corners of it and returns it
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    // Tries to load the image, if it can't find it then it will load a temp image
    public static void loadImage(Context context, ImageView imageView, Uri uri) {
        //Check to see if there is an image to load
        if (uri != null) {
            //If there is a image then try to load it
            Picasso.with(context).load(uri).into(imageView);
        } else {
            //If there isn't an image for the user then we use the default
            imageView.setImageResource(R.mipmap.temp);
        }
    }

    // Twitter Date Formatting
    public static String formatTwitterDate(String unformattedDate) {
        // TODO: Format the date and get how long ago it was posted
        // TODO: Get information from Twitter date
        String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
        Calendar postTimestamp = Calendar.getInstance();
        String howLongAgo = "";
        try {
            Date date = new SimpleDateFormat(LARGE_TWITTER_DATE_FORMAT, Locale.ENGLISH)
                    .parse(unformattedDate);
            postTimestamp.setTime(date);
            Calendar rightNow = Calendar.getInstance();
            int week = rightNow.get(Calendar.WEEK_OF_YEAR);
            int postWeek = postTimestamp.get(Calendar.WEEK_OF_YEAR);
            int day = rightNow.get(Calendar.DAY_OF_YEAR);
            int postDay = postTimestamp.get(Calendar.DAY_OF_YEAR);
            int hour = rightNow.get(Calendar.HOUR);
            int postHour = postTimestamp.get(Calendar.HOUR);
            int minute = rightNow.get(Calendar.MINUTE);
            int postMinute = postTimestamp.get(Calendar.MINUTE);
            int second = rightNow.get(Calendar.SECOND);
            int postSecond = postTimestamp.get(Calendar.SECOND);
            if (week != postWeek) {
                howLongAgo = "" + (week - postWeek) + "w";
            } else if (day != postDay) {
                howLongAgo = "" + (day - postDay) + "d";
            } else if (hour != postHour) {
                howLongAgo = "" + (hour - postHour) + "h";
            } else if (minute != postMinute) {
                howLongAgo = "" + (minute - postMinute) + "m";
            } else if (second != postSecond) {
                howLongAgo = "" + (second - postSecond) + "s";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return howLongAgo;
    }

    // Instagram Date Formatting
    public static String formatInstagramDate(String unformattedDate) {
        // TODO: Format the date and get how long ago it was posted
        // TODO: Get information from Instagram date
        long parsedTimestamp = Long.parseLong(unformattedDate)*1000;
        String LARGE_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
        Calendar postTimestamp = Calendar.getInstance();
        String howLongAgo = "";
        try {
            Date date = new Date(parsedTimestamp);
            //DateFormat formatter = new SimpleDateFormat(LARGE_DATE_FORMAT);
            postTimestamp.setTime(date);
            Calendar rightNow = Calendar.getInstance();
            int week = rightNow.get(Calendar.WEEK_OF_YEAR);
            int postWeek = postTimestamp.get(Calendar.WEEK_OF_YEAR);
            int day = rightNow.get(Calendar.DAY_OF_YEAR);
            int postDay = postTimestamp.get(Calendar.DAY_OF_YEAR);
            int hour = rightNow.get(Calendar.HOUR);
            int postHour = postTimestamp.get(Calendar.HOUR);
            int minute = rightNow.get(Calendar.MINUTE);
            int postMinute = postTimestamp.get(Calendar.MINUTE);
            int second = rightNow.get(Calendar.SECOND);
            int postSecond = postTimestamp.get(Calendar.SECOND);
            if (week != postWeek) {
                howLongAgo = "" + (week - postWeek) + "w";
            } else if (day != postDay) {
                howLongAgo = "" + (day - postDay) + "d";
            } else if (hour != postHour) {
                howLongAgo = "" + (hour - postHour) + "h";
            } else if (minute != postMinute) {
                howLongAgo = "" + (minute - postMinute) + "m";
            } else if (second != postSecond) {
                howLongAgo = "" + (second - postSecond) + "s";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return howLongAgo;
    }
}