package com.example.pat.aapkatrade.general;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PPC21 on 27-Jan-17.
 */

public class AppSharedPreference extends Application {


    public static String app_pref = "aapkatrade";

    public SharedPreferences shared_pref;
    public SharedPreferences.Editor editor;


    public AppSharedPreference(Context c) {
        this.shared_pref = c.getSharedPreferences(app_pref, Activity.MODE_PRIVATE);
        this.editor = shared_pref.edit();
    }


    public String getsharedpref(String pref_key, String default_value) {
        return shared_pref.getString(pref_key, default_value);
    }


    public String getsharedpref(String pref_key) {
        return shared_pref.getString(pref_key, "");
    }

    public void setsharedpref(String pref_key, String text) {
        editor.putString(pref_key, text);
        editor.commit();

    }

}
