package com.appspot.glancesocial.glance;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yueqizhang on 7/30/15.
 */
public class InstagramService extends IntentService{

    final String LOG_TAG = InstagramService.class.getSimpleName();
    final String INSTA_BASE_URL = "https://api.instagram.com/v1/";
    final String ACCESS = "access_token";
    URL url;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String mediaLiked = null;
    ArrayList<String> likedUsers = new ArrayList<String>();

    public InstagramService(){
        super("InstagramService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getBestFriends();
    }

    public void getBestFriends(){
        StringBuilder builtUri = new StringBuilder();
        builtUri.append(INSTA_BASE_URL)
                .append("users/self/media/liked?")
                .append(ACCESS + "=")
                .append(InstaWebViewActivity.accessToken);

        try {
            url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            mediaLiked = buffer.toString();
            JSONObject jObj = new JSONObject(mediaLiked);
            JSONArray data = jObj.optJSONArray("data");
            for(int i=0; i < data.length(); i++){
                JSONObject imageEntry = data.getJSONObject(i);
                JSONObject user = imageEntry.getJSONObject("user");
                likedUsers.add(user.getString("username"));
            }
        Log.d(LOG_TAG, likedUsers.toString());
        }catch(JSONException e){
            e.printStackTrace();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }




    }
}
