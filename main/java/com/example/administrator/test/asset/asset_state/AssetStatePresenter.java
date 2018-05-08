package com.example.administrator.test.asset.asset_state;

import android.support.annotation.NonNull;

import co.bitpartner.app.asset.AssetBasePresenter;
import co.bitpartner.data.model.AssetRow;
import co.bitpartner.data.source.asset.AssetRepository;
import co.bitpartner.util.AssetUtil;

public class AssetStatePresenter extends AssetBasePresenter {

    private AssetStateContract.View view;

    public AssetStatePresenter(AssetRepository assetRepository, @NonNull AssetStateContract.View view) {
        super(assetRepository);
        this.view = view;
    }

    @Override
    protected void processAsset(AssetRow row) {
        String totalAsset = AssetUtil.getMyAsset(row);
        String fundAsset = AssetUtil.getMyFunds(row.getFunds());
        String privateFundAsset = AssetUtil.getPrivateFund(row);
        String withdrawAsset = AssetUtil.getKrw(row);

        showUserAsset(totalAsset, fundAsset, privateFundAsset, withdrawAsset);
    }

    private void showUserAsset(String totalAsset, String fundAsset, String privateFundAsset, String withdrawAsset) {
        view.showCurrency(currency);

        double mTotalAsset = Double.parseDouble(totalAsset) / currencyPrice;
        double mFundAsset = Double.parseDouble(fundAsset) / currencyPrice;
        double mPrivateFundAsset = Double.parseDouble(privateFundAsset) / currencyPrice;

        view.showTotalAsset(mTotalAsset);
        view.showFundAsset(mFundAsset);
        view.showPrivateFundAsset(mPrivateFundAsset);
        view.showWithdrawAsset(withdrawAsset);
    }
}
