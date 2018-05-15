package com.example.cnwlc.memo.Util;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtil {
    public static void shortToast(Activity context, String message) {
        if(context == null || StringUtil.isEmpty(message))
            return;

        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            // ignore
        }
    }

    public static void longToast(Activity context, String message) {
        if(context == null || StringUtil.isEmpty(message))
            return;

        try {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            // ignore
        }
    }
}
