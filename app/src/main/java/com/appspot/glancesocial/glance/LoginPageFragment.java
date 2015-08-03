package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginPageFragment extends Fragment implements View.OnClickListener {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = LoginPageFragment.class.getSimpleName();
    
    public LoginPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        // Instagram Login Button
        ImageButton instagramLogin = (ImageButton) view.findViewById(R.id.instagram_login_button);
        instagramLogin.setOnClickListener(this);

        // Skip Login Button
        Button skipLogin = (Button) view.findViewById(R.id.skip_login);
        skipLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.instagram_login_button:
                intent = new Intent(getActivity(), InstaWebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.skip_login:
                intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}