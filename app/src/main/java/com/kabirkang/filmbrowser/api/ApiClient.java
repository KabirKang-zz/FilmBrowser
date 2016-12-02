package com.kabirkang.filmbrowser.api;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by kabir on 12/1/2016.
 */

public class ApiClient {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
