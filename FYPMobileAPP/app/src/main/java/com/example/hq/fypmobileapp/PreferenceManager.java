package com.example.hq.fypmobileapp;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {

    public static PreferenceManager instance=null;
    SharedPreferences pref = null;

    public static final String LOGGED_IN = "loggedin";


    public static synchronized PreferenceManager getInstance(){
        if(instance == null){
            instance = new PreferenceManager();
        }
        return instance;
    }


    public void init(Context context){
         pref = context.getSharedPreferences("ApplicationPref", MODE_PRIVATE);
    }

    public void saveString(String key , String value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void saveBoolean(String key , Boolean value){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getString(String key){
        return  pref.getString(key, null);
    }
    public Boolean getBoolean(String key){
        return  pref.getBoolean(key, false);
    }


}
