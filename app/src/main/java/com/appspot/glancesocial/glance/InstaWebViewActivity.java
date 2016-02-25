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

import com.appspot.glancesocial.glance.Utility.GetOwnerId;

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
    public static GetOwnerId getID;
    public boolean fromMainPage;
    private WebView instaWebView;
    final Context context = this;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insta_webview);
        Intent intent = getIntent();
        fromMainPage = intent.getBooleanExtra("MainPage", false);
        Log.d(LOG_TAG, "fromMainPage: " + fromMainPage);
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
                SharedPreferences sharedPref = getSharedPreferences("accountsAdded", Context.MODE_PRIVATE);
                if (url.contains("#access_token=")) {
                    accessToken = url.substring(url.indexOf("token=") + 6, url.length());
                    Log.d(LOG_TAG, "url: " + url);
                    Log.d(LOG_TAG, "access token: " + accessToken);
                    if(fromMainPage) { //if this is launched from Settings, returns user back to settings
                        CharSequence text = "Instagram login was successful";
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.instagram_added), "true");
                        editor.apply();
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        getID = new Utility().new GetOwnerId();
                        getID.execute();
                        Intent serviceIntent = new Intent(context, InstagramService.class);
                        startService(serviceIntent);
                        // Calling finish instead of an intent so you don't see the login screen
                        // again when you press the back button
                        finish();
                    }else { //else, the user is returned to the main page
                        // Calling finish instead of an intent so you don't see the login screen
                        // again when you press the back button
                        finish();
                    }
                } else {
                    if (url.contains("error")) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.instagram_added), "false");
                        editor.apply();
                    }
                    Log.d(LOG_TAG, "onpagefinished failed");
                }
                super.onPageFinished(view, url);
            }
        });

    }
}
