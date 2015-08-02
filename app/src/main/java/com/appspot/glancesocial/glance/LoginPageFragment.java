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
public class LoginPageFragment extends Fragment{
    ImageButton instagramLogin;
    public LoginPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        instagramLogin = (ImageButton) view.findViewById(R.id.instagram_login_button);
        instagramLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InstaWebViewActivity.class);
                startActivity(intent);
            }
        });
        Button skipLogin = (Button) view.findViewById(R.id.skip_login);
        skipLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);
            }
        });
        return view;
    }
}