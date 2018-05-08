package com.example.administrator.test.networkstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.test.rxbus.Events;
import com.example.administrator.test.rxbus.RxEventBus;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (NetworkCheckUtil.getConnectivityStatus(context).equals(NetworkCheckUtil.Type.NOT_CONNECTED))
                RxEventBus.getInstance().post(new Events.NetworkChangeEvent());
        }
    }
}


/**
 * 매니페스트에 추가
 <receiver
 android:name=".networkstatus.NetworkChangeReceiver"
 android:enabled="true"
 android:exported="false">
 <intent-filter>
 <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
 <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
 </intent-filter>
 </receiver>
 */