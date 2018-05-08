package com.example.administrator.test.market;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import co.bitpartner.R;
import co.bitpartner.data.model.PreCoin;

/**
 * Created by Administrator on 2018-02-20.
 */

public class TradeRecyclerAdapter extends RecyclerView.Adapter<TradeViewHolder> {
    private List<PreCoin> markets;
    private boolean mIsFirst;
    private RecyclerView marketRecycler;
    private ProgressBar progressBar;
    private double currencyPrice;
    private String countryCurrency;
    private String systemLang;

    public TradeRecyclerAdapter(double currencyPrice, String countryCurrency, String systemLang, boolean isFirst, RecyclerView marketRecycler, ProgressBar progressBar) {
        this.currencyPrice = currencyPrice;
        this.countryCurrency = countryCurrency;
        this.systemLang = systemLang;
        this.markets = new ArrayList<>();
        this.mIsFirst = isFirst;
        this.marketRecycler = marketRecycler;
        this.progressBar = progressBar;
    }

    @Override
    public TradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trade, parent, false);
        return new TradeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TradeViewHolder holder, int position) {
        holder.bind(markets.get(position), null, mIsFirst, progressBar, marketRecycler, currencyPrice, countryCurrency, systemLang);
    }

    @Override
    public void onBindViewHolder(TradeViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else {
            Bundle bundle = (Bundle) payloads.get(0);
            holder.bind(markets.get(position), bundle, mIsFirst, progressBar, marketRecycler, currencyPrice, countryCurrency, systemLang);
        }
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public void setTrade(List<PreCoin> coins) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final TradeDiffCallback diffCallback = new TradeDiffCallback(markets, coins);
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                diffResult.dispatchUpdatesTo(TradeRecyclerAdapter.this);

                markets.clear();
                markets.addAll(coins);
            }
        });
    }

}
