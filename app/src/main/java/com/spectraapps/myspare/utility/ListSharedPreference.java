package com.spectraapps.myspare.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mahmo on 3/2/2018.
 */

public class ListSharedPreference {

    public static class Set {
        Context context;

        public Set(Context context) {
            this.context = context;
        }

        public void setFirstRun(boolean isFirstRun) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putBoolean("isFirstRun", isFirstRun).apply();
        }

        public void setLanguage(String lang) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("lang", lang).apply();
        }

        public void setLoginStatus(boolean isLoggedIn) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putBoolean("islogin", isLoggedIn).apply();
        }

        public void setUId(String id) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("uid", id).apply();
        }

        public void setUName(String name) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("uname", name).apply();
        }

        public void setEmail(String email) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("email", email).apply();
        }

        public void setToken(String token) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("token", token).apply();
        }

        public void setMobile(String mobile) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("mobile", mobile).apply();
        }

        public void setImage(String image) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("image", image).apply();
        }

        public void setCategory(String category)
        {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("category", category).apply();
        }

        public void setFav(String key,String val)
        {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString(key, val).apply();
        }

        public void setKeyFilter(int keyFilter)
        {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putInt("keyFilter", keyFilter).apply();
        }

        public void setCategoryName(String categoryName) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("categname", categoryName).apply();
        }
        public void setimg1(String img1) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("getImg1", img1).apply();
        }
        public void setimg2(String img2) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("getImg2", img2).apply();
        }
    }//end Set


    public static class Get
    {
        Context context;

        public Get(Context context) {
            this.context = context;
        }

        public int getKeyFilter()
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getInt("keyFilter", 0);
        }

        public String getFav(String fav)
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString(fav, "false");
        }

        public String getCategoryName()
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("categname", "Category");
        }
        public boolean getFirstRun()
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getBoolean("isFirstRun", true);
        }

        public String getLanguage() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("lang", "ar");
        }

        public boolean getLoginStatus() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getBoolean("islogin", false);
        }

        public String getUId() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("uid", "id");
        }

        public String getUName() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("uname", "agent");
        }

        public String getEmail() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("email", "example@domain.com");
        }

        public String getToken() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("token", "123");
        }

        public String getMobile() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("mobile", "0123456789");
        }

        public String getImage() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("image", "http://myspare.net/public/upload/default.jpg");
        }

        public String getImg1() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("getImg1", "http://myspare.net/public/upload/default.jpg");
        }

        public String getImg2() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("getImg2", "http://myspare.net/public/upload/default.jpg");
        }

        public String getCategory() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("category", "1");
        }
    }//end Get

}//end ListSharedPreference
