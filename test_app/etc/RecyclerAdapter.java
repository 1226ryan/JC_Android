package com.example.administrator.test_app.etc;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerAdapter {
//        extends Recycler.Adapter<Recycler.ViewHolder> {

//    private final int VIEW_ITEM = 1;
//    private final int VIEW_PROG = 0;
//    private ArrayList<UsageLogListItem> arrayListItem = new ArrayList<>();
//
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
//    public UsageLogRecyclerAdapter(OnLoadMoreListener onLoadMoreListener) {
//        this.onLoadMoreListener = onLoadMoreListener;
//    }
//
//    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
//        this.mLinearLayoutManager = linearLayoutManager;
//    }
//
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
//
//    @Override
//    public int getItemViewType(int position) {
//        return arrayListItem.get(position) != null ? VIEW_ITEM : VIEW_PROG;
//    }
//
//    @Override
//    public Recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == VIEW_ITEM) {
//            return new UsageLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usagelog, parent, false));
//        } else {
//            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
//        }
//    }
//
//    public void addAll(ArrayList<UsageLogListItem> lst) {
//        arrayListItem.clear();
//        arrayListItem.addAll(lst);
//        notifyDataSetChanged();
//    }
//
//    public void addItemMore(List<UsageLogListItem> lst) {
//        arrayListItem.addAll(lst);
//        notifyItemRangeChanged(0, arrayListItem.size());
//    }
//
//    @Override
//    public void onBindViewHolder(Recycler.ViewHolder holder, int position) {
//        if (holder instanceof UsageLogViewHolder) {
//            UsageLogListItem singleItem = (UsageLogListItem) arrayListItem.get(position);
//            ((UsageLogViewHolder) holder).tvTitleView.setText(singleItem.getStitle());
//            ((UsageLogViewHolder) holder).tvTimeIp.setText(singleItem.getStimeIp());
//        }
//    }
//
//    public void setMoreLoading(boolean isMoreLoading) {
//        this.isMoreLoading = isMoreLoading;
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayListItem.size();
//    }
//
//    public void setProgressMore(final boolean isProgress) {
//        if (isProgress) {
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    arrayListItem.add(null);
//                    notifyItemInserted(arrayListItem.size() - 1);
//                }
//            });
//        } else {
//            arrayListItem.remove(arrayListItem.size() - 1);
//            notifyItemRemoved(arrayListItem.size());
//        }
//    }
}
