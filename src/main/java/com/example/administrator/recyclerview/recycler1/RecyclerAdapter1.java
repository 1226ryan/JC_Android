package com.example.administrator.recyclerview.recycler1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.administrator.recyclerview.R;

import java.util.List;

public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerItemViewHolder1> {
    private List<RecyclerItem1> recyclerItem1List;

    public RecyclerAdapter1(List<RecyclerItem1> recyclerItem1List) {
        this.recyclerItem1List = recyclerItem1List;
    }

    public List<RecyclerItem1> getRecyclerItem1List() {
        return recyclerItem1List;
    }

    @Override
    public RecyclerItemViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_fragment_1, parent, false);
        return new RecyclerItemViewHolder1(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder1 holder, int position) {
        final int pos = position;

        holder.tvRecyclerFragment1Name.setText(recyclerItem1List.get(position).getName());
        holder.cbRecyclerFragment1.setChecked(recyclerItem1List.get(position).isChecked());
        holder.cbRecyclerFragment1.setTag(recyclerItem1List.get(position));

        holder.cbRecyclerFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                RecyclerItem1 recyclerItem1 = (RecyclerItem1) checkBox.getTag();

                recyclerItem1.setChecked(checkBox.isChecked());
                recyclerItem1List.get(pos).setChecked(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerItem1List.size();
    }
}
