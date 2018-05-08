package com.example.administrator.test.fund.fund_join;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundFee;
import co.bitpartner.data.model.FundList;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.ButtonUtil;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.DialogUtilFund;
import co.bitpartner.util.ProgressbarUtil;

/**
 * Created by Bridge on 2018-01-15.
 */

public class FundJoinActivity extends BaseActivity
        implements FundJoinContract.View {

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    @BindView(R.id.rl_fund_success)
    RelativeLayout rlFundInvest;

    @BindView(R.id.name)
    TextView fundTitle;

//    @BindView(R.id.title_currency)
//    TextView titleCurrency;

    @BindView(R.id.fund_duration)
    TextView date;

    @BindView(R.id.tv_min_max_join_money)
    TextView minMaxJoin;
    @BindView(R.id.tv_btc)
    TextView currency1;

    @BindView(R.id.tv_invest_possible_holding_money)
    TextView investPossible;
    @BindView(R.id.tv_btc_2)
    TextView currency2;

    @BindView(R.id.edit_invest_fund)
    EditText editFundInvest;

    public static void start(Context context, double privateAsset, double coinKrw, double minJoinCount, FundList fund) {
        Intent intent = new Intent(context, FundJoinActivity.class);
        intent.putExtra(Defines.EXTRA_PRIVATE_ASSET, privateAsset);
        intent.putExtra(Defines.EXTRA_COIN_KRW, coinKrw);
        intent.putExtra(Defines.EXTRA_FUND_MIN_JOIN_MONEY, minJoinCount);

        intent.putExtra(Defines.EXTRA_FUND_ID, fund.getFundId());
        intent.putExtra(Defines.EXTRA_FUND_FEE, fund.getFundFee());
        intent.putExtra(Defines.EXTRA_FUND_START_DATE, fund.getStartDate());
        intent.putExtra(Defines.EXTRA_FUND_END_DATE, fund.getEndDate());
        intent.putExtra(Defines.EXTRA_FUND_NAME, fund.getName());
        intent.putExtra(Defines.EXTRA_FUND_CURRENCY, fund.getCurrency());
        intent.putExtra(Defines.EXTRA_FUND_CUR_PRICE, fund.getCurPrice());
        intent.putExtra(Defines.EXTRA_FUND_MAX_JOIN_MONEY, fund.getMaxAmount());
        intent.putExtra(Defines.EXTRA_FUND_PRE_AMOUNT, fund.getPreAmount());
        context.startActivity(intent);
    }

    private DialogUtilFund dialogUtil;
    private FundJoinPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        double privateAsset = intent.getDoubleExtra(Defines.EXTRA_PRIVATE_ASSET, 0.0);
        double minJoinCnt = intent.getDoubleExtra(Defines.EXTRA_FUND_MIN_JOIN_MONEY, 0.0);    //min join cnt
        double coinKrw = intent.getDoubleExtra(Defines.EXTRA_COIN_KRW, 0.0);

        String fundID = intent.getStringExtra(Defines.EXTRA_FUND_ID);
        FundFee fundFee = intent.getParcelableExtra(Defines.EXTRA_FUND_FEE);
        String fundName= intent.getStringExtra(Defines.EXTRA_FUND_NAME);
        String fundCurrency = intent.getStringExtra(Defines.EXTRA_FUND_CURRENCY);
        String fundStartDate = intent.getStringExtra(Defines.EXTRA_FUND_START_DATE);
        String fundEndDate = intent.getStringExtra(Defines.EXTRA_FUND_END_DATE);
        double fundMax = intent.getDoubleExtra(Defines.EXTRA_FUND_MAX_JOIN_MONEY, 0.0);
        double fundPre = intent.getDoubleExtra(Defines.EXTRA_FUND_PRE_AMOUNT, 0.0);
        int funcCurprice = intent.getIntExtra(Defines.EXTRA_FUND_CUR_PRICE, 0);

        presenter = new FundJoinPresenter(FundRepository.getInstance(), this);
        presenter.setFundJoinInfo(fundID, fundName, fundCurrency, fundStartDate, fundEndDate,
                fundMax, fundPre, funcCurprice, fundFee, privateAsset, minJoinCnt, coinKrw);
        presenter.start();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_join;
    }

    @Override
    public void showJoinInfo(String fundName, String fundCurrency, String fundDuration, double privateAsset, String minCount, String maxCount) {
        fundTitle.setText(fundName);
//        titleCurrency.setText(fundCurrency);
        date.setText(fundDuration);
//        currency.setText(fundCurrency);
//        myBTC.setText(DecimalFormatUtil.getFormatNumber(privateAsset));

        minMaxJoin.setText(minCount + " ~ " + maxCount);
        currency1.setText(fundCurrency);

        investPossible.setText(DecimalFormatUtil.getFormatNumber(privateAsset));
        currency2.setText(fundCurrency);
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startFundJoinSuccessActivity(double btcInvest, String fundName, String fundCurrency, double fundFee, double coinKrw) {
        Intent intent = new Intent(FundJoinActivity.this, FundJoinSuccessActivity.class);
        intent.putExtra(Defines.EXTRA_FUND_MONEY, btcInvest);
        intent.putExtra(Defines.EXTRA_FUND_NAME, fundName);
        intent.putExtra(Defines.EXTRA_FUND_CURRENCY, fundCurrency);

        intent.putExtra(Defines.EXTRA_FUND_FEE, fundFee);
        intent.putExtra(Defines.EXTRA_FUND_KRW, coinKrw);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.rl_fund_success)
    public void onFundSuccessClicked() {
        presenter.processDialogAction(editFundInvest.getText().toString());
    }

    @Override
    public void showBottomDialog(double btc, double coinKrw, FundFee fundFee) {
        dialogUtil = DialogUtilFund.getDialog(this, btc, coinKrw, fundFee.getJoinFee(), 0,yesListener, noListener);
        dialogUtil.show();
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        finish();
    }

    @Override
    public void showProgressbar() {
        ProgressbarUtil.getInstance().showProgressbar(this, rootLayout);
    }

    @Override
    public void hideProgressbar() {
        ProgressbarUtil.getInstance().hideProgressbar(rootLayout);
    }

    @Override
    public void enableInvestButton() {
        ButtonUtil.onButtonClicked(rlFundInvest, 3).subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressbar();
    }

    private View.OnClickListener yesListener = new View.OnClickListener() {
        public void onClick(View v) {
            presenter.joinFund();
            dialogUtil.dismiss();
        }
    };

    private View.OnClickListener noListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogUtil.dismiss();
        }
    };

    @Override
    public void setPresenter(FundJoinContract.Presenter presenter) { }
}
