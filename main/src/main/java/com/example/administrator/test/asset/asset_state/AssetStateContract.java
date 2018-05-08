package com.example.administrator.test.asset.asset_state;

import co.bitpartner.app.BasePresenter;
import co.bitpartner.app.BaseView;

public interface AssetStateContract {

    interface View extends BaseView<Presenter> {
        void showTotalAsset(double totalAsset);
        void showFundAsset(double fundAsset);
        void showPrivateFundAsset(double privateFundAsset);
        void showWithdrawAsset(String withdrawAsset);

        void showCurrency(String usd);
    }

    interface Presenter extends BasePresenter {
    }

}
