package com.example.cnwlc.app_memo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.invite_textView)
    TextView tvName;
    @BindView(R.id.invite_checkbox)
    CheckBox cbSelect;

    public InviteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}