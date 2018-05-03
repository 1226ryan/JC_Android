package com.example.administrator.test_permission.dialog_country;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.bitpartner.R;

public class DialogUtilCountryAdapter extends RecyclerView.Adapter<DialogUtilCountryAdapter.DialogUtilCountryItemViewHolder> {
    private List<DialogUtilCountryItem> itemList;

    public DialogUtilCountryAdapter(List<DialogUtilCountryItem> inviteItems) {
        this.itemList = inviteItems;
    }

    @Override
    public DialogUtilCountryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_util_dialog_country, parent, false);
        return new DialogUtilCountryItemViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(DialogUtilCountryItemViewHolder holder, int position) {
        holder.tvCountryName.setText(itemList.get(position).getCountryName());

        final int Position = position;
        holder.tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, Position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class DialogUtilCountryItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.country_name_textView)
        TextView tvCountryName;

        public DialogUtilCountryItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }
}