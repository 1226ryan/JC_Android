package restaurant.study.com.test_folder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import restaurant.study.com.test_folder.Item;
import restaurant.study.com.test_folder.R;
import restaurant.study.com.test_folder.viewHolder.MultiSelectionVIewHolder;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionVIewHolder> {

    private List<Item> itemModels;
    private Context context;

    public MultiSelectionAdapter(Context context, List<Item> itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @NonNull
    @Override
    public MultiSelectionVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_multi, viewGroup, false);
        return new MultiSelectionVIewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiSelectionVIewHolder holder, int position) {
        Item model = itemModels.get(position);
        initializeViews(model, holder, position);
    }


    private void initializeViews(final Item model, final MultiSelectionVIewHolder holder, int position) {
        holder.name.setText(model.getName());
        holder.checkBox.setChecked(model.isSelected());
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                int clickedPos = (Integer) cb.getTag();
                itemModels.get(clickedPos).setSelected(cb.isChecked());
                notifyDataSetChanged();
            }
        });
    }

    public List<Item> getSelectedItem() {
        List<Item> itemModelList = new ArrayList<>();

        for (int i = 0; i < itemModels.size(); i++) {
            Item itemModel = itemModels.get(i);
            if (itemModel.isSelected()) {
                itemModelList.add(itemModel);
            }
        }
        return itemModelList;
    }
}