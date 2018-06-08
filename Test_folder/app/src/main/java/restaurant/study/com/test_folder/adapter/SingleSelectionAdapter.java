package restaurant.study.com.test_folder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import restaurant.study.com.test_folder.Item;
import restaurant.study.com.test_folder.R;
import restaurant.study.com.test_folder.viewHolder.SingleSelectionVIewHolder;

public class SingleSelectionAdapter extends RecyclerView.Adapter<SingleSelectionVIewHolder> {

    private List itemModels;
    private Context context;
    private int lastCheckedPosition = -1;

    public SingleSelectionAdapter(Context context, List itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @NonNull
    @Override
    public SingleSelectionVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
        return new SingleSelectionVIewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleSelectionVIewHolder holder, int position) {
        Item model = (Item) itemModels.get(position);
        initializeViews(model, holder, position);
    }


    private void initializeViews(final Item model, final SingleSelectionVIewHolder holder, int position) {
        holder.name.setText(model.getName());
        if (model.getId() == lastCheckedPosition) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                notifyItemRangeChanged(0, itemModels.size());

            }
        });
    }

    public Item getSelectedItem() {
        return (Item) itemModels.get(lastCheckedPosition);
    }

    public int selectedPosition() {
        return lastCheckedPosition;
    }
}