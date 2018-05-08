package com.example.administrator.test.market;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import co.bitpartner.R;
import co.bitpartner.app.BottomNavigationBaseActivity;
import co.bitpartner.data.model.GroupPreCoin;
import co.bitpartner.data.source.market.MarketRepository;
import co.bitpartner.util.SharedPreferenceUtil;

public class MarketActivity extends BottomNavigationBaseActivity implements MarketContract.View {
    private static final String TAG = MarketActivity.class.getSimpleName();

    @BindView(R.id.bottom_navi)
    BottomNavigationView bottomNavi;

    @BindView(R.id.market_recycler)
    RecyclerView marketRecycler;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private MarketRecyclerAdapter marketAdapter;
    private boolean uiBlocking = false;
    private MarketPresenter presenter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MarketPresenter(MarketRepository.getInstance(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
        presenter.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setRecyclerView();
        presenter.start();
        presenter.processNavigationBarState(getNavigationMenuItemId());
    }

    private void setRecyclerView() {
        marketRecycler.getRecycledViewPool().clear();
        MarketLinearLayoutManager layoutManager = new MarketLinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        marketRecycler.setLayoutManager(layoutManager);
    }

    @Override
    public void setSelectedBottomId(int selectedMenuId) {
        bottomNavi.setSelectedItemId(selectedMenuId);
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.market;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_market;
    }

    @Override
    public void initView() {
        marketAdapter = new MarketRecyclerAdapter(SharedPreferenceUtil.getInstance().getTradesOrder(), this, marketRecycler, progressBar);
        marketRecycler.setAdapter(marketAdapter);
        marketRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.changeScrollState(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void setPresenter(MarketContract.Presenter presenter) { }

    @Override
    public void scrollDragging() {
        uiBlocking = true;
    }

    @Override
    public void scrollIdle() {
        uiBlocking = false;
    }

    @Override
    public void socketConnected(String on) {
        Log.i(TAG, on);
    }

    @Override
    public void showMarketInfo(List<GroupPreCoin> marketInfos) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                marketAdapter.setMarkets(marketInfos, uiBlocking);
            }
        });
    }
}
