package com.example.cnwlc.project_memo;

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
import java.util.Comparator;

public class ListAdapter extends BaseAdapter {

    private Context context;
    // listDatas은 기존의 데이터이며 filteredItemList는 필터링된 리스트뷰의 내용을 담는다.
    // 초기(검색을 하지 않은 상태)에서는 기존의 listDatas로 초기화 해준다.
    private ArrayList<ListItem> listDatas = new ArrayList<>();

    public ListAdapter(Context context, ArrayList<ListItem> listDatas) {
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
        TextView t2;
        TextView t3;
        TextView t4;
        ImageView i1;
    }
    // data set안에 특정 position의 data가 있는 View를 얻는 것이며, 아이템과 xml을 연결하여 화면에 표시해주는 부분.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.t1 = (TextView) convertView.findViewById(R.id.titleView);
            holder.t2 = (TextView) convertView.findViewById(R.id.contentsView);
            holder.t3 = (TextView) convertView.findViewById(R.id.dailyView);
            holder.t4 = (TextView) convertView.findViewById(R.id.impView);
            holder.i1 = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        String str = listDatas.get(position).getSuri();
        Bitmap bm = BitmapFactory.decodeFile(str);

        holder.t1.setText(listDatas.get(position).getStitle());
        holder.t2.setText(listDatas.get(position).getScontent());
        holder.t3.setText(listDatas.get(position).getSdaily());
        holder.t4.setText(listDatas.get(position).getSimport());
        holder.i1.setImageBitmap(bm);

        String Simport = listDatas.get(position).getSimport();
        if ("상".equals(Simport)) {
            holder.t4.setTextColor(Color.RED);
        } else if ("중".equals(Simport)) {
            holder.t4.setTextColor(Color.GREEN);
        } else if ("하".equals(Simport)) {
            holder.t4.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    // 이름순정렬
    Comparator<ListItem> nameAsc = new Comparator<ListItem>() {
        @Override
        public int compare(ListItem o1, ListItem o2) {
            return o1.getStitle().compareTo(o2.getStitle());
        }
    };
    public void setNameAsc() {
        Collections.sort(listDatas, nameAsc);
        this.notifyDataSetChanged();
    }

    // 중요순정렬
    Comparator<ListItem> importAsc = new Comparator<ListItem>() {
        @Override
        public int compare(ListItem o1, ListItem o2) {
            return o1.getSimport().compareTo(o2.getSimport());
        }
    };
    public void setImportAsc() {
        Collections.sort(listDatas, importAsc);
        this.notifyDataSetChanged();
    }
}
