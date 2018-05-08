package com.example.administrator.test.asset.asset_profit_state;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;


public interface AssetProfitStateContract {

    interface View extends BaseView<Presenter> {
        void showTodayFund(String day_profit, double todayPrice, String currency);
        void showWeekFund(String week_profit, double weekPrice, String currency);
        void showMonthFund(String month_profit, double monthPrice, String currency);
        void showYearFund(String year_profit, double yearPrice, String currency);

        void showTodayDisjoinFund(String day_profit, double todayPrice, String currency);
        void showWeekDisjoinFund(String week_profit, double weekPrice, String currency);
        void showMonthDisjoinFund(String month_profit, double monthPrice, String currency);
        void showYearDisjoinFund(String year_profit, double yearPrice, String currency);
    }

    interface Presenter extends BasePresenter {
    }

}
