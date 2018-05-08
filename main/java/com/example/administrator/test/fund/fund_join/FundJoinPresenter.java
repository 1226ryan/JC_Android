package com.example.administrator.test.fund.fund_join;

import android.widget.Toast;

import co.bitpartner.BitPartnerApplication;
import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundFee;
import co.bitpartner.data.model.FundJoinCommand;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FundJoinPresenter implements FundJoinContract.Presenter {

    private FundRepository fundRepository;
    private FundJoinContract.View view;

    private String fundID;
    private String fundName;
    private String fundCurrency;
    private String fundDuration;
    private double maxCount;
    private FundFee fundFee;

    private double btcInvest;

    private double privateAsset;
    private double minJoinCnt;
    private double coinKrw;

    public FundJoinPresenter(FundRepository fundRepository, FundJoinContract.View view) {
        this.fundRepository = fundRepository;
        this.view = view;
    }

    @Override
    public void start() { }

    @Override
    public void setFundJoinInfo(String fundID, String fundName, String fundCurrency, String fundStartDate, String fundEndDate,
                                double fundMax, double fundPre, int fundCurpice, FundFee fundFee, double privateAsset, double minJoinCnt, double coinKrw) {
        String startDate[] = fundStartDate.split(" ");
        String endDate[] = fundEndDate.split(" ");

        this.fundID = fundID;
        this.fundName = fundName;
        this.fundCurrency = fundCurrency;
        this.fundDuration = startDate[0] + " ~ " + endDate[0];
        this.privateAsset = privateAsset;
        this.minJoinCnt = minJoinCnt;
        this.coinKrw = coinKrw; //curPrice?
        this.maxCount = fundMax - fundPre;
        this.fundFee = fundFee;

        view.showJoinInfo(fundName, fundCurrency, fundDuration, privateAsset, DecimalFormatUtil.getFormatNumber(minJoinCnt), DecimalFormatUtil.getFormatNumber(maxCount));
    }

    @Override
    public void processDialogAction(String editBTCInvest) {
        if (editBTCInvest.length() > 0) {
            if (editBTCInvest.length() > 10)
                view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.fund_investment_money_limit));
            else {
                btcInvest = Double.parseDouble(editBTCInvest);
                view.showBottomDialog(btcInvest, coinKrw, fundFee);
            }
        } else
            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.input_money));
    }

    @Override
    public void joinFund() {
        view.showProgressbar();
        view.enableInvestButton();
        String sBtcInvest = String.valueOf(btcInvest);

        FundJoinCommand fundJoinCommand = new FundJoinCommand(fundID, sBtcInvest, fundCurrency, SharedPreferenceUtil.getInstance().getSystemCountryLanguage());
        fundRepository
                .joinFund(fundJoinCommand)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject != null) {
                        String result = jsonObject.get("result").getAsString();
                        if (result.equals(Defines.CODE_1000))
                            view.startFundJoinSuccessActivity(btcInvest, fundName, fundCurrency, fundFee.getJoinFee(), coinKrw);
                        else if (result.equals(Defines.CODE_9666))
                            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.service_stop));
                        else if (result.equals(Defines.CODE_6000))
                            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.FundJoinActivity_2));
                        else if (result.equals(Defines.CODE_5002))
                            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.FundJoinActivity_3));
                        else if (result.equals(Defines.CODE_4061))
                            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.FundJoinActivity_4));
                    }

                    view.hideProgressbar();
                }, throwable -> {
                    view.hideProgressbar();
                    Toast.makeText(BitPartnerApplication.getInstance(), BitPartnerApplication.getInstance().getString(R.string.fund_add_invest_server_error), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                });
    }
}
