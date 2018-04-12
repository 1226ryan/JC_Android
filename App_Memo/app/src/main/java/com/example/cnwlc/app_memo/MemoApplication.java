package com.example.cnwlc.app_memo;

import android.app.Application;

public class MemoApplication extends Application {
    private static MemoApplication instance;

    public static MemoApplication getInstance() {
        if(instance == null)
            instance = new MemoApplication();

        return instance;
    }
}
