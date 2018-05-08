package com.example.administrator.test.fund;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.FundList;
import co.bitpartner.util.SharedPreferenceUtil;

/**
 * Created by Bridge on 2017-12-26.
 */

public class FundRecyclerAdapter extends RecyclerView.Adapter<FundViewHolder> {
    private List<Map<String, String>> mapFunds = new ArrayList<>();          //내 자산 탭 -> 펀드
    private FundJoinCallback joinCallback;

    private List<FundList> funds = new ArrayList<>();                       //펀드 탭
    private FundDisjoinCallback disjoinCallback;

    private int fundModelType;
    private double currencyPrice;
    private String countrCurrency;

    public FundRecyclerAdapter(int fundModelType) {
        this.fundModelType = fundModelType;
        this.currencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
        this.countrCurrency = SharedPreferenceUtil.getInstance().getCountryCurrency();
    }

    @Override
    public FundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_fund, parent, false);
        return new FundViewHolder(rootView);
    }

    public void setCallback(FundJoinCallback callback) {
        this.joinCallback = callback;
    }

    public void setCallback(FundDisjoinCallback callback) {
        this.disjoinCallback = callback;
    }

    @Override
    public void onBindViewHolder(FundViewHolder holder, final int position) {
        if (fundModelType == Defines.TYPE_ALREADY_JOIN_FUND) {
            holder.bind(mapFunds.get(position));
            holder.itemView.setOnClickListener(view -> joinCallback.onFundDetailClicked(mapFunds.get(position)));
        }
        else {  //not join, recommand
            holder.bind(getFundItem(position), currencyPrice, countrCurrency);
            holder.itemView.setOnClickListener(view -> disjoinCallback.onFundDetailClicked(getFundItem(position)));
        }
    }

    public FundList getFundItem(int position) {
        return funds.get(position);
    }

    @Override
    public int getItemCount() {
        if (fundModelType == Defines.TYPE_ALREADY_JOIN_FUND)
            return mapFunds == null ? 0 : mapFunds.size();
        else
            return funds == null ? 0 : funds.size();
    }

    public void setFunds(List<Map<String, String>> mFunds) {
        this.mapFunds.clear();
        this.mapFunds.addAll(mFunds);
        this.notifyDataSetChanged();
    }

    public void setFundsInOffer(List<FundList> funds) {
        setFundDescendingOrder(funds);
        this.funds.clear();
        this.funds.addAll(funds);
        this.notifyDataSetChanged();
    }

    private void setFundDescendingOrder(List<FundList> funds) {
        Collections.sort(funds, (fundOne, fundTwo) -> {

            double preCurFund = fundOne.getFundAsset().getPreAsset();
            double preWeekFund = fundOne.getFundAsset().getWeekAsset();
            double preWeekProfit;
            if (preWeekFund == 0)
                preWeekProfit = 0.0;
            else
                preWeekProfit = (preCurFund - preWeekFund) / preWeekFund * 100;

            double nextCurFund = fundTwo.getFundAsset().getPreAsset();
            double nextWeekFund = fundTwo.getFundAsset().getWeekAsset();
            double nextWeekProfit;
            if (nextWeekFund  == 0)
                nextWeekProfit = 0.0;
            else
                nextWeekProfit = (nextCurFund - nextWeekFund) / nextWeekFund * 100;

            return Double.compare(nextWeekProfit, preWeekProfit);
        });
    }

    public interface FundJoinCallback {
        void onFundDetailClicked(Map<String, String> mapFund);
    }
    public interface FundDisjoinCallback {
        void onFundDetailClicked(FundList fund);
    }
}
