package com.spectraapps.myspare.helper;

import com.spectraapps.myspare.model.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by MahmoudAyman on 23/01/2018.
 */

public interface Api {

    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("api/{email}/{password}")
    Call<Login> authenticate(@Path("email") String email, @Path("password") String password);

    @POST("api/{email}/{password}")
    Call<Login> registration(@Path("email") String email, @Path("password") String password);
}
