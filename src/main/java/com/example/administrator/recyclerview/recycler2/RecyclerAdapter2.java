package com.example.administrator.recyclerview.recycler2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.administrator.recyclerview.R;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerItemViewHolder2> {
    private List<RecyclerItem2> recyclerItem2List;

    public RecyclerAdapter2(List<RecyclerItem2> recyclerItem2List) {
        this.recyclerItem2List = recyclerItem2List;
    }

    public List<RecyclerItem2> getRecyclerItem2List() {
        return recyclerItem2List;
    }

    @Override
    public RecyclerItemViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_fragment_2, parent, false);
        return new RecyclerItemViewHolder2(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder2 holder, int position) {
        final int pos = position;

        holder.tvRecyclerFragment2Name.setText(recyclerItem2List.get(position).getName());
        holder.cbRecyclerFragment2.setChecked(recyclerItem2List.get(position).isChecked());
        holder.cbRecyclerFragment2.setTag(recyclerItem2List.get(position));

        holder.cbRecyclerFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                RecyclerItem2 recyclerItem2 = (RecyclerItem2) checkBox.getTag();

                recyclerItem2.setChecked(checkBox.isChecked());
                recyclerItem2List.get(pos).setChecked(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerItem2List.size();
    }
}
