package com.example.administrator.test.asset.asset_profit_state;

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
 * Created by Bridge on 2018-01-12.
 */

public class AssetProfitStateActivity extends BaseActivity implements AssetProfitStateContract.View {

    @BindView(R.id.fund_day_profit)
    TextView fundDayProfit;
    @BindView(R.id.fund_day_won)
    TextView fundDayWon;
    @BindView(R.id.won1)
    TextView dayCurrency;
    @BindView(R.id.fund_week_profit)
    TextView fundWeekProfit;
    @BindView(R.id.fund_week_won)
    TextView fundWeekWon;
    @BindView(R.id.won2)
    TextView weekCurrency;
    @BindView(R.id.fund_month_profit)
    TextView fundMonthProfit;
    @BindView(R.id.fund_month_won)
    TextView fundMonthWon;
    @BindView(R.id.won3)
    TextView monthCurrency;
    @BindView(R.id.fund_year_profit)
    TextView fundYearProfit;
    @BindView(R.id.fund_year_won)
    TextView fundYearWon;
    @BindView(R.id.won4)
    TextView yearCurrency;

    @BindView(R.id.coin_day_profit)
    TextView disDayProfit;
    @BindView(R.id.coin_day_won)
    TextView disDayWon;
    @BindView(R.id.won5)
    TextView disDayCurrency;
    @BindView(R.id.coin_week_profit)
    TextView disWeekProfit;
    @BindView(R.id.coin_week_won)
    TextView disWeekWon;
    @BindView(R.id.won6)
    TextView disWeekCurrency;
    @BindView(R.id.coin_month_profit)
    TextView disMonthProfit;
    @BindView(R.id.coin_month_won)
    TextView disMonthWon;
    @BindView(R.id.won7)
    TextView disMonthCurrency;
    @BindView(R.id.coin_year_profit)
    TextView disYearProfit;
    @BindView(R.id.coin_year_won)
    TextView disYearWon;
    @BindView(R.id.won8)
    TextView disYearCurrency;

    private AssetProfitStatePresenter presenter;
    private NumberFormat format = NumberFormat.getNumberInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AssetProfitStatePresenter(AssetRepository.getInstance(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_profit_state;
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        finish();
    }

    @Override
    public void setPresenter(AssetProfitStateContract.Presenter presenter) { }

    @Override
    public void showTodayFund(String profit, double price, String currency) {
        fundDayProfit.setText(profit);
        fundDayWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        dayCurrency.setText(currency);
    }

    @Override
    public void showWeekFund(String profit, double price, String currency) {
        fundWeekProfit.setText(profit);
        fundWeekWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        weekCurrency.setText(currency);
    }

    @Override
    public void showMonthFund(String profit, double price, String currency) {
        fundMonthProfit.setText(profit);
        fundMonthWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        monthCurrency.setText(currency);
    }

    @Override
    public void showYearFund(String profit, double price, String currency) {
        fundYearProfit.setText(profit);
        fundYearWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        yearCurrency.setText(currency);
    }

    @Override
    public void showTodayDisjoinFund(String profit, double price, String currency) {
        disDayProfit.setText(profit);
        disDayWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        disDayCurrency.setText(currency);
    }

    @Override
    public void showWeekDisjoinFund(String profit, double price, String currency) {
        disWeekProfit.setText(profit);
        disWeekWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        disWeekCurrency.setText(currency);
    }

    @Override
    public void showMonthDisjoinFund(String profit, double price, String currency) {
        disMonthProfit.setText(profit);
        disMonthWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        disMonthCurrency.setText(currency);
    }

    @Override
    public void showYearDisjoinFund(String profit, double price, String currency) {
        disYearProfit.setText(profit);
        disYearWon.setText(format.format(DecimalFormatUtil.getUsdForamt(price)));
        disYearCurrency.setText(currency);
    }
}































