package com.example.cnwlc.app_memo.Common;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

public class MemoApplication extends Application {
    private static MemoApplication instance;

    public static MemoApplication getInstance() {
        if(instance == null)
            instance = new MemoApplication();

        return instance;
    }


    public String userId;
    public static boolean DEBUG;

    @Override
    public void onCreate() {    // 앱 생성될 때 호출됩니다. 모든 상태변수와 리소스 초기화 로직을 이곳에서 관리합니다.
        super.onCreate();

        this.DEBUG = isDebuggable(this);
    }

    @Override
    public void onLowMemory() { // 시스템의 리소스가 부족할 때 발생
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {   // 컴포넌트가 실행되는 동안 기기의 설정 정보가 변경될 때 시스템에서 호출
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 현재 디버그모드여부를 리턴
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
        }

        return debuggable;
    }
}
