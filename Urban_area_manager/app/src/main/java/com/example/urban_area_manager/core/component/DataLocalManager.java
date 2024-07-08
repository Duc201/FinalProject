package com.example.urban_area_manager.core.component;

import android.content.Context;

import com.example.urban_area_manager.utils.Constant;

public class DataLocalManager {
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;
    public static void init (Context context) {

        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setEmail(String email){
        DataLocalManager.getInstance().mySharedPreferences.putString(Constant.NODE_USER_EMAIL,email);
    }
    public static String getEmail(){
        return DataLocalManager.getInstance().mySharedPreferences.getString(Constant.NODE_USER_EMAIL);
    }
    public static void setPass(String pass){
        DataLocalManager.getInstance().mySharedPreferences.putString(Constant.NODE_PASSWORD,pass);
    }
    public static String getPass(){
        return DataLocalManager.getInstance().mySharedPreferences.getString(Constant.NODE_PASSWORD);
    }
    public static void setName(String name){
        DataLocalManager.getInstance().mySharedPreferences.putString(Constant.NODE_USER_NAME,name);
    }
    public static String getName(){
        return DataLocalManager.getInstance().mySharedPreferences.getString(Constant.NODE_USER_NAME);
    }
    public static void setidUser(String id){
        DataLocalManager.getInstance().mySharedPreferences.putString(Constant.NODE_USER_ID,id);
    }
    public static String getIdUser(){
        return DataLocalManager.getInstance().mySharedPreferences.getString(Constant.NODE_USER_ID);
    }
    public static void setidApartment(String id){
        DataLocalManager.getInstance().mySharedPreferences.putString(Constant.NODE_USER_APARTMENT,id);
    }
    public static String getIdApartment(){
        return DataLocalManager.getInstance().mySharedPreferences.getString(Constant.NODE_USER_APARTMENT);
    }
    public static void setLoginSuccess(Boolean isLogin){
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(Constant.NODE_USER_LOGIN,isLogin);
    }
    public static Boolean getLoginSuccess(){
        return DataLocalManager.getInstance().mySharedPreferences.getBoolean(Constant.NODE_USER_LOGIN);
    }

    public static void setPosition(int position){
        DataLocalManager.getInstance().mySharedPreferences.putInt(Constant.NODE_USER_POSITION,position);
    }
    public static int getPosition(){
        return DataLocalManager.getInstance().mySharedPreferences.getInt(Constant.NODE_USER_POSITION);
    }
    public static void setToken(String token){
         DataLocalManager.getInstance().mySharedPreferences.putString(Constant.TOKEN,token);
    }
    public static String getToken(){
        return DataLocalManager.getInstance().mySharedPreferences.getString(Constant.TOKEN);
    }

}
