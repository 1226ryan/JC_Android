package com.unisight.unisight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class DynamicListViewAdapter extends ArrayAdapter<DynamicListViewItem> {
    private ArrayList<DynamicListViewItem> data;
    private Context context;

    private class ViewHolder {
        TextView title;
        TextView content;
    }

    DynamicListViewAdapter(Context context, ArrayList<DynamicListViewItem> data) {
        super(context, R.layout.layout_dynamic_listview_item, data);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DynamicListViewItem item = getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        View view;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_dynamic_listview_item, parent, false);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.content = convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
            view = convertView;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        if (item != null) {
            viewHolder.title.setText(item.getTitle());
            viewHolder.content.setText(item.getContent());

            if (item.getContent().equals("")) {
                viewHolder.content.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
