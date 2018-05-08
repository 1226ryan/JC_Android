package com.example.administrator.test.asset.asset_profit_state;


import java.util.Map;

import co.bitpartner.app.asset.AssetBasePresenter;
import co.bitpartner.data.model.AssetRow;
import co.bitpartner.data.source.asset.AssetRepository;
import co.bitpartner.util.AssetUtil;

public class AssetProfitStatePresenter extends AssetBasePresenter {

    private AssetProfitStateContract.View view;

    public AssetProfitStatePresenter(AssetRepository assetRepository, AssetProfitStateContract.View view) {
        super(assetRepository);
        this.view = view;
    }

    @Override
    protected void processAsset(AssetRow row) {
        Map<String, String> fundMap = AssetUtil.getFundProfit(row.getFunds());
        Map<String, String> disjoinFundMap = AssetUtil.getDisjoinFundProfit(row);

        processFundProfit(fundMap);
        processDisjoinFundProfit(disjoinFundMap);
    }

    private void processFundProfit(Map<String, String> fundMap) {
        double todayPrice = Double.parseDouble(fundMap.get("day_won")) / currencyPrice;
        double weekPrice = Double.parseDouble(fundMap.get("week_won")) / currencyPrice;
        double monthPrice = Double.parseDouble(fundMap.get("month_won")) / currencyPrice;
        double yearPrice = Double.parseDouble(fundMap.get("year_won")) / currencyPrice;

        view.showTodayFund(fundMap.get("day_profit"), todayPrice, currency);
        view.showWeekFund(fundMap.get("week_profit"), weekPrice, currency);
        view.showMonthFund(fundMap.get("month_profit"), monthPrice, currency);
        view.showYearFund(fundMap.get("year_profit"), yearPrice, currency);
    }

    private void processDisjoinFundProfit(Map<String, String> disjoinFundMap) {
        double todayPrice = Double.parseDouble(disjoinFundMap.get("day_won")) / currencyPrice;
        double weekPrice = Double.parseDouble(disjoinFundMap.get("week_won")) / currencyPrice;
        double monthPrice = Double.parseDouble(disjoinFundMap.get("month_won")) / currencyPrice;
        double yearPrice = Double.parseDouble(disjoinFundMap.get("year_won")) / currencyPrice;

        view.showTodayDisjoinFund(disjoinFundMap.get("day_profit"), todayPrice, currency);
        view.showWeekDisjoinFund(disjoinFundMap.get("week_profit"), weekPrice, currency);
        view.showMonthDisjoinFund(disjoinFundMap.get("month_profit"), monthPrice, currency);
        view.showYearDisjoinFund(disjoinFundMap.get("year_profit"), yearPrice, currency);
    }
}
