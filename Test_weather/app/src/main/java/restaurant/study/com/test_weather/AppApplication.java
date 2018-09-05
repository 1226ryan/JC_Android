package restaurant.study.com.test_weather;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class AppApplication extends Application {
    private static AppApplication instance;
    public static AppApplication getInstance() {
        if(instance == null)
            instance = new AppApplication();

        return instance;
    }

    public static boolean DEBUG;

    @Override
    public void onCreate() {    // 앱 생성될 때 호출됩니다. 모든 상태변수와 리소스 초기화 로직을 이곳에서 관리합니다.
        super.onCreate();

        this.DEBUG = isDebuggable(this);
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
