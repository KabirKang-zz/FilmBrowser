package com.kabirkang.filmbrowser.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kabir on 12/1/2016.
 */

public interface MovieDBService {
    @GET("movie/top_rated?api_key=e835809f980454bba7103c260b923fe4")
    Call<JsonObject> listTopRatedFilms();

    @GET("movie/popular?api_key=e835809f980454bba7103c260b923fe4")
    Call<JsonObject> listPopularFilms();

    @GET("movie/{id}/videos?api_key=e835809f980454bba7103c260b923fe4")
    Call<JsonObject> getRelatedVideos(@Path("id") String id);

    @GET("movie/{id}/reviews?api_key=e835809f980454bba7103c260b923fe4")
    Call<JsonObject> getReviews(@Path("id") String id);

    @GET("movie/{id}?api_key=e835809f980454bba7103c260b923fe4")
    Call<JsonObject> getMovie(@Path("id") String id);
}
