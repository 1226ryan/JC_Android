package com.example.administrator.test.usage_log;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsageLogViewHolderProgress extends RecyclerView.ViewHolder {
    @BindView(R.id.pBar)
    ProgressBar pBar;

    public UsageLogViewHolderProgress(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
