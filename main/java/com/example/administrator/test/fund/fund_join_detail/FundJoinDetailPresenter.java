package com.example.administrator.test.fund.fund_join_detail;

import android.widget.LinearLayout;
import android.widget.TextView;

import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundDetailRow;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.GsonUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FundJoinDetailPresenter implements FundJoinDetailContract.Presenter {

    private FundJoinDetailContract.View view;
    private FundRepository fundRepository;

    private String fundID;
    private String fundJoinCnt;
    private String dayProfit;
    private String weekProfit;
    private String monthProfit;
    private String yearProfit;
    private String dateStartEnd;
    private String joinDate;
    private FundDetailRow row;

    protected String currency;
    protected double countryCurrencyPrice;

    public FundJoinDetailPresenter(FundRepository fundRepository, FundJoinDetailContract.View view) {
        this.view = view;
        this.fundRepository = fundRepository;

        this.currency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        this.countryCurrencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
    }

    @Override
    public void setFund(String fundID, String fundJoinCnt,
                        String dayProfit, String weekProfit, String monthProfit, String yearProfit,
                        String startDate) {

        this.fundID = fundID;
        this.fundJoinCnt = fundJoinCnt;

        this.dayProfit = dayProfit;
        this.weekProfit = weekProfit;
        this.monthProfit = monthProfit;
        this.yearProfit = yearProfit;
        this.joinDate = startDate;
    }

    @Override
    public void start() {
        if (!SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO))
            view.hideKakao();

        loadFundDetailInfo();
    }

    private void loadFundDetailInfo() {
        Disposable disposable = fundRepository
                .loadFundDetailInfo(fundID, SharedPreferenceUtil.getInstance().getSystemCountryLanguage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        this.row = (FundDetailRow) GsonUtil.getInstance().fromJson(jsonObject.get("row").toString(), FundDetailRow.class);
                        this.view.showFeeInfo(row);
                        processFundAsset();
                    }
                });
    }

    private void processFundAsset() {
        if (row != null) {
            String startDate[] = row.getStartDate().split(" ");
            String endDate[] = row.getEndDate().split(" ");
            dateStartEnd = joinDate+" ~ "+endDate[0];       // startData[0]+"~"+endDate[0] -> joinDate+" ~ "+endData[0] 으로 변경 : 추가 투자 시 시작 날은 가입 날로 해야함

            double settingMoney = row.getMaxAmount();
            double currentMoney = row.getPreAmount();
            double minMoney = row.getMinJoinCnt();
            int currentCurreny = row.getCurPrice();    //현재가

            view.showFundDate(joinDate, endDate);
            view.showFundDetail(row, fundJoinCnt, settingMoney, currentCurreny, minMoney, currency, countryCurrencyPrice);

            view.showDayProfit(dayProfit);
            if (Float.parseFloat(dayProfit) > 0)
                view.showDayProfitUp();
            else if (Float.parseFloat(dayProfit) < 0)
                view.showDayProfitDown();

            view.showWeekProfit(weekProfit);
            if (Float.parseFloat(weekProfit) > 0)
                view.showWeekProfitUp();
            else if (Float.parseFloat(weekProfit) < 0)
                view.showWeekProfitDown();

            view.showMonthProfit(monthProfit);
            if (Float.parseFloat(monthProfit) > 0)
                view.showMonthProfitUp();
            else if (Float.parseFloat(monthProfit) < 0)
                view.showMonthProfitDown();

            view.showYearProfit(yearProfit);
            if (Float.parseFloat(yearProfit) > 0)
                view.showYearProfitUp();
            else if (Float.parseFloat(yearProfit) < 0)
                view.showYearProfitDown();

            view.showFundContent(row.getInfo());
        }
    }

    @Override
    public void onButtonClicked(int viewId) {
        switch (viewId) {
            case R.id.img_back:
                view.finishActivity();
                break;
            case R.id.rl_fund_join:
                view.startFundAddInvestActivity(row, fundJoinCnt, dateStartEnd, fundID);
                break;
            case R.id.kakao_fund_consult:
                view.startKakaoConsult();
                break;
            case R.id.rl_dis_join:
                view.startFundDisjoinActivity(fundID);
                break;
            case R.id.telegram_consult:
                view.startTelegramConsult();
                break;
        }
    }

    @Override
    public void processFeeInfo(LinearLayout layout) {
        if (!layout.isShown()) {
            view.showFeeInfo(row);
            view.scrollDown();
        } else
            view.hideFeeInfo();
    }

    @Override
    public void processEtcInfo(TextView warningContent) {
        if (!warningContent.isShown()) {
            view.showWarningInfo(row.getAdditionalInfo());
            view.scrollDown();
        } else
            view.hideWarningInfo();
    }
}
