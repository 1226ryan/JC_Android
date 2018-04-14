package com.example.administrator.test_app.etc;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler {
    private Activity activity;
    private float pressedTime;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }


    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            pressedTime = System.currentTimeMillis();
            Toast.makeText(activity, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                pressedTime = 0 ;
            }
            else {
                activity.finish();
            }
        }
    }
}
