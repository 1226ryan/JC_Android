package com.example.administrator.test.fund.fund_add_invest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.app.fund.FundActivity;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.SharedPreferenceUtil;

public class FundAddInvestSuccessActivity extends BaseActivity{
    @BindView(R.id.fund_title_2)
    TextView tvFundTitle;

    @BindView(R.id.add_investment_btc)
    TextView tvAddInvestmentBtc;

    @BindView(R.id.add_investment_krw)
    TextView tvAddInvestmentKrw;

    @BindView(R.id.add_invest_fee_btc)
    TextView tvAddInvestFeeBtc;

    @BindView(R.id.total_add_invest_money_btc)
    TextView tvTotalAddInvestMoneyBtc;

    @BindView(R.id.add_invest_fee_reflection_krw)
    TextView tvTotalAddInvestMoneyKrw;

    private String fundName, fundCurrency, fundAddInvestMoney;
    private float fundFee;
    private int fundKrw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            fundName = intent.getStringExtra(Defines.EXTRA_FUND_NAME);
            fundCurrency = intent.getStringExtra(Defines.EXTRA_FUND_CURRENCY);
            fundAddInvestMoney = intent.getStringExtra(Defines.ADD_FUND_MONEY);
            fundFee = intent.getFloatExtra(Defines.EXTRA_FUND_FEE, 0);
            fundKrw = intent.getIntExtra(Defines.EXTRA_FUND_KRW, 0);

            fundFee = fundFee/100;

            initView(fundName, fundCurrency, fundAddInvestMoney, fundFee, fundKrw);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_add_invest_success;
    }

    @SuppressLint("SetTextI18n")
    private void initView(String fundName, String fundCurrency, String fundAddInvestMoney, float fundFee, int fundKrw) {
        double fundInvestMoneyDouble = Double.valueOf(fundAddInvestMoney);
        double finalFund = fundInvestMoneyDouble-fundInvestMoneyDouble*fundFee;

        tvFundTitle.setText(fundName);
        tvAddInvestmentBtc.setText(DecimalFormatUtil.getFormatNumber(fundInvestMoneyDouble)+" "+fundCurrency);
        tvAddInvestmentKrw.setText(getString(R.string.shift_9)+DecimalFormatUtil.getFormat((int) ((fundInvestMoneyDouble*fundKrw) / SharedPreferenceUtil.getInstance().getCurrencyPrice()))+" " + SharedPreferenceUtil.getInstance().getCountryCurrency() + ")" ) ;
        tvAddInvestFeeBtc.setText(DecimalFormatUtil.getFormatNumber(fundInvestMoneyDouble*fundFee)+" "+fundCurrency);
        tvTotalAddInvestMoneyBtc.setText(DecimalFormatUtil.getFormatNumber(finalFund)+" "+fundCurrency);
        tvTotalAddInvestMoneyKrw.setText(getString(R.string.shift_9)+DecimalFormatUtil.getFormat((int) ((finalFund*fundKrw) / SharedPreferenceUtil.getInstance().getCurrencyPrice()))+" "+ SharedPreferenceUtil.getInstance().getCountryCurrency() + ")" );
    }

    @OnClick(R.id.fund_add_invest_success_ok)
    public void onMainClicked() {
        FundActivity.start(this);
    }

    @Override
    public void onBackPressed() {
        FundActivity.start(this);
    }
}
