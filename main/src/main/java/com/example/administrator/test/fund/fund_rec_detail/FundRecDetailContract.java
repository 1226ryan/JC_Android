package com.example.administrator.test.fund.fund_rec_detail;

import android.widget.LinearLayout;
import android.widget.TextView;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.FundList;

public interface FundRecDetailContract {

    interface View extends BaseView<Presenter> {
        void showFundDate(String[] startDate, String[] endDate);
        void showFundDetail(FundList fund, double currencyPrice, String countryCurrency);
        //day
        void showDayProfit(float dayProfit);
        void showNoDayProfit();
        void showDayProfitUp();
        void showDayProfitDown();
        //week
        void showWeekProfit(float weekProfit);
        void showNoWeekProfit();
        void showWeekProfitUp();
        void showWeekProfitDown();
        //month
        void showMonthProfit(float monthProfit);
        void showNoMonthProfit();
        void showMonthProfitUp();
        void showMonthProfitDown();
        //year
        void showYearProfit(float yearProfit);
        void showNoYearProfit();
        void showYearProfitUp();
        void showYearProfitDown();
        //common
        void showNoFundProfit();

        void showFailureDialog();
        void startFundJoinActivity(double privateAsset, double coinKrw, double minJoinCount, FundList fund);

        void hideFeeInfo();
        void showFeeInfo(FundList fund);
        void scrollDown();

        void showWarningInfo(String additionalInfo);
        void hideWarningInfo();

        void hideKakao();
    }

    interface Presenter extends BasePresenter {
        void setFund(FundList fund);
        void processFundJoin();
        void processFeeInfo(LinearLayout layout);
        void processEtcInfo(TextView warningInfo);
    }

}
