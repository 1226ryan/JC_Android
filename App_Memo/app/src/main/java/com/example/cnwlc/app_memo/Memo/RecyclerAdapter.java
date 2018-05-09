package com.example.cnwlc.app_memo.Memo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cnwlc.app_memo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Item> arrayListItem = new ArrayList<>();

//    private OnLoadMoreListener onLoadMoreListener;
//    private LinearLayoutManager mLinearLayoutManager;
//
//    private boolean isMoreLoading = false;
//    private int firstVisibleItem;   // 처음 보이는 아이템의 번호
//    private int lastVisibleItem;    // 마지막에 보이는 아이템의 번호
//    private int visibleItemCount;   // 보이는 아이템들의 개수
//    private int totalItemCount;     // 전체 아이템 개수
//
//    public interface OnLoadMoreListener {
//        void onLoadMore();
//    }
//
//    public RecyclerAdapter(OnLoadMoreListener onLoadMoreListener) {
//        this.onLoadMoreListener = onLoadMoreListener;
//    }
//
//    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
//        this.mLinearLayoutManager = linearLayoutManager;
//    }

    public RecyclerAdapter() {

    }

//    public void setRecyclerView(Recycler mView) {
//        mView.addOnScrollListener(new Recycler.OnScrollListener() {
//            @Override
//            public void onScrolled(Recycler recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                visibleItemCount = recyclerView.getChildCount();
//                totalItemCount = mLinearLayoutManager.getItemCount();
//                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
//                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
//
//                Log.d("total", totalItemCount + "");
//                Log.d("visible", visibleItemCount + "");
//
//                Log.d("first", firstVisibleItem + "");
//                Log.d("last", lastVisibleItem + "");
//
////                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
//                if (!isMoreLoading && (totalItemCount) <= (firstVisibleItem + visibleItemCount)) {
//                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore();
//                    }
//                    isMoreLoading = true;
//                }
//            }
//        });
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item singleItem = (Item) arrayListItem.get(position);
        holder.tvTitle.setText(singleItem.getsTitle());
        holder.tvImport.setText(singleItem.getsImport());
        holder.tvDaily.setText(singleItem.getsDaily());
    }

    @Override
    public int getItemCount() {
        return arrayListItem.size();
    }

//    public void setMoreLoading(boolean isMoreLoading) {
//        this.isMoreLoading = isMoreLoading;
//    }

    public void addAll(ArrayList<Item> lst) {
        arrayListItem.clear();
        arrayListItem.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Item> lst) {
        arrayListItem.addAll(lst);
        notifyItemRangeChanged(0, arrayListItem.size());
    }
}
