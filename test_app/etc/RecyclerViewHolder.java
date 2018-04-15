package com.example.administrator.test_app.etc;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.test_app.R;

public class RecyclerViewHolder extends Recycler.ViewHolder {

    @BindView(R.id.textView_titleView)
    TextView tvTitleView;
    @BindView(R.id.textView_time_ip)
    TextView tvTimeIp;

    public UsageLogViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
