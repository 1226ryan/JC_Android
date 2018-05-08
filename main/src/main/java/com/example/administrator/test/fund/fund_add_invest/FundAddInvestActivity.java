package com.example.administrator.test.fund.fund_add_invest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundDetailRow;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.ButtonUtil;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.DialogUtilFund;
import co.bitpartner.util.ProgressbarUtil;

public class FundAddInvestActivity extends BaseActivity
        implements FundAddInvestContract.View {

    @BindView(R.id.fund_title_2)
    TextView tvFundTitle;
    @BindView(R.id.start_end_date_2)
    TextView tvStartEndDate;
    @BindView(R.id.current_invest_money_btc)
    TextView tvCurrentInvestMoneyBtc;
    @BindView(R.id.current_invest_money_krw)
    TextView tvCurrentInvestMoneyKrw;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_min_max_join_money)
    TextView tvMinMaxJoinMoney;
    @BindView(R.id.tv_invest_possible_holding_money)
    TextView tvInvestPossibleHoldingMonay;
    @BindView(R.id.rl_add_invest)
    RelativeLayout rlAddInvest;
    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;
//    private String fundName, fundStartEndDate, fundCurrentInvestMoney, fundCurrency, fundID;
//    private double fundMinJoinMoney;
//    private float fundMaxJoinMoney, fundInvestPossibleHoldingMoney, fundFee;
//    private int fundKrw;
//    private FundDetailRow row;
    private DialogUtilFund dialogUtil;
    private FundAddInvestPresenter presenter;

    public static void start(Context context, FundDetailRow row, String fundJoinCnt, String dateStartEnd, String fundID) {
        Intent intent = new Intent(context, FundAddInvestActivity.class);
//        intent.putExtra(Defines.EXTRA_FUND_NAME, row.getName());
//        intent.putExtra(Defines.EXTRA_FUND_CURRENCY, row.getCurrency());
//        intent.putExtra(Defines.EXTRA_FUND_KRW, row.getCurPrice());
//        intent.putExtra(Defines.EXTRA_FUND_MIN_JOIN_MONEY, row.getMinJoinCnt());
//        intent.putExtra(Defines.EXTRA_FUND_FEE, row.getFundFee().getJoinFee());
//        intent.putExtra(Defines.EXTRA_FUND_MAX_JOIN_MONEY, row.getMaxAmount());

        intent.putExtra(Defines.EXTRA_FUND_DETAIL_ROW, row);
        intent.putExtra(Defines.EXTRA_FUND_START_END_DATE, dateStartEnd);
        intent.putExtra(Defines.EXTRA_FUND_JOIN_COUNT, fundJoinCnt);
        intent.putExtra(Defines.EXTRA_FUND_ID, fundID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
//        fundName = intent.getStringExtra(Defines.EXTRA_FUND_NAME);
//        fundCurrency = intent.getStringExtra(Defines.EXTRA_FUND_CURRENCY);
//        fundKrw = intent.getIntExtra(Defines.EXTRA_FUND_KRW, 0);
//        fundMinJoinMoney = intent.getDoubleExtra(Defines.EXTRA_FUND_MIN_JOIN_MONEY, 0.0);
//        fundFee = intent.getFloatExtra(Defines.EXTRA_FUND_FEE, 0);
//        fundMaxJoinMoney = intent.getFloatExtra(Defines.EXTRA_FUND_MAX_JOIN_MONEY, 0);
        FundDetailRow row = intent.getParcelableExtra(Defines.EXTRA_FUND_DETAIL_ROW);
        String fundStartEndDate = intent.getStringExtra(Defines.EXTRA_FUND_START_END_DATE);
        String fundCurrentInvestMoney = intent.getStringExtra(Defines.EXTRA_FUND_JOIN_COUNT);
        String fundID = intent.getStringExtra(Defines.EXTRA_FUND_ID);

        presenter = new FundAddInvestPresenter(FundRepository.getInstance(), this);
        presenter.setFund(row, fundStartEndDate, fundCurrentInvestMoney, fundID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        finish();
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_invest;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showFundInfo(FundDetailRow row, String fundStartEndDate, String fundCurrentInvestMoney, String fundID,
                             double fundInvestPossibleHoldingMoney, String currency, double countryCurrencyPrice) {
        double dFundCurrentInvestMoney = Double.valueOf(fundCurrentInvestMoney);
        tvFundTitle.setText(row.getName());
        tvStartEndDate.setText(fundStartEndDate);
        tvCurrentInvestMoneyBtc.setText(DecimalFormatUtil.getFormatNumber(dFundCurrentInvestMoney) + " " + row.getCurrency());
        if(currency.equals("KRW")) {
            tvCurrentInvestMoneyKrw.setText(getString(R.string.shift_9) + DecimalFormatUtil.getFormat((int) ((dFundCurrentInvestMoney * row.getCurPrice()) /countryCurrencyPrice )) + " " + currency + ")");
        } else {
            tvCurrentInvestMoneyKrw.setText(getString(R.string.shift_9) + DecimalFormatUtil.getFormat((dFundCurrentInvestMoney * row.getCurPrice()) /countryCurrencyPrice  ) + " " + currency + ")");
        }

        tvMinMaxJoinMoney.setText(" " + DecimalFormatUtil.getFormatNumber(row.getMinJoinCnt()) + " ~ " + (row.getMaxAmount() - dFundCurrentInvestMoney)+ " " + row.getCurrency());
        tvInvestPossibleHoldingMonay.setText(" " + DecimalFormatUtil.getFormatNumber(fundInvestPossibleHoldingMoney) + " " + row.getCurrency());
    }

    @OnClick(R.id.rl_add_invest)
    public void onSuccessClick() {
        presenter.processInvestAdd(etMoney.getText().toString());
    }

    @Override
    public void showBottomDialog(double btcInvest, double coinKrw, double joinFee) {
        dialogUtil = DialogUtilFund.getDialog(this, btcInvest, coinKrw, joinFee, 1, yesListener, noListener);
        dialogUtil.show();
    }

    @Override
    public void showProgressbar() {
        ProgressbarUtil.getInstance().showProgressbar(this, rootLayout);
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enableInvestButton() {
        ButtonUtil.onButtonClicked(rlAddInvest, 3).subscribe();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private View.OnClickListener yesListener = new View.OnClickListener() {
        public void onClick(View v) {
            presenter.addFund();
        }
    };

    private View.OnClickListener noListener = new View.OnClickListener() {
        public void onClick(View v) { dialogUtil.dismiss(); }
    };

    @Override
    public void showSuccessMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideBottomDialog() {
        dialogUtil.dismiss();
    }

    @Override
    public void hideProgressbar() {
        ProgressbarUtil.getInstance().hideProgressbar(rootLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressbar();
    }

    @Override
    public void startFundAddSuccessActivity(String fundName, String fundCurrency, String etMoney, float joinFee, int curPrice) {
        Intent intent = new Intent(FundAddInvestActivity.this, FundAddInvestSuccessActivity.class);
        intent.putExtra(Defines.EXTRA_FUND_NAME, fundName);
        intent.putExtra(Defines.EXTRA_FUND_CURRENCY, fundCurrency);
        intent.putExtra(Defines.ADD_FUND_MONEY, etMoney);
        intent.putExtra(Defines.EXTRA_FUND_FEE, joinFee);
        intent.putExtra(Defines.EXTRA_FUND_KRW, curPrice);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void setPresenter(FundAddInvestContract.Presenter presenter) { }
}

