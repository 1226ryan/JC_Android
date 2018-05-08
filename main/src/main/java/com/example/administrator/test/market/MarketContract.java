package com.example.administrator.test.market;

import java.util.List;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.GroupPreCoin;

public interface MarketContract {

    interface View extends BaseView<Presenter> {
        void scrollDragging();
        void scrollIdle();
        void socketConnected(String on);
        void showMarketInfo(List<GroupPreCoin> marketInfos);

        void setSelectedBottomId(int selectedMenuId);
    }

    interface Presenter extends BasePresenter {
        void changeScrollState(int newState);
        void connectSocket();
        void disconnectSocket();
        void stop();


        void processNavigationBarState(int navigationMenuItemId);
    }
}
