package com.example.choo.project_study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.basic_relative_layout_back})
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.basic_relative_layout_back :
                finish();
                break;
        }
    }
}
