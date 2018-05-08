package com.example.administrator.test.market;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import co.bitpartner.data.model.GroupPreCoin;
import co.bitpartner.data.source.market.MarketRepository;
import co.bitpartner.data.source.market.MarketSource;

import static co.bitpartner.app.BottomNavigationBaseActivity.oldId;

public class MarketPresenter implements MarketContract.Presenter {

    private static final String TAG = MarketPresenter.class.getSimpleName();

    private MarketRepository marketRepository;
    private MarketContract.View view;

    public MarketPresenter(MarketRepository marketRepository, MarketContract.View view) {
        this.marketRepository = marketRepository;
        this.view = view;
    }

    @Override
    public void changeScrollState(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
            view.scrollDragging();
        else if (newState == RecyclerView.SCROLL_STATE_IDLE)
            view.scrollIdle();
    }

    @Override
    public void start() {
        connectSocket();
    }

    @Override
    public void processNavigationBarState(int selectedMenuId) {
        if (oldId == selectedMenuId)
            view.setSelectedBottomId(selectedMenuId);
    }

    @Override
    public void connectSocket() {
        marketRepository.loadMarketInfo(new MarketSource.LoadInfoCallback() {
            @Override
            public void onSocketConnected(String on) {
                view.socketConnected(on);
            }

            @Override
            public void onMarketInfoLoaded(List<GroupPreCoin> marketInfos) {
                view.showMarketInfo(marketInfos);
            }
        });
    }

    @Override
    public void stop() {
        disconnectSocket();
    }

    @Override
    public void disconnectSocket() {
        marketRepository.disconnectMarketSocket();
    }
}
