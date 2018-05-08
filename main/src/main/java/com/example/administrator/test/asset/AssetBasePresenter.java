package com.example.administrator.test.asset;


import java.util.concurrent.TimeUnit;

import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.AssetRow;
import co.bitpartner.data.source.asset.AssetRepository;
import co.bitpartner.util.GsonUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public abstract class AssetBasePresenter implements AssetContract.Presenter  {

    private AssetRepository assetRepository;
    private CompositeDisposable disposables = new CompositeDisposable();

    protected String currency;
    protected double currencyPrice;

    public AssetBasePresenter(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
        this.currency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        this.currencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
    }

    @Override
    public void start() {
        loadAssets();
    }

    @Override
    public void stop() {
        disposables.clear();
    }

    @Override
    public void loadAssets() {
        if (!disposables.isDisposed())
            disposables.clear();

        disposables
                .add(getFlowable()
//                .subscribeOn(Schedulers.io())
                .subscribe(next-> assetRepository
                        .loadAssets()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonObject -> {
                            if (jsonObject.get("result").toString().equals(Defines.CODE_1000) && jsonObject != null) {
                                AssetRow row = (AssetRow) GsonUtil.getInstance().fromJson(jsonObject.get("row").toString(), AssetRow.class);
                                processAsset(row);
                            }
                        }, throwable -> throwable.printStackTrace() )));
    }

    private Flowable<? extends Long> getFlowable() {
        return Flowable.interval(Defines.REQUEST_INITIAL_DELAY, Defines.REQUEST_PERIOD, TimeUnit.SECONDS).startWith(Long.valueOf(1)).retry(5);
    }

    protected abstract void processAsset(AssetRow row);
}
