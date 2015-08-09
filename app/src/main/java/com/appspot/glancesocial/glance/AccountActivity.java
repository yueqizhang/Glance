package com.appspot.glancesocial.glance;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Jonah on 8/1/15.
 */
public class AccountActivity extends ActionBarActivity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = AccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AccountFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void disconnectAccountClickHandler(View target) {
        CharSequence text = "Account Disconnected";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void addFriendClickHandler(View target) {
        CharSequence text = "Open Twitter";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void deleteFriendClickHandler(View target) {
        CharSequence text = "Open Twitter";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
