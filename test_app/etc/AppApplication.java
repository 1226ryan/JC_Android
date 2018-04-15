package com.example.administrator.test_app.etc;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.administrator.test_app.R;

import java.net.Socket;
import java.net.URISyntaxException;

public class AppApplication extends Application {

    private static BitPartnerApplication instance;
    public static BitPartnerApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
//        setMemoryLeakDectector();
        Typekit.getInstance()
                .addBold(Typekit.createFromAsset(this, "NotoSansKR-Bold-Hestia.otf"))
                .addCustom1(Typekit.createFromAsset(this, "NotoSansKR-Medium-Hestia.otf"))
                .addCustom2(Typekit.createFromAsset(this, "NotoSansKR-Regular-Hestia.otf"));
        registerActivityLifeCycleCallbacks();
        registerDialogEvent();

        MultiDex.install(this);
    }
    private DialogUtil dialogUtil;
    private void registerDialogEvent() {
        RxEventBus.getInstance().getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event-> {
                    Log.d("BitparApp", "on bus event" + currentActivity.getClass().getSimpleName());
                    if (event instanceof Events.DialogFloatEvent) {
                        dialogUtil = DialogUtil.getDialog(currentActivity, getString(R.string.notification), getString(R.string.bitpartner_application_content), ok);
                        dialogUtil.show();
                    }
                });
    }
    private View.OnClickListener ok = new View.OnClickListener() {
        public void onClick(View v) {
            RemoveUtil.removeUserInfo();
            startSplashActivity();

            dialogUtil.dismiss();
        }
    };

    private void startSplashActivity() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

//    private void setMemoryLeakDectector() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//    }

    private Socket mSocket;
    {
        try {
            final IO.Options options = new IO.Options();
            options.transports = new String[]{WebSocket.NAME};
            mSocket = IO.socket(Defines.WEB_SOCKET_URL, options);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    public String getUuid()  {
        try {
            String idByANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            return idByANDROID_ID;
        }
        catch (SecurityException ex)    {ex.printStackTrace();}
        return null;
    }
}
