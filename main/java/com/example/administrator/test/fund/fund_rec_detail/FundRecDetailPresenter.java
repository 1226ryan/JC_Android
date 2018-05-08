package com.example.administrator.test.fund.fund_rec_detail;

import android.widget.LinearLayout;
import android.widget.TextView;

import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundAsset;
import co.bitpartner.data.model.FundList;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FundRecDetailPresenter implements FundRecDetailContract.Presenter {

    private FundRecDetailContract.View view;
    private FundRepository fundRepository;
    private FundList fund;
    private double privateAsset;
    private int coinKrw;

    public FundRecDetailPresenter(FundRepository fundRepository, FundRecDetailContract.View view) {
        this.fundRepository = fundRepository;
        this.view = view;
    }

    @Override
    public void setFund(FundList fund) {
        String startDate[] = fund.getStartDate().split(" ");
        String endDate[] = fund.getEndDate().split(" ");

        this.fund = fund;
        this.view.showFundDate(startDate, endDate);
        this.view.showFundDetail(fund, SharedPreferenceUtil.getInstance().getCurrencyPrice(), SharedPreferenceUtil.getInstance().getCountryCurrency());
        this.view.showFeeInfo(fund);

        processFundAsset(fund.getFundAsset());
    }

    private void processFundAsset(FundAsset fundAsset) {
        if (fundAsset != null) {
            Float currentFund = (float) fundAsset.getPreAsset();
            Float day = Float.valueOf(fundAsset.getDayAsset());
            Float week = Float.valueOf(fundAsset.getWeekAsset());
            Float month = Float.valueOf(fundAsset.getMonthAsset());
            Float year = Float.valueOf(fundAsset.getYearAsset());

            if (day != 0) {
                Float dayProfit = ((currentFund-day) / day) * 100;
                view.showDayProfit(dayProfit);
                if (dayProfit > 0)
                    view.showDayProfitUp();
                else if (dayProfit < 0)
                    view.showDayProfitDown();
            }
            else view.showNoDayProfit();

            if (week != 0) {
                Float weekProfit = ((currentFund-week) / week) * 100;
                view.showWeekProfit(weekProfit);
                if (weekProfit > 0)
                    view.showWeekProfitUp();
                else if (weekProfit < 0)
                    view.showWeekProfitDown();
            }
            else view.showNoWeekProfit();

            if (month != 0) {
                Float monthProfit = ((currentFund - month) / month) * 100;
                view.showMonthProfit(monthProfit);
                if (monthProfit > 0)
                    view.showMonthProfitUp();
                else if (monthProfit < 0)
                    view.showMonthProfitDown();
            }
            else view.showNoMonthProfit();

            if (year != 0) {
                Float yearProfit = ((currentFund - year) / year) * 100;
                view.showYearProfit(yearProfit);
                if (yearProfit > 0)
                    view.showYearProfitUp();
                else if (yearProfit < 0)
                    view.showYearProfitDown();

            }
            else view.showNoYearProfit();
        } else view.showNoFundProfit();
    }

    @Override
    public void processFundJoin() {
        double minJoinCount = fund.getMinJoinCnt();
        if (minJoinCount > privateAsset)
            view.showFailureDialog();
        else
            view.startFundJoinActivity(privateAsset, coinKrw, minJoinCount, fund);
    }

    @Override
    public void processFeeInfo(LinearLayout layout) {
        if (!layout.isShown()) {
            view.showFeeInfo(fund);
            view.scrollDown();
        } else
            view.hideFeeInfo();
    }

    @Override
    public void processEtcInfo(TextView warningContent) {
        if (!warningContent.isShown()) {
            view.showWarningInfo(fund.getAdditionalInfo());
            view.scrollDown();
        }
        else
            view.hideWarningInfo();
    }

    @Override
    public void start() {
        loadPrivateAsset();
        if (!SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO))
            view.hideKakao();
    }

    private void loadPrivateAsset() {
        Disposable disposable = fundRepository
                .getPrivateAsset(fund.getCurrency())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        privateAsset = jsonObject.get("row").getAsJsonObject().get("private_assets").getAsDouble();
                        coinKrw = jsonObject.get("row").getAsJsonObject().get("coin_krw").getAsInt();
                    }
                }, throwable -> throwable.printStackTrace());
    }


}
