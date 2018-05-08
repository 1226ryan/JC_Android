package com.example.administrator.test.fund.fund_dis_join;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.FundCancelRow;

public interface FundDisjoinContract {

    interface View extends BaseView<Presenter> {
        void showFailureMessage(String msg);
        void showCheckDialog();
        void hideCheckDialog();
        void startAuthActivity();

        void showCancelBTCInfo(FundCancelRow row);
        void showCancelKRWInfo(int jointQuantityCurrency, int fundQuantityCurrency, int benefitFeeCurrency, int dropFitCurrency, int realQuantityCurrency);

        void showCancelUSDInfo(double jointQuantityCurrency, double fundQuantityCurrency, double benefitFeeCurrency, double dropFitCurrency, double realQuantityCurrency, String currency);
    }

    interface Presenter extends BasePresenter {
        void setFundID(String fundID);
        void processFundDisjoin();
        void processDialogAction(int type);

        void unsubscribe();
    }

}
