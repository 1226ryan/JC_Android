package com.example.administrator.test.usage_log;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.test.settings.SettingsRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Bridge on 2018-05-03.
 */

public class UsageLogActivity2 extends BaseActivity implements UsageLogRecyclerAdapter.OnLoadMoreListener, UsageLogContract.View {

    @BindView(R.id.recycler_view) RecyclerView usageLogRecycler;
    private UsageLogPresenter presenter;
    private UsageLogRecyclerAdapter usageLogRecyclerAdapter = new UsageLogRecyclerAdapter(this);
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    private int page = 0;       // 페이징변수(페이지의 수)
    private int OFFSET = 20;    // 한 페이지마다 로드할 데이터 갯수.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usageLogRecycler.setLayoutManager(mLayoutManager);
        usageLogRecyclerAdapter.setLinearLayoutManager(mLayoutManager);
        usageLogRecyclerAdapter.setRecyclerView(usageLogRecycler);
        usageLogRecycler.setAdapter(usageLogRecyclerAdapter);

        presenter = new UsageLogPresenter(SettingsRepository.getInstance(), this, page, OFFSET);
        presenter.getUsageDetailItem(page);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_usage_log2;
    }

    @Override
    public void onLoadMore() {
        presenter.start();
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        finish();
    }

    @Override
    public void setPresenter(UsageLogContract.Presenter presenter) { }

    @Override
    public void showRecycler(List<UsageLogListItem> list) {
        usageLogRecyclerAdapter.addAll(list);
    }

    @Override
    public void pagingView(int listCount, int OFFSET, List<UsageLogListItem> itemList) {
        if (listCount != 0 && listCount % OFFSET == 0) {
            usageLogRecyclerAdapter.setProgressMore(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    page += 1;

                    usageLogRecyclerAdapter.addItemMore(itemList);
                    usageLogRecyclerAdapter.setMoreLoading(false);
                    usageLogRecyclerAdapter.setProgressMore(false);

                    presenter.getUsageDetailItem(page);
                }
            }, 0000);
        } else {
            usageLogRecyclerAdapter.setMoreLoading(true);
        }
    }
}
