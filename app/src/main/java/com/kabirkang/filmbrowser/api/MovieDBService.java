package com.kabirkang.filmbrowser.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kabir on 12/1/2016.
 */

public interface MovieDBService {
    @GET("movie/top_rated?api_key=e835809f980454bba7103c260b923fe4")
    Call<JsonObject> listTopRatedFilms();
}
