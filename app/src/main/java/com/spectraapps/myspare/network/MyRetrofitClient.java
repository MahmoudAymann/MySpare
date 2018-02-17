package com.spectraapps.myspare.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MahmoudAyman on 07/02/2018.
 */

public class MyRetrofitClient {

    public static Retrofit getBase()
    {
        return new Retrofit.Builder().baseUrl("http://localhost:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}