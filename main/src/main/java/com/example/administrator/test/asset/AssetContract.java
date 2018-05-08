package com.example.administrator.test.asset;

import java.util.List;
import java.util.Map;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;

public interface AssetContract {

    interface View extends BaseView<Presenter> {
        //내 자산
        void showEmptyAsset();
        void showAsset(double asset);

        //총 자산 수익
        void showTodayProfit(String today_profit, double todayProfit, String currency);
        void showWeekProfit(String week_profit, double weekProfit, String currency);
        void showMonthProfit(String month_profit, double monthProfit, String currency);
        void showYearProfit(String year_profit, double yearProfit, String currency);

        //펀드
        void initFund();
        void showFund(List<Map<String, String>> mapFunds);
        void hideFund();

        //보유코인
        void showCoin(List<Map<String, String>> coins);

        void showCurrency(String usd);
    }

    interface Presenter extends BasePresenter {
        void loadAssets();
        void stop();
    }

}
