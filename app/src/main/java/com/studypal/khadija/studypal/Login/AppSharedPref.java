package com.studypal.khadija.studypal.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPref {
    SharedPreferences sharepreferences;
    public static AppSharedPref instance = null;
   /* public static AppSharedPref getInstance() {
        if (instance == null) {
            synchronized (AppSharedPref.class) {
                instance = new AppSharedPref();
            }
        }
        return instance;
    }*/
    public AppSharedPref(Context context) {
        sharepreferences = context.getSharedPreferences("studyPalPref", Context.MODE_PRIVATE);
    }
    /* ------------------ Sharepref keys ------------------------- */
    private static final String KEY_IS_LOGIN = "is_login";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "email";

    /**
     *
     * @param isLoggedin
     */
    public void saveISLogged_IN(boolean isLoggedin) {
        sharepreferences.edit().putBoolean(KEY_IS_LOGIN, isLoggedin).commit();
    }

    /**
     *
     * @return
     */
    public boolean IsLoggedIN() {
        return sharepreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setUserName(String name){
        sharepreferences.edit().putString(KEY_USER_NAME, name).commit();
    }

    public String getUserName() {
        return sharepreferences.getString(KEY_USER_NAME, "");
    }

    public void setEmail(String email) {
        sharepreferences.edit().putString(KEY_USER_EMAIL, email).commit();
    }

    public String getEmail() {
        return sharepreferences.getString(KEY_USER_EMAIL, "");
    }
}
