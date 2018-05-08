package com.example.administrator.test.market;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.GroupPreCoin;
import co.bitpartner.data.model.PreCoin;
import co.bitpartner.util.DateUtil;
import co.bitpartner.util.SequenceUtil;
import co.bitpartner.util.SharedPreferenceUtil;

/**
 * Created by Administrator on 2018-02-07.
 */

public class MarketViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.coin_name_kor)
    TextView coinKor;

    @BindView(R.id.coin_name_eng)
    TextView coinEng;

    @BindView(R.id.up_to_date)
    TextView upToDate;

    @BindView(R.id.trade_recycler)
    RecyclerView tradeRecycler;

    public MarketViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //코인 갯수 만큼
    public void bind(GroupPreCoin coins, Context context, TradeRecyclerAdapter tradeRecyclerAdapter, List<String> tradesOrder, String systemLang) {
        String date = coins.getUtcDate();

        if (!systemLang.equals("ko"))
            coinKor.setVisibility(View.GONE);
        else
            coinKor.setText(coins.getName());

        coinEng.setText(coins.getCoinNameEng());
        if (SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO)) {
            upToDate.setText(DateUtil.getTime(date));
        } else {
            upToDate.setText(coins.getUtcDate());
        }

        if (tradeRecyclerAdapter != null) {
            TradeRecyclerAdapter tradeAdapter = tradeRecyclerAdapter;
            MarketLinearLayoutManager layoutManager = new MarketLinearLayoutManager(context);
            layoutManager.setSmoothScrollbarEnabled(true);
            tradeRecycler.getRecycledViewPool().clear();
            tradeRecycler.setLayoutManager(layoutManager);
            tradeRecycler.setHasFixedSize(true);
            tradeRecycler.setAdapter(tradeAdapter);
            List<PreCoin> trades = SequenceUtil.getTradeSequence(tradesOrder, coins.getPreCoins());
            tradeAdapter.setTrade(trades);
        }
    }
}























