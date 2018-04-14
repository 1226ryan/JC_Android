package com.example.cnwlc.memoapp.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static final String TAG = SharedPreferenceUtil.class.getSimpleName();

    private static SharedPreferenceUtil instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static SharedPreferenceUtil getInstance(Context context) {
        if(instance == null)
            instance = new SharedPreferenceUtil(context);

        return instance;
    }

    public SharedPreferenceUtil(Context context) {
        this.sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

}
