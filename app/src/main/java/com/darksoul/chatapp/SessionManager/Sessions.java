package com.darksoul.chatapp.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Sessions {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;

    Context context;

    public static final String SESSION_USER="userLoginSession";
    public static final String SESSION_REMEMBER="rememberMe";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME ="name";
    public static final String KEY_EMAIL ="email";
    public static final String KEY_PHONE ="phone";
    public static final String KEY_USERID ="uid";
    public static final String KEY_COUNTRY ="country";
    public static final String KEY_COUNTRYCODE ="countryCode";
    public static final String KEY_GENDER ="gender";
    public static final String KEY_STATUS ="status";

    //for remember me session........
    private static final String IS_REMEMBER = "IsRememberMe";
    public static final String KEY_USERNAME ="username";
    public static final String KEY_PASSWORD ="password";



    public Sessions(Context contextt,String sesson){
        context=contextt;
        userSession=context.getSharedPreferences(sesson,Context.MODE_PRIVATE);
        editor=userSession.edit();
    }

    public void createLoginSession(String name,String email,String phone,String uid, String country,String countryCode, String gender, String status){
        editor.putString(IS_LOGIN,"true");
        editor.putString(KEY_FULLNAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONE,phone);
        editor.putString(KEY_USERID,uid);
        editor.putString(KEY_COUNTRY,country);
        editor.putString(KEY_COUNTRYCODE,countryCode);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_STATUS,status);
        editor.commit();
    }

    public HashMap<String,String> getUserDataFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_FULLNAME,userSession.getString(KEY_FULLNAME,null));
        userData.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
        userData.put(KEY_PHONE,userSession.getString(KEY_PHONE,null));
        userData.put(KEY_USERID,userSession.getString(KEY_USERID,null));
        userData.put(KEY_COUNTRY,userSession.getString(KEY_COUNTRY,null));
        userData.put(KEY_COUNTRYCODE,userSession.getString(KEY_COUNTRYCODE,null));
        userData.put(KEY_GENDER,userSession.getString(KEY_GENDER,null));
        userData.put(KEY_STATUS,userSession.getString(KEY_STATUS,null));

        return userData;
    }

    public boolean checkLogin(){
        if(userSession.getString(IS_LOGIN,null)==null){
            return false;
        }else{
            return true;
        }
    }

    public String getUID(){
        String uid=userSession.getString(KEY_USERID,null);
        return uid;
    }

    public void logOutUserSession(){
        editor.clear();
        editor.commit();
    }

    //for remember me button...........
    public void createRememberMeSession(String username,String password){
        editor.putString(IS_REMEMBER,"true");
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_USERNAME,username);
        editor.commit();
    }

    public HashMap<String,String> getRememberMeFromSession(){
        HashMap<String,String> userData = new HashMap<String,String>();

        userData.put(KEY_USERNAME,userSession.getString(KEY_USERNAME,null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));
        return userData;
    }

    public boolean checkRememberMe(){
        if(userSession.getString(IS_REMEMBER,null)==null){
            return false;
        }else{
            return true;
        }
    }

}
