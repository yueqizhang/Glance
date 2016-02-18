package com.appspot.glancesocial.glance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

/**
 * Created by Jonah on 8/1/15.
 */
public class AccountActivity extends ActionBarActivity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = AccountActivity.class.getSimpleName();

    public static ArrayList<Post> friends;
    private String userToAdd;
    private String userToDelete;
    public ViewFlipper viewFlipper;

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
        //TODO: Disconnect the corresponding account
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(intent.EXTRA_TEXT)) {
            String accountName = intent.getStringExtra(intent.EXTRA_TEXT);
            ((TextView) findViewById(R.id.account_name)).setText(accountName);
            SharedPreferences sharedPref = getSharedPreferences("accountsAdded", Context.MODE_PRIVATE);
            if (accountName.equals("Twitter")) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.twitter_added), "false");
                editor.apply();
                //TODO: Actually revoke permissions
            } else if (accountName.equals("Instagram")) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.instagram_added), "false");
                editor.apply();
                //TODO: Actually revoke permissions
            }
        }
        CharSequence text = "Account Disconnected";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    public void flipAccountInfoViewClickHandler(View target) {
        viewFlipper = (ViewFlipper) findViewById(R.id.account_info_flipper);
        if (viewFlipper.getDisplayedChild()==0) {
            ((ImageButton) findViewById(R.id.more_account_info)).setImageResource(R.drawable.x_icon);
            // There may not be a card currently flipped so check for that
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            // Flip the card that is currently flipped over
            viewFlipper.setDisplayedChild(1);
        } else {
            ((ImageButton) findViewById(R.id.more_account_info)).setImageResource(R.drawable.info_icon);
            // There may not be a card currently flipped so check for that
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            // Flip the card that is currently flipped over
            viewFlipper.setDisplayedChild(0);
        }
    }

    public void addFriendClickHandler(View target) {
        //TODO: Hook up this to the back end
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter User Handle");
        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Take the user input and add the new user to the database
                userToAdd = input.getText().toString();
                Utility.AddUserToParse addUserTask = new Utility()
                        .new AddUserToParse(userToAdd);
                addUserTask.execute();
                AccountFragment.mFriendAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void deleteFriendClickHandler(View target) {
        //TODO: Hook up this to the back end
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter User Handle");
        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Take the user input and delete the user
                userToDelete = input.getText().toString();
                AccountFragment.mFriendAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
