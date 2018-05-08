package com.example.administrator.test.usage_log;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-04-12.
 */

public class UsageLogRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private ArrayList<UsageLogListItem> arrayListItem = new ArrayList<>();

    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int firstVisibleItem;   // 처음 보이는 아이템의 번호
    private int lastVisibleItem;    // 마지막에 보이는 아이템의 번호
    private int visibleItemCount;   // 보이는 아이템들의 개수
    private int totalItemCount;     // 전체 아이템 개수

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public UsageLogRecyclerAdapter(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView) {
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();

                if (!isMoreLoading && (totalItemCount) <= (firstVisibleItem + visibleItemCount)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return arrayListItem.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new UsageLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usagelog, parent, false));
        } else {
            return new UsageLogViewHolderProgress(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }
    }

    public void addAll(List<UsageLogListItem> lst) {
        arrayListItem.clear();
        arrayListItem.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<UsageLogListItem> lst) {
        arrayListItem.addAll(lst);
        notifyItemRangeChanged(0, arrayListItem.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UsageLogViewHolder) {
            UsageLogListItem singleItem = (UsageLogListItem) arrayListItem.get(position);

            ((UsageLogViewHolder) holder).tvTitleView.setText(singleItem.getStitle());
            ((UsageLogViewHolder) holder).tvTimeIp.setText(singleItem.getStimeIp());
        }
    }
    /** instanceof  ( 객체 + instanceof + 클래스 )
     * 객체 타입을 확인하는데 사용
     * 속성은 연산자, 형변환이 가능한지 해당 여부를 true / false로 나타냄.
     * 주로 부모 객체인지 자식 객체인지 확인하는데 씀
     *
     * ex_1) 자녀객체 instanceof 부모타입 == true
     * ex_2) 부모객체 instanceof 자녀타입 == false
     * ex_3) 객체 instanceof 객체타입 == true
     */

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return arrayListItem.size();
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    arrayListItem.add(null);
                    notifyItemInserted(arrayListItem.size() - 1);
                }
            });
        } else {
            arrayListItem.remove(arrayListItem.size() - 1);
            notifyItemRemoved(arrayListItem.size());
        }
    }
}