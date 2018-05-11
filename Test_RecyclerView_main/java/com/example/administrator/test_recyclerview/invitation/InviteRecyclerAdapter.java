package com.example.administrator.test_recyclerview.invitation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import co.bitpartner.R;

public class InviteRecyclerAdapter extends RecyclerView.Adapter<InviteViewHolder> {

    private List<InviteItem> itemList;

    public InviteRecyclerAdapter(List<InviteItem> inviteItems) {
        this.itemList = inviteItems;
    }

    @Override
    public InviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invite, parent, false);
        return new InviteViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(InviteViewHolder holder, int position) {
        final int pos = position;

        holder.tvName.setText(itemList.get(position).getName());
        holder.cbSelect.setChecked(itemList.get(position).isChecked());
        holder.cbSelect.setTag(itemList.get(position));

        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                InviteItem inviteItem = (InviteItem) cb.getTag();

                inviteItem.setChecked(cb.isChecked());
                itemList.get(pos).setChecked(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<InviteItem> getItemList() {
        return itemList;
    }
}
