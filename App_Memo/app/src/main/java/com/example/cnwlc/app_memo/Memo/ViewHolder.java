package com.example.cnwlc.app_memo.Memo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.cnwlc.app_memo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.memo_item_textView_title)
    TextView tvTitle;
    @BindView(R.id.memo_item_textView_import)
    TextView tvImport;
    @BindView(R.id.memo_item_textView_daily)
    TextView tvDaily;

    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
