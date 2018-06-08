package com.example.cnwlc.memo.App.read;

import android.app.Service;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;

import com.example.cnwlc.memo.Common.BaseActivity;
import com.example.cnwlc.memo.R;

public class ReadActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_read;
    }
}
