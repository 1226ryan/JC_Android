package com.example.administrator.test_recyclerview.another;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test_recyclerview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiMaxSelectionAdapter extends RecyclerView.Adapter {

    private List itemModels;
    private Context context;
    int numberOfCheckboxesChecked = 0;

    public MultiMaxSelectionAdapter(Context context, List itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_multi, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemModel model = (ItemModel) itemModels.get(position);
        initializeViews(model, holder, position);
    }


    private void initializeViews(final ItemModel model, final RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).name.setText(model.getName());
        ((ItemViewHolder)holder).checkBox.setChecked(model.isSelected());
        ((ItemViewHolder)holder).checkBox.setTag(new Integer(position));
        ((ItemViewHolder)holder).checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox)view;
                int clickedPos = ((Integer)cb.getTag()).intValue();
                if (cb.isChecked() && numberOfCheckboxesChecked >= 3){
                    cb.setChecked(false);
                    Toast.makeText(context, "Max allowed three checkbox only", Toast.LENGTH_LONG).show();
                }else {
                    if (cb.isChecked()) {
                        numberOfCheckboxesChecked++;
                    } else {
                        numberOfCheckboxesChecked--;
                    }
//                    itemModels.get(clickedPos).setSelected(cb.isChecked());
                    notifyDataSetChanged();
                }
            }
        });
    }

    public  List getSelectedItem(){
        List itemModelList = new ArrayList<>();
        for (int i =0; i < itemModels.size(); i++){
            ItemModel itemModel = (ItemModel) itemModels.get(i);
            if (itemModel.isSelected()){
                itemModelList.add(itemModel);
            }
        }
        return itemModelList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.checkbox)
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}