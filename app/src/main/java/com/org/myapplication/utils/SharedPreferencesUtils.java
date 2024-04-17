package com.org.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.collection.SimpleArrayMap;

import com.org.myapplication.MyApplication;
import com.org.myapplication.compenents.MyApplication1;

public final class SharedPreferencesUtils {
    private static final SimpleArrayMap<String, SharedPreferencesUtils> mCaches = new SimpleArrayMap<>();
    private static SharedPreferencesUtils utils;
    private SharedPreferences mSharedPreferences;
    private SharedPreferencesUtils(final String spName, final int mode) {
        mSharedPreferences = MyApplication1.get().getSharedPreferences(spName, mode);
    }

    public static SharedPreferencesUtils getInstance(String spName) {
        if (utils == null) {
            utils = new SharedPreferencesUtils(spName, Context.MODE_PRIVATE);
        }
        return utils;
    }

    public void put(final String key, final String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String get(final String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void remove(final String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
