package com.example.cnwlc.memo.Common;

import android.util.Log;

import com.example.cnwlc.memo.MemoApplication;

/**
 * Created by Bridge on 2018-05-06.
 */


public class Dlog {
    static final String TAG = MemoApplication.class.getSimpleName();

    public static final void e(String message) {
        if (MemoApplication.DEBUG) Log.e(TAG, buildLogMsg(message));
    }

    public static final void w(String message) {
        if (MemoApplication.DEBUG) Log.w(TAG, buildLogMsg(message));
    }

    public static final void i(String message) {
        if (MemoApplication.DEBUG) Log.i(TAG, buildLogMsg(message));
    }

    public static final void d(String message) {
        if (MemoApplication.DEBUG) Log.d(TAG, buildLogMsg(message));
    }

    public static final void v(String message) {
        if (MemoApplication.DEBUG) Log.v(TAG, buildLogMsg(message));
    }

    public static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);
        return sb.toString();
    }

}
