package com.example.cnwlc.test_paging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cnwlc on 2018-02-18.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    // listDatas은 기존의 데이터이며 filteredItemList는 필터링된 리스트뷰의 내용을 담는다.
    // 초기(검색을 하지 않은 상태)에서는 기존의 listDatas로 초기화 해준다.
    private ArrayList<ListViewItem> listDatas = new ArrayList<>();

    public ListViewAdapter(Context context, ArrayList<ListViewItem> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    // 리스트 뷰의 전체 아이템의 수
    @Override
    public int getCount() {
        return listDatas.size();
    }

    // 리스트뷰의 포지션에 맞는 아이템을 보여줌
    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    // 리스트뷰의 포지션에 맞는 아이템의 아이디를 보여줌
    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView t1;
        TextView t3;
        TextView t4;
    }
    // data set안에 특정 position의 data가 있는 View를 얻는 것이며, 아이템과 xml을 연결하여 화면에 표시해주는 부분.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);

            holder = new ViewHolder();
            holder.t1 = (TextView) convertView.findViewById(R.id.titleView);
            holder.t3 = (TextView) convertView.findViewById(R.id.dailyView);
            holder.t4 = (TextView) convertView.findViewById(R.id.impView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.t1.setText(listDatas.get(position).getStitle());
        holder.t3.setText(listDatas.get(position).getSdaily());
        holder.t4.setText(listDatas.get(position).getSimport());

        return convertView;
    }
}