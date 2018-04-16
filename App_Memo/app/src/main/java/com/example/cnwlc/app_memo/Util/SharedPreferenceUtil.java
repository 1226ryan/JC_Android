package com.example.cnwlc.app_memo.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.cnwlc.app_memo.Common.MemoApplication;

public class SharedPreferenceUtil {
    private static Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceUtil(Context context) {
        /**
         * getSharedPreference("name",0); : 쉐어프리퍼런스 저장 폴더에 name.xml 이름의 파일로 저장이 된다. 다른 이름도 얼마든지 가능
         * getSharedPreference(0); : 현재 액티비티의 이름으로 xml 파일이 저장
         * PreferenceManager.getDefaultSharedPreference(this); : 환경설정에 저장된 값으로 쉐어프리퍼런스를 가져오는 방법
         */
        this.context = context;
//        preferences = PreferenceManager.getDefaultSharedPreferences(ChattingApplication.getInstance());
        preferences = context.getSharedPreferences(MemoApplication.userId, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    private static SharedPreferenceUtil instance;

    public static SharedPreferenceUtil getInstance() {
        if (instance == null)
            instance = new SharedPreferenceUtil(context);

        return instance;
    }

//    public void setHashKey(String hashkey) {
//        mEditor.putString(Defines.KEY_HASH_KEY, hashkey);
//        mEditor.commit();
//    }
//
//    public String getHashKey() {
//        return mPref.getString(Defines.KEY_HASH_KEY, "");
//    }
//
//    public void clearHashKey() {
//        mEditor.remove(Defines.KEY_HASH_KEY);
//        mEditor.apply();
//    }
}