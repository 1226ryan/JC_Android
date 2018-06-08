package restaurant.study.com.test_folder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import restaurant.study.com.test_folder.Item;
import restaurant.study.com.test_folder.R;
import restaurant.study.com.test_folder.viewHolder.MultiMaxSelectViewHolder;

public class MultiMaxSelectionAdapter extends RecyclerView.Adapter<MultiMaxSelectViewHolder> {

    private List<Item> itemModels;
    private Context context;
    private int numberOfCheckboxesChecked = 0;

    public MultiMaxSelectionAdapter(Context context, List<Item> itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    @NonNull
    @Override
    public MultiMaxSelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_multi, viewGroup, false);
        return new MultiMaxSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiMaxSelectViewHolder holder, int position) {
        Item model = itemModels.get(position);
        initializeViews(model, holder, position);
    }

    private void initializeViews(final Item model, final MultiMaxSelectViewHolder holder, int position) {
        holder.textViewName.setText(model.getName());
        holder.checkBox.setChecked(model.isSelected());
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox)view;
                int clickedPos = (Integer) cb.getTag();

                if (cb.isChecked() && numberOfCheckboxesChecked >= 3){
                    cb.setChecked(false);
                    Toast.makeText(context, "Max allowed three checkbox only", Toast.LENGTH_LONG).show();
                } else {
                    if (cb.isChecked()) {
                        numberOfCheckboxesChecked++;
                    } else {
                        numberOfCheckboxesChecked--;
                    }
                    itemModels.get(clickedPos).setSelected(cb.isChecked());
                    notifyDataSetChanged();
                }
            }
        });
    }

    public List<Item> getSelectedItem(){
        List<Item> itemModelList = new ArrayList<>();
        for (int i =0; i < itemModels.size(); i++){
            Item itemModel = itemModels.get(i);
            if (itemModel.isSelected()){
                itemModelList.add(itemModel);
            }
        }
        return itemModelList;
    }
}