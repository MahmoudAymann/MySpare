package com.spectraapps.myspare.api;

import com.spectraapps.myspare.model.AddModel;
import com.spectraapps.myspare.model.BrandsModel;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.CurrencyModel;
import com.spectraapps.myspare.model.LoginModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.model.ModelsModel;
import com.spectraapps.myspare.model.ProductsModel;
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
    @POST("resetPassword")
    Call<ResetPasswordModel> resetPassword(@Field("mail") String mail);

    @FormUrlEncoded
    @POST("countries")
    Call<CountriesModel> countries(@Field("language") String language);

    @FormUrlEncoded
    @POST("manufacturingCountries")
    Call<ManufacturerCountriesModel> manufacturerCountries(@Field("language") String language);

    @FormUrlEncoded
    @POST("brands")
    Call<BrandsModel> brands(@Field("language") String language);

    @FormUrlEncoded
    @POST("currencies")
    Call<CurrencyModel> currency(@Field("language") String language);

    @FormUrlEncoded
    @POST("models")
    Call<ModelsModel> models(@Field("bid") String bid);

    @FormUrlEncoded
    @POST("add.php")
    Call<AddModel> add(@Field("id") String id,
                       @Field("name") String name,
                       @Field("number") String number,
                       @Field("manufacturingCountry") String manufacturingCountry,
                       @Field("date") String date,
                       @Field("brand") String brand,
                       @Field("model") String model,
                       @Field("category") String category,
                       @Field("country") String country,
                       @Field("currency") String currency,
                       @Field("price") String price,
                       @Field("image1") String image1,
                       @Field("image2") String image2,
                       @Field("image3") String image3);


    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsAll(@Field("language") String language,@Field("category") String category);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithAll(@Field("id") String id,
                                 @Field("category") String category,
                                 @Field("language") String language,
                                 @Field("brand") String brand,
                                 @Field("model") String model,
                                 @Field("number") String number,
                                 @Field("date") String date,
                                 @Field("country") String country);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithCountry(@Field("id") String id,
                                        @Field("category") String category,
                                        @Field("language") String language,
                                        @Field("country") String country);



}