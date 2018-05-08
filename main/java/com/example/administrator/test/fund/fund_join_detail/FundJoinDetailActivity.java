package com.example.administrator.test.fund.fund_join_detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import co.bitpartner.app.fund.fund_add_invest.FundAddInvestActivity;
import co.bitpartner.app.fund.fund_dis_join.FundDisJoinActivity;
import co.bitpartner.data.model.FundDetailRow;
import co.bitpartner.data.source.fund.FundRepository;
import co.bitpartner.util.DecimalFormatUtil;
import co.bitpartner.util.SharedPreferenceUtil;

public class FundJoinDetailActivity extends BaseActivity
        implements FundJoinDetailContract.View {

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
    @BindView(R.id.telegram_consult)
    TextView tvTelegram;

    @BindView(R.id.fund_won_krw2)
    TextView settingCurrency;
    @BindView(R.id.tv_min_fund_won_krw2)
    TextView joinCurrency;
    @BindView(R.id.tv_join_fund_won_krw2)
    TextView minJoinCurrency;

    private String fundID;
    private String fundJoinCnt;

    private String dayProfit = "";
    private String weekProfit = "";
    private String monthProfit = "";
    private String yearProfit = "";

    private String startDate = "";

    private FundJoinDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        fundID = intent.getStringExtra(Defines.EXTRA_FUND_ID);
        fundJoinCnt = intent.getStringExtra(Defines.EXTRA_FUND_JOIN_COUNT);
        dayProfit = intent.getStringExtra(Defines.EXTRA_DAY_FUND);
        weekProfit = intent.getStringExtra(Defines.EXTRA_WEEK_FUND);
        monthProfit = intent.getStringExtra(Defines.EXTRA_MONTH_FUND);
        yearProfit = intent.getStringExtra(Defines.EXTRA_YEAR_FUND);
        startDate = intent.getStringExtra(Defines.EXTRA_START_DATE);

        presenter = new FundJoinDetailPresenter(FundRepository.getInstance(), this);
        presenter.setFund(fundID, fundJoinCnt, dayProfit, weekProfit, monthProfit, yearProfit, startDate);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fund_join_detail;
    }

    @Override
    public void hideKakao() {
        tvKakao.setVisibility(View.GONE);
    }

    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showFundDate(String joinDate, String[] endDate) {
        fundStartDate.setText(joinDate);
        fundEndDate.setText(endDate[0]);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void showFundDetail(FundDetailRow row, String fundJoinCnt, double settingMoney, int currentCurreny, double minMoney, String currency, double countryCurrencyPrice) {
        fundTitle.setText(row.getName());
        fundCurrency.setText("["+row.getCurrency()+"]");

        //설정액
        fundWon.setText(DecimalFormatUtil.getFormatNumber(settingMoney));
        fundWonCurrency.setText(row.getCurrency());
        settingCurrency.setText(currency);

        //가입액
        fundWonJoin.setText(DecimalFormatUtil.getFormatNumber(Double.valueOf(fundJoinCnt)));        //가입액
        fundWonCurrencyJoin.setText(row.getCurrency());
        joinCurrency.setText(currency);

        //최소가입
        fundWonMin.setText(DecimalFormatUtil.getFormatNumber(minMoney));
        fundWonCurrencyMin.setText(row.getCurrency());
        minJoinCurrency.setText(currency);

        if(currency.equals("KRW")) {
            fundMoneyKrw.setText(""+DecimalFormatUtil.getFormat((int) ((settingMoney*currentCurreny) / countryCurrencyPrice)));
            fundMoneyKrwJoin.setText(""+DecimalFormatUtil.getFormat((int) ((Double.valueOf(fundJoinCnt)*currentCurreny) / countryCurrencyPrice)));
            fundMoneyKrwMin.setText(""+DecimalFormatUtil.getFormat((int) ((minMoney*currentCurreny) / countryCurrencyPrice)));
        } else {
            fundMoneyKrw.setText(""+ Double.parseDouble(String.format("%.2f",((settingMoney*currentCurreny) / countryCurrencyPrice))));
            fundMoneyKrwJoin.setText(""+ Double.parseDouble(String.format("%.2f",(Double.valueOf(fundJoinCnt)*currentCurreny) / countryCurrencyPrice)));
            fundMoneyKrwMin.setText(""+ Double.parseDouble(String.format("%.2f",(minMoney*currentCurreny) / countryCurrencyPrice)));
        }
    }

    @Override
    public void showDayProfit(String dayProfit) {
        dayFund.setText(dayProfit);
    }

    @Override
    public void showDayProfitUp() {
        dayFund.setTextColor(Color.parseColor("#dc143c"));
    }

    @Override
    public void showDayProfitDown() {
        dayFund.setTextColor(Color.parseColor("#014070"));
    }

    @Override
    public void showWeekProfit(String weekProfit) {
        weekFund.setText(weekProfit);
    }

    @Override
    public void showWeekProfitUp() {
        weekFund.setTextColor(Color.parseColor("#dc143c"));
    }

    @Override
    public void showWeekProfitDown() {
        weekFund.setTextColor(Color.parseColor("#014070"));
    }

    @Override
    public void showMonthProfit(String monthProfit) {
        monthFund.setText(monthProfit);
    }

    @Override
    public void showMonthProfitUp() {
        monthFund.setTextColor(Color.parseColor("#dc143c"));
    }

    @Override
    public void showMonthProfitDown() {
        monthFund.setTextColor(Color.parseColor("#014070"));
    }

    @Override
    public void showYearProfit(String yearProfit) {
        yearFund.setText(yearProfit);
    }

    @Override
    public void showYearProfitUp() {
        yearFund.setTextColor(Color.parseColor("#dc143c"));
    }

    @Override
    public void showYearProfitDown() {
        yearFund.setTextColor(Color.parseColor("#014070"));
    }

    @Override
    public void showFundContent(String info) {
        fundContent.setText(info);
    }

    @OnClick(R.id.rl_fee_info)
    public void onFeeInfoClicked() {
        presenter.processFeeInfo(llParentDetail);
    }

    private View view = null;
    @Override
    public void showFeeInfo(FundDetailRow row) {
        llParentDetail.setVisibility(View.VISIBLE);
        arrowFee.setImageResource(R.drawable.arrow_up);

        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.fund_fee_info, llDetailInfo, false);
            TextView joinFee = view.findViewById(R.id.join_fee);
            TextView profitFee = view.findViewById(R.id.profit_fee);
            TextView terminateFee = view.findViewById(R.id.terminate_fee);
            TextView endFee = view.findViewById(R.id.end_fee);

            joinFee.setText(row.getFundFee().getJoinFee()+"");
            profitFee.setText(row.getFundFee().getBenefitFee()+"");
            terminateFee.setText(row.getFundFee().getDropFee()+"");
            endFee.setText(row.getFundFee().getEndFee()+"");

            llDetailInfo.addView(view);
        }
    }

    @Override
    public void hideFeeInfo() {
        llParentDetail.setVisibility(View.GONE);
        arrowFee.setImageResource(R.drawable.arrow_down);
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

    @OnClick({R.id.img_back, R.id.rl_fund_join, R.id.kakao_fund_consult, R.id.rl_dis_join, R.id.telegram_consult})
    public void onButtonClicked(View view) {
        presenter.onButtonClicked(view.getId());
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void startFundAddInvestActivity(FundDetailRow row, String fundJoinCnt, String dateStartEnd, String fundID) {
        FundAddInvestActivity.start(this, row, fundJoinCnt, dateStartEnd, fundID);
    }

    @Override
    public void startFundDisjoinActivity(String fundID) {
        FundDisJoinActivity.start(this, fundID);
    }

    @Override
    public void startKakaoConsult() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SharedPreferenceUtil.getInstance().getKakaoUrl())));
    }

    @Override
    public void startTelegramConsult() {
        String telegramUrl;
        if(SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO)) {
            telegramUrl = getString(R.string.telegram_address);
        } else {
            telegramUrl = getString(R.string.telegram_address_en);
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl)));
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

    @Override
    public void setPresenter(FundJoinDetailContract.Presenter presenter) { }
}
