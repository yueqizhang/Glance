package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainPage extends ActionBarActivity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = MainPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Glance");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainPageFragment())
                    .commit();
        }
        Intent testIntent = new Intent(this, InstagramService.class);
        startService(testIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // We override this function for one reason:
        // When the user is on the main screen and hits the back button
        // they shouldn't be sent to the intro page. The app should close.
        // This function call will finish all activities (Login and Main).
        this.finishAffinity();
    }

    //App can't handle this yet. Slows down and tries to crash.
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        View view = getWindow().getDecorView().findViewById(R.id.gridview_posts);
//        if (hasFocus) {
//            view.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_IMMERSIVE
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//            );
//        }
//    }
}

