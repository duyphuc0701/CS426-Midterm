package com.example.travelapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class UserPreferences {
    public static final String PREFS_NAME = "Profile";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";

    public static final String KEY_PHONE = "phone";

    public static final String KEY_EMAIL = "email";

    public static void saveUserData(@NonNull Context context, String firstName, String lastName, String phone, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
}
