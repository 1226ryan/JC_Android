package com.example.administrator.test.fund.fund_dis_join;

import co.bitpartner.BitPartnerApplication;
import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundCancelRow;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.GsonUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FundDisjoinPresenter implements FundDisjoinContract.Presenter {

    private FundRepository fundRepository;
    private FundDisjoinContract.View view;
    private String fundID;

    private CompositeDisposable compositeDisposable;
    private String currency;
    private double currencyPrice;

    public FundDisjoinPresenter(FundRepository fundRepository, FundDisjoinContract.View view) {
        this.fundRepository = fundRepository;
        this.view = view;

        this.compositeDisposable = new CompositeDisposable();
        this.currency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        this.currencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
    }

    @Override
    public void setFundID(String fundID) {
        this.fundID = fundID;
    }

    @Override
    public void start() {
        getCancelInfo();
    }

    private void getCancelInfo() {
        compositeDisposable.clear();

        Disposable disposable = fundRepository
                .getCancelInfo(fundID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        FundCancelRow row = (FundCancelRow) GsonUtil.getInstance().fromJson(jsonObject.get("row").toString(), FundCancelRow.class);
                        view.showCancelBTCInfo(row);

                        if (currency.equals("KRW")) {
                            int jointQuantityCurrency = (int) (row.joinQuantityKrw / currencyPrice);
                            int fundQuantityCurrency = (int) (row.fundQuantityKrw / currencyPrice);
                            int benefitFeeCurrency = (int) (row.benefitFeeKrw / currencyPrice);
                            int dropFitCurrency = (int) (row.dropFeeKrw / currencyPrice);
                            int realQuantityCurrency = (int) (row.realQuantityKrw / currencyPrice);
                            view.showCancelKRWInfo(jointQuantityCurrency, fundQuantityCurrency, benefitFeeCurrency,
                                    dropFitCurrency, realQuantityCurrency);
                        } else {
                            double jointQuantityCurrency = Double.parseDouble(String.format("%.2f", row.joinQuantityKrw / currencyPrice));
                            double fundQuantityCurrency = Double.parseDouble(String.format("%.2f", row.fundQuantityKrw / currencyPrice));
                            double benefitFeeCurrency = Double.parseDouble(String.format("%.2f", row.benefitFeeKrw / currencyPrice));
                            double dropFitCurrency = Double.parseDouble(String.format("%.2f", row.dropFeeKrw / currencyPrice));
                            double realQuantityCurrency = Double.parseDouble(String.format("%.2f", row.realQuantityKrw / currencyPrice));
                            view.showCancelUSDInfo(jointQuantityCurrency, fundQuantityCurrency, benefitFeeCurrency,
                                    dropFitCurrency, realQuantityCurrency, currency);
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void processFundDisjoin() {
        compositeDisposable.clear();

        Disposable disposable = fundRepository
                .cancelFund(fundID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    String result = jsonObject.get("result").getAsString();
                    if (result.equals(Defines.CODE_4062))
                        view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.func_cancel_stop));
                    else if (result.equals(Defines.CODE_9666))
                        view.showFailureMessage(BitPartnerApplication.getInstance().getString(R.string.service_stop));
                    else {
                        String authToken = jsonObject.get("row").getAsJsonObject().get("auth_token").getAsString();
                        SharedPreferenceUtil.getInstance().setAuthToken(authToken);
                        view.showCheckDialog();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void processDialogAction(int type) {
        if (type == Defines.KEY_FUND_DIS_JOIN) {    //yes clicked
            view.startAuthActivity();
            view.hideCheckDialog();
        } else
            view.hideCheckDialog();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
