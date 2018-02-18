package com.spectraapps.myspare.api;

import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.LoginModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.model.RegisterModel;
import com.spectraapps.myspare.model.ResetPasswordModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MahmoudAyman on 23/01/2018.
 */

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> login(@Field("mail") String email,
                           @Field("password") String password,
                           @Field("token") String token);

    @FormUrlEncoded
    @POST("register")
    Call<RegisterModel> register(@Field("name") String name,
                                 @Field("mail") String email,
                                 @Field("mobile") String mobile,
                                 @Field("password") String password,
                                 @Field("token") String token);

    @FormUrlEncoded
    @POST("categories")
    Call<CategoriesModel> categories(@Field("language") String language);

    @FormUrlEncoded
    @POST("categories")
    Call<ResetPasswordModel> resetPassword(@Field("mail") String mail);

    @FormUrlEncoded
    @POST("countries")
    Call<CountriesModel> countries(@Field("language") String language);

    @FormUrlEncoded
    @POST("manufacturingCountries")
    Call<ManufacturerCountriesModel> manufacturerCountries(@Field("language") String language);



}