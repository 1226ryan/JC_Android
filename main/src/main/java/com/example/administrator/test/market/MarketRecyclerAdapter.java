package com.example.administrator.test.market;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import co.bitpartner.R;
import co.bitpartner.data.model.GroupPreCoin;
import co.bitpartner.util.SharedPreferenceUtil;

/**
 * Created by Bridge on 2017-12-26.
 */

public class MarketRecyclerAdapter extends RecyclerView.Adapter<MarketViewHolder> {
    private List<GroupPreCoin> markets;
    private Context context;
    private TradeRecyclerAdapter[] tradeAdapters;
    private List<String> tradesOrder;
    private boolean isFirst = false;
    private RecyclerView marketRecycler;
    private ProgressBar progressBar;
    private double currencyPrice;
    private String countryCurrency;
    private String systemLang;

    public MarketRecyclerAdapter(List<String> tradesOrder, Context context, RecyclerView marketRecycler, ProgressBar progressBar) {
        this.markets = new ArrayList<>();
        this.context = context;
        this.tradesOrder = tradesOrder;
        this.marketRecycler = marketRecycler;
        this.progressBar = progressBar;
        this.currencyPrice = SharedPreferenceUtil.getInstance().getCurrencyPrice();
        this.countryCurrency = SharedPreferenceUtil.getInstance().getCountryCurrency();
        this.systemLang = SharedPreferenceUtil.getInstance().getSystemCountryLanguage();
    }

    @Override
    public MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market, parent, false);
        return new MarketViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, int position) {
        holder.bind(markets.get(position), context, tradeAdapters[position], tradesOrder, systemLang);
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public void setMarkets(List<GroupPreCoin> markets, boolean uiBlocking) {
        createTradeAdapters(markets.size());
        if (uiBlocking == false) {
            this.markets = markets;
            this.notifyDataSetChanged();
        }
    }

    //최초 한번만 실행되도록, diffutil 클래스 활용을 위해
    private void createTradeAdapters(int size) {
        if (isFirst == false) {
            this.tradeAdapters = new TradeRecyclerAdapter[size];
            for(int i=0; i<size; i++)
                tradeAdapters[i] = new TradeRecyclerAdapter(
                        currencyPrice,
                        countryCurrency,
                        systemLang,
                        isFirst, marketRecycler, progressBar);

            isFirst = true;
        }
    }

}
