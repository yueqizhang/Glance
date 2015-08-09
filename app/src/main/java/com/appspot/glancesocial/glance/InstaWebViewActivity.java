package com.appspot.glancesocial.glance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class InstaWebViewActivity extends Activity {
    // Use LOG_TAG when logging anything
    private final String LOG_TAG = InstaWebViewActivity.class.getSimpleName();

    final String INSTA_BASE_URL = "https://instagram.com/oauth/authorize/?";
    final String ID_PARAM = "client_id";
    final String REDIRECT_PARAM = "redirect_uri";
    final String RESPONSE_TYPE = "response_type";
    final String SCOPE = "scope";
    final String SCOPE_PARAMS = "likes+comments"; //extra permissions
    public static String accessToken= null;
    public boolean fromSettingsActivity;
    private WebView instaWebView;
    final Context context = this;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insta_webview);
        Intent intent = getIntent();
        fromSettingsActivity = intent.getBooleanExtra("SettingsActivity", false);
        Log.d(LOG_TAG, "fromsettingsactivity: " + fromSettingsActivity);
        StringBuilder instaUrl = new StringBuilder();
        instaUrl.append(INSTA_BASE_URL)
                .append(ID_PARAM + "=" + IntroScreenActivity.INSTA_ID + "&")
                .append(REDIRECT_PARAM + "=" + IntroScreenActivity.INSTA_REDIRECT + "&")
                .append(RESPONSE_TYPE + "=token&")
                .append(SCOPE + "=" + SCOPE_PARAMS);

        Log.d(LOG_TAG, "built url: " + instaUrl.toString());

        instaWebView = (WebView) findViewById(R.id.webView1);
        instaWebView.getSettings().setJavaScriptEnabled(true);
        instaWebView.loadUrl(instaUrl.toString());

        instaWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Intent intent;
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                if (url.contains("#access_token=")) {
                    accessToken = url.substring(url.indexOf("token=") + 6, url.length());
                    Log.d(LOG_TAG, "url: " + url);
                    Log.d(LOG_TAG, "access token: " + accessToken);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.instagram_added), "true");
                    editor.apply();
                    if(fromSettingsActivity) { //if this is launched from Settings, returns user back to settings
                        CharSequence text = "Instagram login was successful";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        // Calling finish instead of an intent so you don't see the login screen
                        // again when you press the back button
                        finish();
                    }else { //else, the user is returned to the main page
                        // Calling finish instead of an intent so you don't see the login screen
                        // again when you press the back button
                        finish();
                    }
                } else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.instagram_added), "false");
                    editor.apply();
                    Log.d(LOG_TAG, "onpagefinished failed");
                }
                super.onPageFinished(view, url);

            }
        });

    }
}
