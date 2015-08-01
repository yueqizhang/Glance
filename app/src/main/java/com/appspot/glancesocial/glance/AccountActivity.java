package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jonah on 8/1/15.
 */
public class AccountActivity extends ActionBarActivity {

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

    public static class AccountFragment extends Fragment {

        public AccountFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.account_fragment, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(intent.EXTRA_TEXT)) {
                String accountName = intent.getStringExtra(intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.account_name)).setText(accountName);
            }
            return rootView;
        }
    }
}
