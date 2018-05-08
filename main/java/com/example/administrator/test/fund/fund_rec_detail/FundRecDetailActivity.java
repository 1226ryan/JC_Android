package com.example.administrator.test.fund.fund_rec_detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import co.bitpartner.R;
import co.bitpartner.app.BaseActivity;
import co.bitpartner.app.common.Defines;
import co.bitpartner.app.fund.fund_join.FundJoinActivity;
import co.bitpartner.app.wallet.bitcoin_io.BitcoinIOActivity;
import co.bitpartner.data.model.FundList;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.DialogUtil;
import co.bitpartner.util.SharedPreferenceUtil;

/**
 * Created by Bridge on 2018-01-15.
 */

public class FundRecDetailActivity extends BaseActivity implements FundRecDetailContract.View {

    @BindView(R.id.fund_name)
    TextView fundTitle;
    @BindView(R.id.fund_currency)
    TextView fundCurrency;
    @BindView(R.id.fund_start_date)
    TextView fundStartDate;
    @BindView(R.id.fund_end_date)
    TextView fundEndDate;

    @BindView(R.id.fund_won)
    TextView fundWon;          //설정액 -> 최대 모집금액
    @BindView(R.id.fund_won_currency)
    TextView fundWonCurrency;
    @BindView(R.id.fund_won_krw)
    TextView fundMoneyKrw;

    @BindView(R.id.tv_join_fund_won)
    TextView fundWonJoin;      //가입액 -> 현재 모집된 금액
    @BindView(R.id.tv_join_fund_won_currency)
    TextView fundWonCurrencyJoin;
    @BindView(R.id.tv_join_fund_won_krw)
    TextView fundMoneyKrwJoin;

    @BindView(R.id.tv_min_fund_won)
    TextView fundWonMin;          //최소 가입액 -> 가입해야할 최소 금액
    @BindView(R.id.tv_min_fund_won_currency)
    TextView fundWonCurrencyMin;
    @BindView(R.id.tv_min_fund_won_krw)
    TextView fundMoneyKrwMin;

    @BindView(R.id.day_fund)
    TextView dayFund;
    @BindView(R.id.week_fund)
    TextView weekFund;
    @BindView(R.id.month_fund)
    TextView monthFund;
    @BindView(R.id.year_fund)
    TextView yearFund;
    @BindView(R.id.fund_content)
    TextView fundContent;

    @BindView(R.id.fund_regist)
    TextView tvFundRegist;

    @BindView(R.id.scrollview)
    ScrollView scrollView;

    @BindView(R.id.ll_parent_detail)
    LinearLayout llParentDetail;
    @BindView(R.id.ll_detail_info)
    LinearLayout llDetailInfo;
    @BindView(R.id.warning_content)
    TextView warningContent;

    @BindView(R.id.arrow_fee)
    ImageView arrowFee;
    @BindView(R.id.arrow_etc)
    ImageView arrowEtc;

    @BindView(R.id.kakao_fund_consult)
    TextView tvKakao;

    @BindView(R.id.fund_won_krw2)
    TextView settingPriceCurrency;
    @BindView(R.id.tv_join_fund_won_krw2)
    TextView joinPriceCurrency;
    @BindView(R.id.tv_min_fund_won_krw2)
    TextView minPriceCurrency;

    public static void start(Context context, FundList fund) {
        Intent intent = new Intent(context, FundRecDetailActivity.class);
        intent.putExtra(Defines.EXTRA_FUND_TYPE, Defines.TYPE_NOT_JOIN_FUND);
        intent.putExtra(Defines.EXTRA_FUND_PARCELABLE, fund);
        context.startActivity(intent);
    }

    private DialogUtil dialogUtil;
    private FundRecDetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        FundList fund = intent.getParcelableExtra(Defines.EXTRA_FUND_PARCELABLE);

        presenter = new FundRecDetailPresenter(FundRepository.getInstance(), this);
        presenter.setFund(fund);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_rec_detail;
    }

    @Override
    public void hideKakao() {
        tvKakao.setVisibility(View.GONE);
    }

    @Override
    public void showFundDate(String[] startDate, String[] endDate) {
        fundStartDate.setText(startDate[0]);
        fundEndDate.setText(endDate[0]);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void showFundDetail(FundList fund, double currencyPrice, String countryCurrency) {
        double settingMoney = fund.getMaxAmount();
        double currentMoney = fund.getPreAmount();
        double minMoney = fund.getMinJoinCnt();
        int currentCurreny = fund.getCurPrice();    //현재가

        fundTitle.setText(fund.getName());
        fundCurrency.setText("["+fund.getCurrency()+"] ");

        fundWon.setText(DecimalFormatUtil.getFormatNumber(settingMoney));
        fundWonCurrency.setText(fund.getCurrency());
        settingPriceCurrency.setText(countryCurrency);

        fundWonJoin.setText(DecimalFormatUtil.getFormatNumber(currentMoney));
        fundWonCurrencyJoin.setText(fund.getCurrency());
        joinPriceCurrency.setText(countryCurrency);

        fundWonMin.setText(DecimalFormatUtil.getFormatNumber(minMoney));
        fundWonCurrencyMin.setText(fund.getCurrency());
        minPriceCurrency.setText(countryCurrency);

        if(countryCurrency.equals("KRW")) {
            fundMoneyKrw.setText(""+DecimalFormatUtil.getFormat( (int) ((settingMoney*currentCurreny) / currencyPrice) ) );
            fundMoneyKrwJoin.setText(""+DecimalFormatUtil.getFormat( (int) ((currentMoney*currentCurreny) / currencyPrice) ) );
            fundMoneyKrwMin.setText(""+DecimalFormatUtil.getFormat( (int) ((minMoney*currentCurreny) / currencyPrice) ) );
        } else {
            fundMoneyKrw.setText(""+ Double.parseDouble(String.format("%.2f",((settingMoney*currentCurreny) / currencyPrice) )));
            fundMoneyKrwJoin.setText(""+ Double.parseDouble(String.format("%.2f",((currentMoney*currentCurreny) / currencyPrice) )));
            fundMoneyKrwMin.setText(""+ Double.parseDouble(String.format("%.2f",((minMoney*currentCurreny) / currencyPrice) )));
        }

        fundContent.setText(fund.getInfo());
    }

    @Override
    public void showDayProfit(float dayProfit) { dayFund.setText(String.format("%.2f", dayProfit)); }

    @Override
    public void showNoDayProfit() { dayFund.setText("0"); }

    @Override
    public void showDayProfitUp() { dayFund.setTextColor(Color.parseColor("#dc143c")); }

    @Override
    public void showDayProfitDown() { dayFund.setTextColor(Color.parseColor("#014070")); }

    @Override
    public void showWeekProfit(float weekProfit) { weekFund.setText(String.format("%.2f", weekProfit)); }

    @Override
    public void showNoWeekProfit() { weekFund.setText("0"); }

    @Override
    public void showWeekProfitUp() { weekFund.setTextColor(Color.parseColor("#dc143c")); }

    @Override
    public void showWeekProfitDown() { weekFund.setTextColor(Color.parseColor("#014070")); }

    @Override
    public void showMonthProfit(float monthProfit) { monthFund.setText(String.format("%.2f", monthProfit)); }

    @Override
    public void showNoMonthProfit() { monthFund.setText("0"); }

    @Override
    public void showMonthProfitUp() { monthFund.setTextColor(Color.parseColor("#dc143c")); }

    @Override
    public void showMonthProfitDown() { monthFund.setTextColor(Color.parseColor("#014070")); }

    @Override
    public void showYearProfit(float yearProfit) { yearFund.setText(String.format("%.2f", yearProfit)); }

    @Override
    public void showNoYearProfit() { yearFund.setText("0"); }

    @Override
    public void showYearProfitUp() { yearFund.setTextColor(Color.parseColor("#dc143c")); }

    @Override
    public void showYearProfitDown() { yearFund.setTextColor(Color.parseColor("#014070")); }

    @Override
    public void showNoFundProfit() {
        dayFund.setText("0");
        weekFund.setText("0");
        monthFund.setText("0");
        yearFund.setText("0");
    }

    @OnClick(R.id.img_back)
    public void onBackClicked() {
        finish();
    }

    @OnClick(R.id.rl_fund_join)
    public void onFundJoinClicked() {
        presenter.processFundJoin();
    }

    @OnClick(R.id.kakao_fund_consult)
    public void onKakaoConsultClicked() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SharedPreferenceUtil.getInstance().getKakaoUrl())));
    }

    @OnClick(R.id.telegram_consult)
    public void onTelegramConsultClicked() {
        String telegramUrl;
        if(SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO)) {
            telegramUrl = getString(R.string.telegram_address);
        } else {
            telegramUrl = getString(R.string.telegram_address_en);
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl)));
    }

    @Override
    public void showFailureDialog() {
        dialogUtil = DialogUtil.getDialog(this, getString(R.string.coin_not_enough), getString(R.string.coin_need), yesListener, noListener);
        dialogUtil.show();
    }

    @Override
    public void startFundJoinActivity(double privateAsset, double coinKrw, double minJoinCount, FundList fund) {
        FundJoinActivity.start(this, privateAsset, coinKrw, minJoinCount, fund);
    }

    private View view = null;
    @OnClick(R.id.rl_fee_info)
    public void onFeeInfoClicked() {
        presenter.processFeeInfo(llParentDetail);
    }

    @Override
    public void hideFeeInfo() {
        llParentDetail.setVisibility(View.GONE);
        arrowFee.setImageResource(R.drawable.arrow_down);
    }

    @Override
    public void showFeeInfo(FundList fund) {
        llParentDetail.setVisibility(View.VISIBLE);
        arrowFee.setImageResource(R.drawable.arrow_up);

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.fund_fee_info, llDetailInfo, false);
            TextView joinFee = view.findViewById(R.id.join_fee);
            TextView profitFee = view.findViewById(R.id.profit_fee);
            TextView terminateFee = view.findViewById(R.id.terminate_fee);
            TextView endFee = view.findViewById(R.id.end_fee);

            joinFee.setText(fund.getFundFee().getJoinFee()+"");
            profitFee.setText(fund.getFundFee().getBenefitFee()+"");
            terminateFee.setText(fund.getFundFee().getDropFee()+"");
            endFee.setText(fund.getFundFee().getEndFee()+"");

            llDetailInfo.addView(view);
        }
    }

    @Override
    public void scrollDown() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @OnClick(R.id.rl_etc_info)
    public void onEtcInfoClicked() {
        presenter.processEtcInfo(warningContent);
    }

    @Override
    public void showWarningInfo(String additionalInfo) {
        warningContent.setVisibility(View.VISIBLE);
        warningContent.setText(additionalInfo);
        arrowEtc.setImageResource(R.drawable.arrow_up);
    }

    @Override
    public void hideWarningInfo() {
        warningContent.setVisibility(View.GONE);
        arrowEtc.setImageResource(R.drawable.arrow_down);
    }

    private View.OnClickListener yesListener = new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(FundRecDetailActivity.this, BitcoinIOActivity.class));
            dialogUtil.dismiss();
        }
    };

    private View.OnClickListener noListener = new View.OnClickListener() {
        public void onClick(View v) { dialogUtil.dismiss(); }
    };

    @Override
    public void setPresenter(FundRecDetailContract.Presenter presenter) { }
}
