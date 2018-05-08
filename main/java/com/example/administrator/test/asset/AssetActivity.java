package com.example.administrator.test.asset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BottomNavigationBaseActivity;
import co.bitpartner.app.asset.asset_profit_state.AssetProfitStateActivity;
import co.bitpartner.app.asset.asset_state.AssetStateActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.app.fund.FundRecyclerAdapter;
import co.bitpartner.app.fund.fund_join_detail.FundJoinDetailActivity;
import co.bitpartner.data.source.asset.AssetRepository;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.SharedPreferenceUtil;

public class AssetActivity extends BottomNavigationBaseActivity
        implements AssetContract.View, FundRecyclerAdapter.FundJoinCallback {

    private static final String TAG = AssetActivity.class.getSimpleName();

    //펀드
    @BindView(R.id.no_fund_img)
    ImageView imgNoFund;
    @BindView(R.id.ll_bit_fund)
    LinearLayout llBitFund;
    @BindView(R.id.rl_account_state)
    RelativeLayout rlAsset;

    //총 자산 수익
    @BindView(R.id.today_profit)
    TextView todayProfit;
    @BindView(R.id.today_won)
    TextView todayWon;
    @BindView(R.id.won1)
    TextView todayProfitCurrency;

    @BindView(R.id.this_week_profit)
    TextView weekProfit;
    @BindView(R.id.this_week_won)
    TextView weekWon;
    @BindView(R.id.won2)
    TextView weekProfitCurrency;

    @BindView(R.id.this_month_profit)
    TextView monthProfit;
    @BindView(R.id.this_month_won)
    TextView monthWon;
    @BindView(R.id.won3)
    TextView monthProfitCurrency;

    @BindView(R.id.this_year_profit)
    TextView yearProfit;
    @BindView(R.id.this_year_won)
    TextView yearWon;
    @BindView(R.id.won4)
    TextView yearProfitCurrency;

    //내 자산
    @BindView(R.id.my_asset)
    TextView tvMyAsset;
    @BindView(R.id.money_unit)
    TextView currency;

    @BindView(R.id.coin_recycler)
    RecyclerView coinRecycler;
    @BindView(R.id.fund_recycler)
    RecyclerView fundRecycler;

    private CoinRecyclerAdapter coinAdapter;
    private FundRecyclerAdapter fundAdapter;
    private NumberFormat format = NumberFormat.getNumberInstance();

    private AssetPresenter assetPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetPresenter = new AssetPresenter(AssetRepository.getInstance(), this);
        initView();
    }

    @Override
    public void setPresenter(AssetContract.Presenter presenter) { }

    @Override
    protected void onResume() {
        super.onResume();
        assetPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        assetPresenter.stop();
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.my_asset;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_asset;
    }

    @Override
    public void initView() {
        coinAdapter = new CoinRecyclerAdapter();
        coinRecycler.setLayoutManager(new LinearLayoutManager(this));
        coinRecycler.setAdapter(coinAdapter);
        coinRecycler.setNestedScrollingEnabled(false);

        fundAdapter = new FundRecyclerAdapter(Defines.TYPE_ALREADY_JOIN_FUND);
        fundAdapter.setCallback(this);
        fundRecycler.setHasFixedSize(true);
        fundRecycler.setLayoutManager(new LinearLayoutManager(this));
        fundRecycler.setAdapter(fundAdapter);
    }

    @Override
    public void showAsset(double asset) {
        tvMyAsset.setText(format.format(DecimalFormatUtil.getUsdForamt(asset)));
    }

    @Override
    public void showEmptyAsset() {
        tvMyAsset.setText("0");
    }

    @Override
    public void showCurrency(String usd) {
        if( !SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO) ) {
            imgNoFund.setImageResource(R.drawable.no_bitfund_img_en);
        }
        currency.setText(usd);
    }

    @Override
    public void showTodayProfit(String profit, double profitPrice, String currency) {
        todayProfit.setText(profit);
        todayWon.setText(format.format(DecimalFormatUtil.getUsdForamt(profitPrice)));
        todayProfitCurrency.setText(currency);
    }

    @Override
    public void showWeekProfit(String profit, double profitPrice, String currency) {
        weekProfit.setText(profit);
        weekWon.setText(format.format(DecimalFormatUtil.getUsdForamt(profitPrice)));
        weekProfitCurrency.setText(currency);
    }

    @Override
    public void showMonthProfit(String profit, double profitPrice, String currency) {
        monthProfit.setText(profit);
        monthWon.setText(format.format(DecimalFormatUtil.getUsdForamt(profitPrice)));
        monthProfitCurrency.setText(currency);
    }

    @Override
    public void showYearProfit(String profit, double profitPrice, String currency) {
        yearProfit.setText(profit);
        yearWon.setText(format.format(DecimalFormatUtil.getUsdForamt(profitPrice)));
        yearProfitCurrency.setText(currency);
    }

    @Override
    public void initFund() {
        imgNoFund.setVisibility(View.GONE);
        llBitFund.setVisibility(View.VISIBLE);
        fundRecycler.setVisibility(View.VISIBLE);
        fundRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void showFund(List<Map<String, String>> mapFunds) {
        fundAdapter.setFunds(mapFunds);
    }

    @Override
    public void hideFund() {
        imgNoFund.setVisibility(View.VISIBLE);
        llBitFund.setVisibility(View.GONE);
        fundRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showCoin(List<Map<String, String>> coins) {
        coinAdapter.setCoins(coins);
    }

    @OnClick(R.id.rl_account_state)
    public void onAssetStateClicked() {
        startActivity(new Intent(this, AssetStateActivity.class));
    }

    @OnClick(R.id.rl_all_profit_state)
    public void onAssetProfitStateClicked() {
        startActivity(new Intent(this, AssetProfitStateActivity.class));
    }

    @Override
    public void onFundDetailClicked(Map<String, String> mapFund) {
        String startDate[] = mapFund.get("fund_date").split(" ");

        Intent intent = new Intent(this, FundJoinDetailActivity.class);
        intent.putExtra(Defines.EXTRA_FUND_TYPE, Defines.TYPE_ALREADY_JOIN_FUND);
        intent.putExtra(Defines.EXTRA_FUND_ID, mapFund.get("fund_id"));
        intent.putExtra(Defines.EXTRA_FUND_JOIN_COUNT, mapFund.get("join_coin_cnt"));
        intent.putExtra(Defines.EXTRA_FUND_CURRENCY, mapFund.get("fund_currency"));
        intent.putExtra(Defines.EXTRA_DAY_FUND, mapFund.get("today_profit"));
        intent.putExtra(Defines.EXTRA_WEEK_FUND, mapFund.get("week_profit"));
        intent.putExtra(Defines.EXTRA_MONTH_FUND, mapFund.get("month_profit"));
        intent.putExtra(Defines.EXTRA_YEAR_FUND, mapFund.get("year_profit"));
        intent.putExtra(Defines.EXTRA_START_DATE, startDate[0]);
        startActivity(intent);
    }
}
