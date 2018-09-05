package com.unisight.unisight;

import android.support.annotation.Nullable;

/**
 * Created by jc_chu on 2018. 08. 05..
 */

public interface ResultCallback<T> {
    void onSuccess(@Nullable T t);
    void onFailure(Throwable t);
}