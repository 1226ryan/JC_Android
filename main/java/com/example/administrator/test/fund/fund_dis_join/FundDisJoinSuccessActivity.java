package com.example.administrator.test.fund.fund_dis_join;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.app.fund.FundActivity;
import co.bitpartner.data.model.FundDisJoinRow;
import co.bitpartner.util.DateUtil;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.SharedPreferenceUtil;

public class FundDisJoinSuccessActivity extends BaseActivity {

    public static void start(Context context, FundDisJoinRow row) {
        Intent intent = new Intent(context, FundDisJoinSuccessActivity.class);
        intent.putExtra(Defines.EXTRA_FUND_DIS_JOIN, row);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @BindView(R.id.real_quantity_coin)
    TextView realQuantityCoin;

    @BindView(R.id.real_quantity_krw)
    TextView realQuantityKrw;

    @BindView(R.id.date)
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        FundDisJoinRow row = intent.getParcelableExtra(Defines.EXTRA_FUND_DIS_JOIN);

        String currency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        double currencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();

        initView(row, currency, currencyPrice);
    }

    private void initView(FundDisJoinRow row, String currency, double currencyPrice) {
        realQuantityCoin.setText(DecimalFormatUtil.getFormatNumber(row.realQuantityCoin) + " " + getString(R.string.btc));
        if(currency.equals("KRW")) {
            realQuantityKrw.setText(getString(R.string.shift_9) + (int)(row.realQuantityKrw/currencyPrice) + " " + currency + ")");
        } else {
            realQuantityKrw.setText(getString(R.string.shift_9) + (row.realQuantityKrw/currencyPrice) + " " + currency + ")");
        }
        date.setText(DateUtil.getCurrentTime());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_dis_join_success;
    }

    @OnClick(R.id.ok)
    public void onOkClicked() {
        FundActivity.start(this);
    }

    @Override
    public void onBackPressed() {
        FundActivity.start(this);
    }
}
