package com.example.administrator.test.fund.fund_dis_join;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.common.AuthActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundCancelRow;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.BottomSheetDialogUtil;
import co.bitpartner.util.DateUtil;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.SharedPreferenceUtil;

public class FundDisJoinActivity extends BaseActivity
        implements FundDisjoinContract.View {

    @BindView(R.id.join_quantity_coin)
    TextView joinQuantityCoin;
    @BindView(R.id.join_quantity_krw)
    TextView joinQuantityKrw;

    @BindView(R.id.fund_quantity_coin)
    TextView fundQuantityCoin;
    @BindView(R.id.fund_quantity_krw)
    TextView fundQuantityKrw;

    @BindView(R.id.benefit_fee_coin)
    TextView benefitFeeCoin;
    @BindView(R.id.benefit_fee_krw)
    TextView benefitFeeKrw;

    @BindView(R.id.drop_fee_coin)
    TextView dropFeeCoin;
    @BindView(R.id.drop_fee_krw)
    TextView dropFeeKrw;

    @BindView(R.id.real_quantity_coin)
    TextView realQuantityCoin;
    @BindView(R.id.real_quantity_krw)
    TextView realQuantityKrw;

    @BindView(R.id.real_quantity_coin1)
    TextView mRealQuantityCoin;
    @BindView(R.id.real_quantity_krw1)
    TextView mRealQuantityKrw1;

    @BindView(R.id.date)
    TextView date;

    public static void start(Context context, String fundID) {
        Intent intent = new Intent(context, FundDisJoinActivity.class);
        intent.putExtra(Defines.EXTRA_FUND_ID, fundID);
        context.startActivity(intent);
    }

    private FundDisjoinPresenter presenter;
    private BottomSheetDialogUtil sheetDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String fundID = intent.getStringExtra(Defines.EXTRA_FUND_ID);

        presenter = new FundDisjoinPresenter(FundRepository.getInstance(), this);
        presenter.setFundID(fundID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        this.finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_dis_join;
    }

    @Override
    public void showCancelKRWInfo(int jointQuantityCurrency, int fundQuantityCurrency,
                                  int benefitFeeCurrency, int dropFitCurrency, int realQuantityCurrency) {
        joinQuantityKrw.setText("(" + jointQuantityCurrency + " KRW)");
        fundQuantityKrw.setText(getString(R.string.shift_9) + fundQuantityCurrency + " " + "KRW)");
        benefitFeeKrw.setText(getString(R.string.shift_9) + benefitFeeCurrency + " " +"KRW)");
        dropFeeKrw.setText(getString(R.string.shift_9) + dropFitCurrency + " " +"KRW)");
        realQuantityKrw.setText(getString(R.string.shift_9) + realQuantityCurrency + " " +"KRW)");
        mRealQuantityKrw1.setText(getString(R.string.shift_9) + realQuantityCurrency + " " +"KRW)");
    }

    @Override
    public void showCancelUSDInfo(double jointQuantityCurrency, double fundQuantityCurrency,
                                  double benefitFeeCurrency, double dropFitCurrency, double realQuantityCurrency, String currency) {
        joinQuantityKrw.setText(getString(R.string.shift_9) + jointQuantityCurrency +  " " + currency+")");
        fundQuantityKrw.setText(getString(R.string.shift_9) + fundQuantityCurrency + " " + currency+")");
        benefitFeeKrw.setText(getString(R.string.shift_9) + benefitFeeCurrency + " " + currency+")");
        dropFeeKrw.setText(getString(R.string.shift_9) + dropFitCurrency + " " + currency+")");
        realQuantityKrw.setText(getString(R.string.shift_9) + realQuantityCurrency + " " + currency+")");
        mRealQuantityKrw1.setText(getString(R.string.shift_9) + realQuantityCurrency + " " + currency+")");
    }

    @Override
    public void showCancelBTCInfo(FundCancelRow row) {
        joinQuantityCoin.setText(DecimalFormatUtil.getFormatNumber(row.joinQuantityCoin) + " " + getString(R.string.btc));
        fundQuantityCoin.setText(DecimalFormatUtil.getFormatNumber(row.fundQuantityCoin) + " " + getString(R.string.btc));
        benefitFeeCoin.setText(DecimalFormatUtil.getFormatNumber(row.benefitFeeCoin) + " " + getString(R.string.btc));
        dropFeeCoin.setText(DecimalFormatUtil.getFormatNumber(row.dropFeeCoin) + " " + getString(R.string.btc));
        realQuantityCoin.setText(DecimalFormatUtil.getFormatNumber(row.realQuantityCoin) + " " + getString(R.string.btc));
        mRealQuantityCoin.setText(DecimalFormatUtil.getFormatNumber(row.realQuantityCoin) + " " + getString(R.string.btc));

        date.setText(DateUtil.getCurrentTime());
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.dis_join)
    public void onDisJoinClicked() {
        presenter.processFundDisjoin();
    }

    @Override
    public void showCheckDialog() {
        sheetDialogUtil = BottomSheetDialogUtil.newInstance(Defines.KEY_FUND_DIS_JOIN);
        sheetDialogUtil.setOnAnswerClickListener(type -> presenter.processDialogAction(type));
        sheetDialogUtil.show(getSupportFragmentManager(), null);
    }

    @Override
    public void hideCheckDialog() {
        sheetDialogUtil.dismiss();
    }

    @Override
    public void startAuthActivity() {
        AuthActivity.start(this, String.valueOf(SharedPreferenceUtil.getInstance().getUserLevel()), "fund_dis_join");
    }

    @Override
    public void setPresenter(FundDisjoinContract.Presenter presenter) { }
}
