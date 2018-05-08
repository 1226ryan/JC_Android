package com.example.administrator.test.asset;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import co.bitpartner.R;

/**
 * Created by Bridge on 2018-01-08.
 */

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinViewHolder> {
    private List<Map<String, String>> coins;   // 개별 코인 수익 현황

    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_coin, parent, false);
        return new CoinViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        if (coins != null && coins.size() != 0)
            holder.bind(coins.get(position));
    }

    @Override
    public int getItemCount() {
        return coins == null ? 0 : coins.size();
    }

    public void setCoins(List<Map<String, String>> coins) {
        this.coins = coins;
        this.notifyDataSetChanged();
    }
}
