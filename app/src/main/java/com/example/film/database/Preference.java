package com.example.film.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Preference {

    private static final Gson gson = new Gson();

    private static SharedPreferences getSharePreferences(Context context) {
        String spName = "film";
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public static boolean isFirstTimeOpen(Context context) {
        return getSharePreferences(context).getBoolean("firstTimeOpen", true);
    }

    public static void setOpened(Context context) {
        getSharePreferences(context).edit().putBoolean("firstTimeOpen", false).apply();
    }

    public static void saveUser(Context context, String username, String password) {
        User user = new User(username, password);
        String userString = gson.toJson(user);

        getSharePreferences(context).edit().putString("user", userString).apply();
    }

    public static void clearUser(Context context) {
        getSharePreferences(context).edit().remove("user").apply();
    }

    public static User getUser(Context context) {
        String userString = getSharePreferences(context).getString("user", null);
        if (userString == null) {
            return null;
        }
        return gson.fromJson(userString, User.class);
    }

    public static void setLoginUser(Context context, long id) {
        getSharePreferences(context).edit()
                .putLong("login", id).apply();
    }

    public static int getLoginUser(Context context) {
        return (int) getSharePreferences(context).getLong("login", -1);
    }
}
