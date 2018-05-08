package com.example.administrator.test.fund;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.FundAccumuldateBTC;
import co.bitpartner.data.model.FundList;

public interface FundContract {

    interface View extends BaseView<Presenter> {
        void showAllFundInvest(FundAccumuldateBTC fundAccumuldateBTC, double countryCurrencyPrice);
        void showEmptyFundMsg();
        void showRecommandFund(List<FundList> funds);
        void showNotice(List<String> list);
        void showFundMenuCheck(MenuItem item);
        void showProgressbar();
        void hideProgressbar();
    }

    interface Presenter extends BasePresenter {
        void processNoticeTimeDelay(List<String> notices);
        void saveCloseTime();
        void processFundMenuCheck(Menu menu);

        void stop();
    }

}
