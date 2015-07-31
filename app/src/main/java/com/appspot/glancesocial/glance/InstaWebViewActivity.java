package com.appspot.glancesocial.glance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InstaWebViewActivity extends Activity {

    final String INSTA_BASE_URL = "https://instagram.com/oauth/authorize/?";
    final String ID_PARAM = "client_id";
    final String REDIRECT_PARAM = "redirect_uri";
    final String RESPONSE_TYPE = "response_type";
    final String SCOPE = "scope";
    final String SCOPE_PARAMS = "likes+comments"; //extra permissions
    public String accessToken;
    private WebView instaWebView;
    final Context context = this;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insta_webview);

        StringBuilder instaUrl = new StringBuilder();
        instaUrl.append(INSTA_BASE_URL)
                .append(ID_PARAM + "=" + LoginPage.INSTA_ID + "&")
                .append(REDIRECT_PARAM + "=" + LoginPage.INSTA_REDIRECT + "&")
                .append(RESPONSE_TYPE + "=token&")
                .append(SCOPE + "=" + SCOPE_PARAMS);

        //this doesnt work b/c the uri builder encodes non-safe URL characters into their hex values
//        Uri instaUri = Uri.parse(INSTA_BASE_URL).buildUpon()
//                .appendQueryParameter(ID_PARAM, LoginPage.INSTA_ID)
//                .appendQueryParameter(REDIRECT_PARAM, LoginPage.INSTA_REDIRECT)
//                .appendQueryParameter(RESPONSE_TYPE, "token")
//                .build();

        Log.d("", "built url: " + instaUrl.toString());

        Log.d(" ", "webview");
        instaWebView = (WebView) findViewById(R.id.webView1);
        instaWebView.getSettings().setJavaScriptEnabled(true);
        instaWebView.loadUrl(instaUrl.toString());

        instaWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("#access_token=")) {
                    accessToken = url.substring(url.indexOf("token=") + 6, url.length());
                    Log.d("", "url: " + url);
                    Log.d("", "access token: " + accessToken);
                    Intent intent = new Intent(context, MainPage.class);
                    context.startActivity(intent);
                } else {
                    Log.d("", "onpagefinished failed");
                }
                super.onPageFinished(view, url);

            }
        });

    }
}
