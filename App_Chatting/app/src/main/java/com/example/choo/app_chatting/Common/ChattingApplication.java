package com.example.choo.app_chatting.Common;

import android.app.Application;
import android.content.res.Configuration;

public class ChattingApplication extends Application{
    private static ChattingApplication instance;
    public static ChattingApplication getInstance() {
        if(instance == null)
            instance = new ChattingApplication();

        return instance;
    }

    public static String userId;

    @Override
    public void onCreate() {    // 앱 생성될 때 호출됩니다. 모든 상태변수와 리소스 초기화 로직을 이곳에서 관리합니다.
        super.onCreate();
    }

    @Override
    public void onLowMemory() { // 시스템의 리소스가 부족할 때 발생
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {   // 컴포넌트가 실행되는 동안 기기의 설정 정보가 변경될 때 시스템에서 호출
        super.onConfigurationChanged(newConfig);
    }
}
