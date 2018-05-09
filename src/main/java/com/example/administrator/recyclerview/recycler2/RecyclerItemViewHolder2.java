package com.example.administrator.recyclerview.recycler2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.recyclerview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerItemViewHolder2 extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerFragment2TextViewName)
    TextView tvRecyclerFragment2Name;
    @BindView(R.id.RecyclerFragment2CheckBox)
    CheckBox cbRecyclerFragment2;

    public RecyclerItemViewHolder2(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
