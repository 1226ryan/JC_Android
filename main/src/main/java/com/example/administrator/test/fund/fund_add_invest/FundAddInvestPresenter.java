package com.example.administrator.test.fund.fund_add_invest;

import android.widget.Toast;

import co.bitpartner.BitPartnerApplication;
import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundDetailRow;
import co.bitpartner.data.model.command.AddInvestCommand;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FundAddInvestPresenter implements FundAddInvestContract.Presenter {

    private FundRepository fundRepository;
    private FundAddInvestContract.View view;

    private FundDetailRow row;
    private String fundStartEndDate;
    private String fundCurrentInvestMoney;
    private String fundID;

    private double fundInvestPossibleHoldingMoney;
    private double coinKrw;

    private String etMoney;

    protected String currency;
    protected double countryCurrencyPrice;

    public FundAddInvestPresenter(FundRepository fundRepository, FundAddInvestContract.View view) {
        this.fundRepository = fundRepository;
        this.view = view;

        this.currency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        this.countryCurrencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
    }

    @Override
    public void setFund(FundDetailRow row, String fundStartEndDate, String fundCurrentInvestMoney, String fundID) {
        this.row = row;
        this.fundStartEndDate = fundStartEndDate;
        this.fundCurrentInvestMoney = fundCurrentInvestMoney;
        this.fundID = fundID;
    }

    @Override
    public void start() {
        joinInfo();
    }

    private void joinInfo() {
        Disposable disposable = fundRepository
                .getPrivateAsset(row.getCurrency())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        fundInvestPossibleHoldingMoney = jsonObject.get("row").getAsJsonObject().get("private_assets").getAsDouble();
                        coinKrw = jsonObject.get("row").getAsJsonObject().get("coin_krw").getAsDouble();
                    }

                    view.hideKeyboard();
                    view.showFundInfo(row, fundStartEndDate, fundCurrentInvestMoney, fundID, fundInvestPossibleHoldingMoney,
                            currency, countryCurrencyPrice);
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    public void processInvestAdd(String etMoney) {
        this.etMoney = etMoney;

        view.hideKeyboard();
        if (etMoney.equals("")) {
            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.add_invest_money_input));
            return;
        }

        double btc = Double.parseDouble(etMoney);
        if (btc < row.getMinJoinCnt()) {
            view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.fund_add_invest_input_min_money));
            return;
        }

        view.showBottomDialog(Double.valueOf(etMoney), coinKrw, row.getFundFee().getJoinFee());
    }

    @Override
    public void addFund() {
        fundRepository
                .addFund(new AddInvestCommand(fundID, etMoney, row.getCurrency()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        view.showSuccessMessage(BitPartnerApplication.getInstance().getString(R.string.fund_add_invest_complete));
                        view.hideProgressbar();
                        view.startFundAddSuccessActivity(row.getName(), row.getCurrency(), etMoney, row.getFundFee().getJoinFee(), row.getCurPrice());
                    } else if (jsonObject.get("result").getAsString().equals(Defines.CODE_5002)) {
                        view.hideProgressbar();
                        view.showToast(BitPartnerApplication.getInstance().getString(R.string.FundAddInvestPresenter_5002));
                    }
                }, throwable -> {
                    view.hideProgressbar();
                    Toast.makeText(BitPartnerApplication.getInstance(), BitPartnerApplication.getInstance().getString(R.string.fund_add_invest_server_error), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                });

        view.showProgressbar();
        view.enableInvestButton();
        view.hideBottomDialog();
    }
}
