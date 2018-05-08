package com.example.administrator.test.usage_log;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsageLogViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_titleView)
    TextView tvTitleView;
    @BindView(R.id.textView_time_ip)
    TextView tvTimeIp;

    public UsageLogViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
