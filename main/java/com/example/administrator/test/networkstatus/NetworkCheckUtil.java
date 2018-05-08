package com.example.administrator.test.networkstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018-02-28.
 */

public class NetworkCheckUtil {
    enum Type {
        NOT_CONNECTED,
        WIFI,
        MOBILE
    }

    public static Type getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return Type.WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return Type.MOBILE;
        }
        return Type.NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        Type type = NetworkCheckUtil.getConnectivityStatus(context);

        String status = null;
        if (type == Type.MOBILE)
            status = "mobile data enabled";
        else if (type == Type.WIFI)
            status = "wifi enabled";
        else if (type == Type.NOT_CONNECTED)
            status = "not connected to internet";

        return status;
    }
}
