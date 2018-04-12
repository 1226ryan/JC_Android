package com.example.cnwlc.app_memo;

import android.app.Activity;
import android.widget.Toast;

public class BackPressClose {
    private Activity activity;
    private float pressedTime;

    public BackPressClose(Activity context) {
        this.activity = context;
    }


    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            pressedTime = System.currentTimeMillis();
            Toast.makeText(activity, R.string.back_press , Toast.LENGTH_LONG).show();
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
