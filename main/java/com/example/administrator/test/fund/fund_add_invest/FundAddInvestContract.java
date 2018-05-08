package com.example.administrator.test.fund.fund_add_invest;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.FundDetailRow;

public interface FundAddInvestContract {

    interface View extends BaseView<Presenter> {
        void showFailureMessage(String msg);
        void showFundInfo(FundDetailRow row, String fundStartEndDate, String fundCurrentInvestMoney, String fundID, double fundInvestPossibleHoldingMoney, String currency, double countryCurrencyPrice);

        void hideKeyboard();
        void hideBottomDialog();
        void showBottomDialog(double aDouble, double coinKrw, double joinFee);

        void showSuccessMessage(String msg);
        void startFundAddSuccessActivity(String name, String currency, String etMoney, float joinFee, int curPrice);

        void showProgressbar();

        void hideProgressbar();

        void enableInvestButton();
        void showToast(String message);
    }

    interface Presenter extends BasePresenter {
        void setFund(FundDetailRow row, String fundStartEndDate, String fundCurrentInvestMoney, String fundID);
        void processInvestAdd(String etMoney);
        void addFund();
    }
}
