package com.example.administrator.recyclerview.recycler1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.recyclerview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerItemViewHolder1 extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerFragment1TextViewName)
    TextView tvRecyclerFragment1Name;
    @BindView(R.id.RecyclerFragment1CheckBox)
    CheckBox cbRecyclerFragment1;

    public RecyclerItemViewHolder1(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
