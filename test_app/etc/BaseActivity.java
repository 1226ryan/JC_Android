package com.example.administrator.test_app.etc;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private BitPartnerApplication instance;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        instance = (BitPartnerApplication) this.getApplicationContext();
        backPressCloseHandler = new BackPressCloseHandler(this);
        setVersionCompatibility();
        checkNetworkStatus();
    }

    private void checkNetworkStatus() {}

    protected abstract int getContentViewId();

    private void setVersionCompatibility() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때
//                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.WHITE);
        }

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(Color.parseColor("#014070"));
//            tintManager.setTintColor(Color.parseColor("#014070"));
        }
    }

    @Override
    public void onBackPressed() {
        if (this instanceof  BottomNavigationBaseActivity)
            backPressCloseHandler.onBackPressed();
        else
            super.onBackPressed();
    }
}

