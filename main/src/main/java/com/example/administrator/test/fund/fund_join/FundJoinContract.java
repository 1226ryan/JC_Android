package com.example.administrator.test.fund.fund_join;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.FundFee;

public interface FundJoinContract {

    interface View extends BaseView<Presenter> {
        void showJoinInfo(String fundName, String fundCurrency, String fundDuration, double privateAsset, String minCount, String maxCount);
        void showFailureMessage(String msg);
        void showBottomDialog(double btc, double fundKrw, FundFee fundFee);
        void startFundJoinSuccessActivity(double btcInvest, String fundName, String fundCurrency, double fundFee, double coinKrw);

        void showProgressbar();

        void hideProgressbar();

        void enableInvestButton();
    }

    interface Presenter extends BasePresenter {
        void setFundJoinInfo(String fundID, String fundName, String fundCurrency, String fundStartDate, String fundEndDate,
                             double fundMax, double fundPre, int fundCurpice, FundFee fundFee, double privateAsset, double minJoinCnt, double coinKrw);
        void processDialogAction(String length);
        void joinFund();
    }
}
