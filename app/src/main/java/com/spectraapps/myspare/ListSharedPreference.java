package com.spectraapps.myspare;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mahmo on 3/2/2018.
 */

public class ListSharedPreference {

    public void setFirstLaunch(Context context, boolean isFirstRun) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putBoolean("isFirstRun", isFirstRun).apply();
    }

    public boolean getFirstLaunch(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("isFirstRun", false);
    }

    public void setLanguage(Context context, String lang) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("lang", lang).apply();
    }

    public String getLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("lang", "ar");
    }


    public void setUId(Context context, String id) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("uid", id).apply();
    }

    public void setUName(Context context, String name) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("uname", name).apply();
    }

    public void setEmail(Context context, String email) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("email", email).apply();
    }
    public void setToken(Context context, String token) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("token", token).apply();
    }

    public void setMobile(Context context, String mobile) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString("mobile", mobile).apply();
    }

    public void setLoginStatus(Context context, boolean isLoggedIn) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putBoolean("islogin", isLoggedIn).apply();
    }

    public String getUId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("uid", "1");
    }

    public String getUName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("uname", "agent");
    }
    public String getEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("email", "example@domain.com");
    }
    public String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("token", "123");
    }
    public String getMobile(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("mobile", "0123456789");
    }
    public boolean getLoginStatus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("islogin", false);
    }


}
