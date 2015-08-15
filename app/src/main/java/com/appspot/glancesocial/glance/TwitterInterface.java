package com.appspot.glancesocial.glance;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by fbueti on 8/11/15.
 */
public interface TwitterInterface {

    @GET("/1.1/favorites/list.json")
    List<FavoriteResponse> getFavoritesList(
            @Query("count") int numFavorites
    );

    @GET("/1.1/account/verify_credentials.json")
    UserTwitterResponse getUserCredentials(
            @Query("id_str") String userID
    );

}