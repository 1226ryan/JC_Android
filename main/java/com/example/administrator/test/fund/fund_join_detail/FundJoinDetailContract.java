package com.example.administrator.test.fund.fund_join_detail;

import android.widget.LinearLayout;
import android.widget.TextView;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;
import co.bitpartner.data.model.FundDetailRow;

public interface FundJoinDetailContract {

    interface View extends BaseView<Presenter> {
        void showFundDate(String joinDate, String[] endDate);
        void showFundDetail(FundDetailRow row, String fundJoinCnt, double settingMoney, int currentCurreny, double minMoney, String currency, double countryCurrencyPrice);

        //day
        void showDayProfit(String dayProfit);
        void showDayProfitUp();
        void showDayProfitDown();
        //week
        void showWeekProfit(String weekProfit);
        void showWeekProfitUp();
        void showWeekProfitDown();
        //month
        void showMonthProfit(String monthProfit);
        void showMonthProfitUp();
        void showMonthProfitDown();
        //year
        void showYearProfit(String yearProfit);
        void showYearProfitUp();
        void showYearProfitDown();

        void showFundContent(String info);

        void finishActivity();
        void startFundAddInvestActivity(FundDetailRow row, String fundJoinCnt, String dateStartEnd, String fundID);
        void startFundDisjoinActivity(String fundID);
        void startKakaoConsult();

        void hideFeeInfo();
        void showFeeInfo(FundDetailRow row);
        void scrollDown();

        void showWarningInfo(String additionalInfo);
        void hideWarningInfo();

        void startTelegramConsult();

        void hideKakao();
    }

    interface Presenter extends BasePresenter {
        void setFund(String fundID, String fundJoinCnt, String dayProfit, String weekProfit, String monthProfit, String yearProfit, String startDate);
        void onButtonClicked(int viewId);
        void processFeeInfo(LinearLayout layout);
        void processEtcInfo(TextView warningContent);
    }

}
