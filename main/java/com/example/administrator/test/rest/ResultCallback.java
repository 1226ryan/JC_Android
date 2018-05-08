package com.example.administrator.test.rest;

import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018-02-02.
 */

public interface ResultCallback<T> {
    void onSuccess(@Nullable T t);
    void onFailure(Throwable t);
}
