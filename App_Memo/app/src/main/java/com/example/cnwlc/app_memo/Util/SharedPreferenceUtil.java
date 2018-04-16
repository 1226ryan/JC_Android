package com.example.cnwlc.app_memo.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.cnwlc.app_memo.Common.MemoApplication;

public class SharedPreferenceUtil {
    final static MemoApplication app = MemoApplication.getInstance();
    public static final String SP_NAME_DEFAULT = "DEFAULT";
    public static final String SP_NAME_HASHKEY = "HASHKEY";

    private static SharedPreferenceUtil instance;

    public static SharedPreferenceUtil getInstance() {
        if (instance == null)
            instance = new SharedPreferenceUtil();

        return instance;
    }

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private SharedPreferenceUtil() {
        mPref = PreferenceManager.getDefaultSharedPreferences(MemoApplication.getInstance());
        mEditor = mPref.edit();
    }
//
//
//
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
//
//
//
//
//    public void setTradesOrder(List<String> exchangeLists) {
//        mEditor.putInt(Defines.KEY_TRADES_SIZE, exchangeLists.size());
//
//        for(int i=0; i<exchangeLists.size(); i++) {
//            mEditor.remove(Defines.KEY_TRADE_ORDER + i);
//            mEditor.putString(Defines.KEY_TRADE_ORDER + i, exchangeLists.get(i));
//        }
//        mEditor.commit();
//    }
//    public List<String> getTradesOrder() {
//        List<String> tradesOrder = new ArrayList<>();
//        int size = mPref.getInt(Defines.KEY_TRADES_SIZE, 0);
//
//        for(int i=0; i<size; i++)
//            tradesOrder.add(mPref.getString(Defines.KEY_TRADE_ORDER + i, ""));
//
//        return tradesOrder;
//    }
}