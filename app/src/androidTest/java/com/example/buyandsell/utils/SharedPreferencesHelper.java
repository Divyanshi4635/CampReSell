package com.example.buyandsell.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing SharedPreferences
 */
public class SharedPreferencesHelper {

    private static final String PREFS_NAME = Constants.PREFS_NAME;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Customer data methods (Sequence Diagram step 11: getCustDet())
    public void saveCustomerName(String name) {
        sharedPreferences.edit().putString(Constants.PREFS_USER_NAME, name).apply();
    }

    public String getCustomerName() {
        return sharedPreferences.getString(Constants.PREFS_USER_NAME, "");
    }

    public void saveCustomerEmail(String email) {
        sharedPreferences.edit().putString(Constants.PREFS_USER_EMAIL, email).apply();
    }

    public String getCustomerEmail() {
        return sharedPreferences.getString(Constants.PREFS_USER_EMAIL, "");
    }

    public void setLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(Constants.PREFS_USER_LOGGED_IN, loggedIn).apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(Constants.PREFS_USER_LOGGED_IN, false);
    }

    public void clearUserData() {
        sharedPreferences.edit()
                .remove(Constants.PREFS_USER_NAME)
                .remove(Constants.PREFS_USER_EMAIL)
                .remove(Constants.PREFS_USER_LOGGED_IN)
                .apply();
    }
}