package restaurant.study.com.test_folder.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import restaurant.study.com.test_folder.R;

public class SingleSelectionVIewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.radio)
    public RadioButton radioButton;

    public SingleSelectionVIewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
