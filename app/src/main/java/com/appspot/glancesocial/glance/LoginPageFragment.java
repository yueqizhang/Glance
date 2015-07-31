package com.appspot.glancesocial.glance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginPageFragment extends Fragment implements View.OnClickListener{
    ImageButton instagramLogin;
    public LoginPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(" ", "view created");

        View view = inflater.inflate(R.layout.login_page_fragment, container, false);
        instagramLogin = (ImageButton) view.findViewById(R.id.instagram_login_button);
        Log.d(" ", "button found");
        if(instagramLogin == null){
            Log.d("tag", "button is null");
        }
        instagramLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d(" ", "Insta clicked");
        Intent intent = new Intent(getActivity(), InstaWebViewActivity.class);
        startActivity(intent);
    }
}