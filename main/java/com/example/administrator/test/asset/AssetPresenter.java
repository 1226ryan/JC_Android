package com.example.administrator.test.asset;

import java.util.List;
import java.util.Map;

import co.bitpartner.data.model.AssetRow;
import co.bitpartner.data.model.Fund;
import co.bitpartner.data.source.asset.AssetRepository;
import co.bitpartner.util.AssetUtil;


public class AssetPresenter extends AssetBasePresenter {

    private AssetContract.View view;

    public AssetPresenter(AssetRepository assetRepository, AssetContract.View view) {
        super(assetRepository);
        this.view = view;
    }

    protected void processAsset(AssetRow row) {
        String myAsset = AssetUtil.getMyAsset(row);
        Map<String, String> allProfitMap = AssetUtil.getAllProfit (row.getAllAsset());
        List<Fund> funds = row.getFunds();
        List<Map<String, String>> coins = AssetUtil.getMyCoin(row);

        processMyAsset(myAsset);
        processTotalProfitAsset(allProfitMap);
        processFunds(funds);
        processCoins(coins);
    }

    private void processCoins(List<Map<String, String>> coins) {
        view.showCoin(coins);
    }

    private void processFunds(List<Fund> funds) {
        if (funds.size() != 0) {
            List<Map<String, String>> mapFunds = AssetUtil.setMyFund(funds);

            view.initFund();
            view.showFund(mapFunds);
        } else
            view.hideFund();
    }

    private void processTotalProfitAsset(Map<String, String> allProfitMap) {
        double todayProfit = Double.parseDouble(allProfitMap.get("today_won")) / currencyPrice;
        double weekProfit= Double.parseDouble(allProfitMap.get("week_won")) / currencyPrice;
        double monthProfit = Double.parseDouble(allProfitMap.get("month_won")) / currencyPrice;
        double yearProfit = Double.parseDouble(allProfitMap.get("year_won")) /currencyPrice;

        view.showTodayProfit(allProfitMap.get("today_profit"), todayProfit, currency);
        view.showWeekProfit(allProfitMap.get("week_profit"), weekProfit, currency);
        view.showMonthProfit(allProfitMap.get("month_profit"), monthProfit, currency);
        view.showYearProfit(allProfitMap.get("year_profit"), yearProfit, currency);
    }

    private void processMyAsset(String asset) {
        view.showCurrency(currency);

        if (asset.equals("0")) {
            view.showEmptyAsset();
        } else {
            double myAsset = Double.parseDouble(asset) / currencyPrice;
            view.showAsset(myAsset);
        }
    }
}
