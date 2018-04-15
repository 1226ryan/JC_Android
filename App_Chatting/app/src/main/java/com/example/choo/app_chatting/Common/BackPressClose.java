package com.example.choo.app_chatting.Common;

import android.app.Activity;
import android.widget.Toast;

public class BackPressClose {
    private Activity activity;
    private float pressedTime;

    public BackPressClose(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if(pressedTime == 0) {
            pressedTime = System.currentTimeMillis();
            Toast.makeText(activity, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
        } else {
            int second = (int)(System.currentTimeMillis() - pressedTime);

            if(second > 2000) pressedTime = 0;
            else activity.finish();
        }
    }
}
