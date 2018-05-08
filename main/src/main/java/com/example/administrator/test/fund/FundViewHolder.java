package com.example.administrator.test.fund;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.bitpartner.R;
import co.bitpartner.data.model.FundAsset;
import co.bitpartner.data.model.FundList;
import co.bitpartner.util.DecimalFormatUtil;

/**
 * Created by Administrator on 2018-02-14.
 */

public class FundViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fund_name)
    TextView fundTitle;
    @BindView(R.id.fund_currency)
    TextView fundCurrency;
    @BindView(R.id.fund_start_date)
    TextView fundStartDate;
    @BindView(R.id.fund_end_date)
    TextView fundEndDate;
    @BindView(R.id.tv_setting_size)
    TextView fundJoinCount;

    @BindView(R.id.fund_won)
    TextView fundWon;
    @BindView(R.id.fund_currency2)
    TextView fundCurrency2;

    @BindView(R.id.fund_won_s)
    TextView left;
    @BindView(R.id.right_shift)
    TextView right;
    @BindView(R.id.fund_won_krw)
    TextView fundWonKrw;
    @BindView(R.id.fund_won_krw2)
    TextView countryCurrency;

    @BindView(R.id.day_fund)
    TextView dayFund;
    @BindView(R.id.week_fund)
    TextView weekFund;
    @BindView(R.id.month_fund)
    TextView monthFund;
    @BindView(R.id.year_fund)
    TextView yearFund;

    public FundViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    //내 자산에 펀드
    public void bind(Map<String, String> fundMap) {
        String startDate[] = fundMap.get("fund_date").split(" ");
        String endDate[] = fundMap.get("fund_end_date").split(" ");
        double joinCnt = Double.valueOf(fundMap.get("join_coin_cnt"));  //가입액

        fundJoinCount.setText(R.string.join_money);
        fundCurrency.setText("["+fundMap.get("fund_currency")+"]");

        fundTitle.setText(fundMap.get("fund_name"));
        fundStartDate.setText(startDate[0]);
        fundEndDate.setText(endDate[0]);
        fundWon.setText(DecimalFormatUtil.getFormatNumber(joinCnt));
        fundCurrency2.setText(fundMap.get("fund_currency"));

        left.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        fundWonKrw.setVisibility(View.GONE);
        countryCurrency.setVisibility(View.GONE);

        float todayProfit = Float.parseFloat(fundMap.get("today_profit"));
        float weekProfit = Float.parseFloat(fundMap.get("week_profit"));
        float monthProfit = Float.parseFloat(fundMap.get("month_profit"));
        float yearProfit = Float.parseFloat(fundMap.get("year_profit"));

        dayFund.setText(fundMap.get("today_profit"));
        if (todayProfit > 0) dayFund.setTextColor(Color.parseColor("#dc143c"));
        else if (todayProfit < 0) dayFund.setTextColor(Color.parseColor("#014070"));

        weekFund.setText(fundMap.get("week_profit"));
        if (weekProfit > 0) weekFund.setTextColor(Color.parseColor("#dc143c"));
        else if (weekProfit < 0) weekFund.setTextColor(Color.parseColor("#014070"));

        monthFund.setText(fundMap.get("month_profit"));
        if (monthProfit > 0) monthFund.setTextColor(Color.parseColor("#dc143c"));
        else if (monthProfit < 0) monthFund.setTextColor(Color.parseColor("#014070"));

        yearFund.setText(fundMap.get("year_profit"));
        if (yearProfit > 0) yearFund.setTextColor(Color.parseColor("#dc143c"));
        else if (yearProfit < 0) yearFund.setTextColor(Color.parseColor("#014070"));
    }

    //펀드 탭
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void bind(FundList fundList, double currencyPrice, String countrCurrency) {
        String startDate[] = fundList.getStartDate().split(" ");
        String endDate[] = fundList.getEndDate().split(" ");

        double settingMoney = fundList.getMaxAmount();
        int currentCurreny = fundList.getCurPrice();

        fundTitle.setText(fundList.getName());
        fundCurrency.setText("["+fundList.getCurrency()+"]");
        fundStartDate.setText(startDate[0]);
        fundEndDate.setText(endDate[0]);

        int settingMon = (int) settingMoney;
        fundWon.setText(String.valueOf((settingMon)));
        fundCurrency2.setText(fundList.getCurrency());

        double countryCurrencyPrice = (settingMoney*currentCurreny) / currencyPrice;
        if(countrCurrency.equals("KRW")) {
            fundWonKrw.setText(""+DecimalFormatUtil.getFormat((int) countryCurrencyPrice));
        } else {
            fundWonKrw.setText(""+ Double.parseDouble(String.format("%.2f",countryCurrencyPrice)));
        }
        countryCurrency.setText(countrCurrency);

        FundAsset fundAsset = fundList.getFundAsset();
        if (fundAsset != null) {
            double currentFund = (double) fundAsset.getPreAsset();
            double day = (double) fundAsset.getDayAsset();
            double week = (double) fundAsset.getWeekAsset();
            double month = (double) fundAsset.getMonthAsset();
            double year = (double) fundAsset.getYearAsset();

            if (day != 0) {
                double dayProfit = ((currentFund-day) / day) * 100;
                if (dayProfit > 0)
                    dayFund.setTextColor(Color.parseColor("#dc143c"));
                else if (dayProfit < 0)
                    dayFund.setTextColor(Color.parseColor("#014070"));

                dayFund.setText(String.format("%.2f", dayProfit));
            }
            else
                dayFund.setText("0");

            if (week != 0) {
                double weekProfit = ((currentFund-week) / week) * 100;
                if (weekProfit > 0)
                    weekFund.setTextColor(Color.parseColor("#dc143c"));
                else if (weekProfit < 0)
                    weekFund.setTextColor(Color.parseColor("#014070"));

                weekFund.setText(String.format("%.2f", weekProfit));
            }
            else
                weekFund.setText("0");

            if (month != 0) {
                double monthProfit = ((currentFund - month) / month) * 100;
                if (monthProfit > 0)
                    monthFund.setTextColor(Color.parseColor("#dc143c"));
                else if (monthProfit < 0)
                    monthFund.setTextColor(Color.parseColor("#014070"));

                monthFund.setText(String.format("%.2f", monthProfit));
            }
            else
                monthFund.setText("0");

            if (year != 0) {
                double yearProfit = ((currentFund - year) / year) * 100;
                if (yearProfit > 0)
                    yearFund.setTextColor(Color.parseColor("#dc143c"));
                else if (yearProfit < 0)
                    yearFund.setTextColor(Color.parseColor("#014070"));

                yearFund.setText(String.format("%.2f", yearProfit));
            }
            else
                yearFund.setText("0");

        } else {
            dayFund.setText("0");
            weekFund.setText("0");
            monthFund.setText("0");
            yearFund.setText("0");
        }
    }
}
