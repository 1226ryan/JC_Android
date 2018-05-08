package com.example.administrator.test.fund.fund_join;

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

/**
 * Created by Bridge on 2018-01-15.
 */

public class FundJoinSuccessActivity extends BaseActivity {

    @BindView(R.id.fund_title_2)
    TextView tvFundTitle;

    @BindView(R.id.investment_money)
    TextView tvInvestmentMoney;

    @BindView(R.id.investment_btc)
    TextView tvInvestmentBtc;

    @BindView(R.id.investment_krw)
    TextView tvInvestmentKrw;

    @BindView(R.id.fund_join_fee_btc)
    TextView tvJoinFeeBtc;

    @BindView(R.id.fund_join_fee_krw)
    TextView tvJoinFeeKrw;

    @BindView(R.id.final_fund_money_btc)
    TextView tvFinalBtc;

    @BindView(R.id.final_fund_money_krw)
    TextView tvFinalKrw;

    private String fundName, fundCurrency;
    private double fundInvestMoney;
    private double fundFee;
    private double coinKrw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        fundInvestMoney = intent.getDoubleExtra(Defines.EXTRA_FUND_MONEY, 0.0);
        fundName = intent.getStringExtra(Defines.EXTRA_FUND_NAME);
        fundCurrency = intent.getStringExtra(Defines.EXTRA_FUND_CURRENCY);

        fundFee = intent.getDoubleExtra(Defines.EXTRA_FUND_FEE, 0.0);
        coinKrw = intent.getDoubleExtra(Defines.EXTRA_FUND_KRW, 0.0);

        String currency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        double currencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();

        initView(fundName, fundCurrency, fundInvestMoney, fundFee, coinKrw,
                currency, currencyPrice);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_join_success;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void initView(String fundName, String fundCurrency, double fundInvestMoney, double fundFee, double coinKrw,
                          String currency, double currencyPrice) {

        double finalFund = fundInvestMoney-(fundInvestMoney*fundFee/100);
        double countryCurrencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
        tvFundTitle.setText(fundName);
        tvInvestmentMoney.setText(getString(R.string.fund_dialog_content3)+fundCurrency);

        tvInvestmentBtc.setText(DecimalFormatUtil.getFormatNumber(fundInvestMoney)+" "+fundCurrency);

        if(currency.equals("KRW")) {
            tvInvestmentKrw.setText(getString(R.string.shift_9)+DecimalFormatUtil.getFormat((int)(fundInvestMoney*coinKrw/currencyPrice))+" "+ currency + ")");
            tvJoinFeeKrw.setText(getString(R.string.shift_9)+DecimalFormatUtil.getFormat((int)(fundInvestMoney*fundFee*coinKrw/currencyPrice/100))+" "+ currency + ")");
            tvFinalKrw.setText(getString(R.string.shift_9)+DecimalFormatUtil.getFormat((int)(finalFund*coinKrw/currencyPrice))+" " + currency + ")");
        } else {
            tvInvestmentKrw.setText(getString(R.string.shift_9)+ Double.parseDouble(String.format("%.2f",(fundInvestMoney*coinKrw/countryCurrencyPrice)))+" USD)");
            tvJoinFeeKrw.setText(getString(R.string.shift_9)+ Double.parseDouble(String.format("%.2f",(fundInvestMoney*fundFee*coinKrw/countryCurrencyPrice/100)))+" USD)");
            tvFinalKrw.setText(getString(R.string.shift_9)+ Double.parseDouble(String.format("%.2f",(finalFund*coinKrw/countryCurrencyPrice)))+" USD)");
        }


        tvJoinFeeBtc.setText(DecimalFormatUtil.getFormatNumber(fundInvestMoney*fundFee/100)+" "+fundCurrency);

        tvFinalBtc.setText(DecimalFormatUtil.getFormatNumber(finalFund)+" "+fundCurrency);
    }

    @OnClick(R.id.join)
    public void onMainClicked() {
        FundActivity.start(this);
    }

    @Override
    public void onBackPressed() {
        FundActivity.start(this);
    }
}
