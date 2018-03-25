package com.spectraapps.myspare.api;

import com.spectraapps.myspare.model.AddModel;
import com.spectraapps.myspare.model.AddToFavModel;
import com.spectraapps.myspare.model.BrandsModel;
import com.spectraapps.myspare.model.CategoriesModel;
import com.spectraapps.myspare.model.CountriesModel;
import com.spectraapps.myspare.model.CurrencyModel;
import com.spectraapps.myspare.model.FavouriteModel;
import com.spectraapps.myspare.model.LoginModel;
import com.spectraapps.myspare.model.ManufacturerCountriesModel;
import com.spectraapps.myspare.model.ModelsModel;
import com.spectraapps.myspare.model.NotificationModel;
import com.spectraapps.myspare.model.ProfileProdModel;
import com.spectraapps.myspare.model.UpdatePasswordModel;
import com.spectraapps.myspare.model.UpdateProfileImageModel;
import com.spectraapps.myspare.model.UpdateProfileModel;
import com.spectraapps.myspare.model.inproducts.ProductsAllModel;
import com.spectraapps.myspare.model.inproducts.ProductsModel;
import com.spectraapps.myspare.model.RegisterModel;
import com.spectraapps.myspare.model.ResetPasswordModel;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by MahmoudAyman on 23/01/2018.
 */

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> login(@Field("mail") String email,
                           @Field("password") String password,
                           @Field("token") String token);

    @Multipart
    @POST("updateProfileImage.php")
    Call<UpdateProfileImageModel> uploadProfileImage(@Part("id") RequestBody id,
                                                     @Part MultipartBody.Part file1);


    @FormUrlEncoded
    @POST("updateProfile")
    Call<UpdateProfileModel> updateProfile(@Field("id") String id,
                                           @Field("name") String name,
                                           @Field("mail") String mail,
                                           @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<UpdatePasswordModel> updatePassword(@Field("id") String id,
                                             @Field("oldPassword") String oldPassword,
                                             @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("favorites")
    Call<FavouriteModel> favourite(@Field("id") String email,
                                   @Field("language") String language);

    @FormUrlEncoded
    @POST("addTofavorite")
    Call<AddToFavModel> addToFavourite(@Field("id") String email,
                                       @Field("pid") String pid,
                                       @Field("isFavorite") boolean isFavorite);

    @FormUrlEncoded
    @POST("profile")
    Call<ProfileProdModel> profile(@Field("id") String id,
                                   @Field("language") String language);

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
    @POST("notifications")
    Call<NotificationModel> notification(@Field("id") String id);


    @FormUrlEncoded
    @POST("currencies")
    Call<CurrencyModel> currency(@Field("language") String language);

    @FormUrlEncoded
    @POST("models")
    Call<ModelsModel> models(@Field("bid") String bid);

    @Multipart
    @POST("add.php")
    Call<AddModel> uploadFileOne(@Part("id") RequestBody id,
                              @Part("name") RequestBody name,
                              @Part("number") RequestBody number,
                              @Part("manufacturingCountry") RequestBody manufacturingCountry,
                              @Part("date") RequestBody date,
                              @Part("brand") RequestBody brand,
                              @Part("model") RequestBody model,
                              @Part("category") RequestBody category,
                              @Part("country") RequestBody country,
                              @Part("currency") RequestBody currency,
                              @Part("price") RequestBody price,
                              @Part MultipartBody.Part file2,
                              @Part("image3") RequestBody image3);

    @Multipart
    @POST("add.php")
    Call<AddModel> uploadFileTwo(@Part("id") RequestBody id,
                              @Part("name") RequestBody name,
                              @Part("number") RequestBody number,
                              @Part("manufacturingCountry") RequestBody manufacturingCountry,
                              @Part("date") RequestBody date,
                              @Part("brand") RequestBody brand,
                              @Part("model") RequestBody model,
                              @Part("category") RequestBody category,
                              @Part("country") RequestBody country,
                              @Part("currency") RequestBody currency,
                              @Part("price") RequestBody price,
                              @Part MultipartBody.Part file1,
                              @Part("image3") RequestBody image3);

    @Multipart
    @POST("add.php")
    Call<AddModel> uploadFileBoth(@Part("id") RequestBody id,
                                 @Part("name") RequestBody name,
                                 @Part("number") RequestBody number,
                                 @Part("manufacturingCountry") RequestBody manufacturingCountry,
                                 @Part("date") RequestBody date,
                                 @Part("brand") RequestBody brand,
                                 @Part("model") RequestBody model,
                                 @Part("category") RequestBody category,
                                 @Part("country") RequestBody country,
                                 @Part("currency") RequestBody currency,
                                 @Part("price") RequestBody price,
                                 @Part MultipartBody.Part file1,
                                 @Part MultipartBody.Part file2,
                                 @Part("image3") RequestBody image3);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsAllModel> productsAll(@Field("language") String language,
                                       @Field("category") String category);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithMail(@Field("id") String email,
                                            @Field("language") String language,
                                            @Field("category") String category);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithCountry(@Field("id") String email,
                                            @Field("category") String category,
                                            @Field("language") String language,
                                            @Field("country") String country);



    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithCountryYear(@Field("id") String email,
                                            @Field("category") String category,
                                            @Field("language") String language,
                                            @Field("country") String country,
                                            @Field("date") String year);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithCountrySerial(@Field("id") String email,
                                                @Field("category") String category,
                                                @Field("language") String language,
                                                @Field("country") String country,
                                                @Field("productNumber") String serial);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithYear(@Field("id") String email,
                                            @Field("category") String category,
                                            @Field("language") String language,
                                            @Field("date") String year);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithSerialNum(@Field("id") String email,
                                         @Field("category") String category,
                                         @Field("language") String language,
                                         @Field("productNumber") String serialNum);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithCountryBrandModel(@Field("id") String email,
                                            @Field("category") String category,
                                            @Field("language") String language,
                                            @Field("country") String country,
                                            @Field("brand") String brand,
                                            @Field("model") String model);

    @FormUrlEncoded
    @POST("products")
    Call<ProductsModel> productsWithAll(@Field("id") String email,
                                        @Field("category") String category,
                                        @Field("language") String language,
                                        @Field("brand") String brand,
                                        @Field("model") String model,
                                        @Field("number") String serial,
                                        @Field("date") String date,
                                        @Field("country") String country);


}