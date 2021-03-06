package com.appspot.glancesocial.glance;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Jonah on 8/5/15.
 */
public class IntroScreenActivity extends FragmentActivity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = IntroScreenActivity.class.getSimpleName();

    public static final String INSTA_ID = "83f264e6915b4dceb166f6a0962dd57b";
    public static final String INSTA_REDIRECT = "http://glancesocial.appspot.com/loading.html";
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    //private static final String TWITTER_KEY = "otG6EvQxlUAQmxqmdRmnsOKBi";
    //private static final String TWITTER_SECRET = "Eh7kzCtbQHCY0Oxg5E60t0BgSzRLIMZRvthdgZEesAa2m96dY7";

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_screen_slide);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return IntroScreenFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    // Click Handler for button on the third page
    public void clickHandler(View target) {
        Intent intent = new Intent(target.getContext(), MainPage.class);
        startActivity(intent);
    }

    // Sends the view to fullscreen immersive mode
    @Override
    @TargetApi(19)
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View view = getWindow().getDecorView().findViewById(R.id.listview_posts);
        if (hasFocus && view != null) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }
}
