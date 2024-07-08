package com.example.urban_area_manager.core.component;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_SHARED_FREFERENCES = "MY_SHARED_FREFERENCES";
    private Context mContext;
    public  MySharedPreferences (Context mContext) {
        this.mContext = mContext;
    }
        public void putBoolean (String key,boolean value){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }
        public boolean getBoolean (String key){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                    Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(key, false);
        }

    public void putString (String key,String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString (String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"null");
    }


    public void putInt (String key,int value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int getInt (String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,1);
    }
}
