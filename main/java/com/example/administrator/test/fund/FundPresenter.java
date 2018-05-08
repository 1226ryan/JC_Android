package com.example.administrator.test.fund;

import android.view.Menu;

import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundAccumuldateBTC;
import co.bitpartner.data.model.FundList;
import co.bitpartner.data.model.FundRow;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.GsonUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FundPresenter implements FundContract.Presenter {

    private FundContract.View view;
    private FundActivity fundActivity;
    private FundRepository fundRepository;
    private Long newTime = -1L;

    public FundPresenter(FundRepository fundRepository, FundContract.View view, FundActivity fundActivity) {
        this.view = view;
        this.fundRepository = fundRepository;
        this.fundActivity = fundActivity;
    }

    @Override
    public void start() {
        loadRecommandFund();
    }

    @Override
    public void stop() {
        disposable.dispose();
    }

    private Disposable disposable;

    private void loadRecommandFund() {
        JsonObject commandObject = new JsonObject();
        commandObject.addProperty("lang", SharedPreferenceUtil.getInstance().getSystemCountryLanguage());

        disposable = fundRepository
                .loadFundRecommands(commandObject)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        FundRow row = (FundRow) GsonUtil.getInstance().fromJson(jsonObject.get("row").toString(), FundRow.class);
                        List<FundList> funds = row.getFundList();

                        view.hideProgressbar();
                        if (funds.size() == 0)
                            view.showEmptyFundMsg();
                        else
                            view.showRecommandFund(funds);

                        FundAccumuldateBTC fundAccumuldateBTC = row.fundAccumuldateBTC;
                        double countryCurrencyPrice = fundAccumuldateBTC.marketPrice* Double.parseDouble(fundAccumuldateBTC.count) / SharedPreferenceUtil.getInstance().getCurrencyPrice();
                        view.showAllFundInvest(fundAccumuldateBTC, countryCurrencyPrice);
                    }
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    public void processFundMenuCheck(Menu menu) {
        view.showFundMenuCheck(menu.getItem(2));
    }

    @Override
    public void processNoticeTimeDelay(List<String> notices) {
        Long oldTime = SharedPreferenceUtil.getInstance().getCloseTime();
        newTime = Calendar.getInstance().getTimeInMillis();
        if (oldTime == -1L)
            view.showNotice(notices);
        else {
            if (newTime - oldTime > TimeUnit.DAYS.toMillis(7))
                view.showNotice(notices);
        }
    }

    @Override
    public void saveCloseTime() {
        SharedPreferenceUtil.getInstance().setCloseTime(newTime);
    }
}
