package com.example.cnwlc.memoapp.Util;

import android.app.Application;
import android.content.Context;

public class DialogUtil extends Application {
    private static DialogUtil instance;
    private String title;
    private String content;

    public static DialogUtil getInstance(Context context, String title, String content) {
        if(instance == null)
            instance = new DialogUtil(context, title, content);

        return instance;
    }

    public DialogUtil(Context context, String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
