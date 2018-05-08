package com.example.administrator.test.asset.asset_state;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.data.source.asset.AssetRepository;
import co.bitpartner.util.DecimalFormatUtil;

/**
 * Created by Bridge on 2018-01-10.
 */

public class AssetStateActivity extends BaseActivity implements AssetStateContract.View {

    @BindView(R.id.total_asset)
    TextView tvTotalAsset;
    @BindView(R.id.won1)
    TextView totalCurrency;

    @BindView(R.id.fund_asset)
    TextView tvFundAsset;     //가입한 펀드 자산
    @BindView(R.id.won2)
    TextView fundCurrency;

    @BindView(R.id.private_asset)
    TextView tvPrivateAsset;  //펀드미가입 보유코인 자산
    @BindView(R.id.won3)
    TextView privateCurrency;

//    @BindView(R.id.withdraw_asset)
//    TextView tvWithdrawAsset;

    private NumberFormat format = NumberFormat.getNumberInstance();
    private AssetStatePresenter assetStatePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetStatePresenter = new AssetStatePresenter(AssetRepository.getInstance(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        assetStatePresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        assetStatePresenter.stop();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_asset_state;
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        finish();
    }

    @Override
    public void setPresenter(AssetStateContract.Presenter presenter) { }

    @Override
    public void showCurrency(String usd) {
        totalCurrency.setText(usd);
        fundCurrency.setText(usd);
        privateCurrency.setText(usd);
    }

    @Override
    public void showTotalAsset(double totalAsset) {
        tvTotalAsset.setText(format.format(DecimalFormatUtil.getUsdForamt(totalAsset)));
    }

    @Override
    public void showFundAsset(double fundAsset) {
        tvFundAsset.setText(format.format(DecimalFormatUtil.getUsdForamt(fundAsset)));
    }

    @Override
    public void showPrivateFundAsset(double privateFundAsset) {
        tvPrivateAsset.setText(format.format(DecimalFormatUtil.getUsdForamt(privateFundAsset)));
    }

    @Override
    public void showWithdrawAsset(String withdrawAsset) {
//        tvWithdrawAsset.setText(format.format(Integer.valueOf(withdrawAsset)));
    }
}
