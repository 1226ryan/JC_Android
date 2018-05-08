package com.example.administrator.test.asset;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.bitpartner.R;

/**
 * Created by Administrator on 2018-02-13.
 */

public class CoinViewHolder  extends RecyclerView.ViewHolder {

    @BindView(R.id.coin_name)
    TextView coinName;

    @BindView(R.id.coin_profit)
    TextView coinProfit;

    @BindView(R.id.coin_won)
    TextView coinWon;

    public CoinViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(Map<String, String> coinMap) {
        DecimalFormat decimalFormat = new DecimalFormat("0.########");
        Float coin = Float.valueOf(coinMap.get("won"));

        coinName.setText(coinMap.get("coin_name"));
        coinProfit.setText(coinMap.get("profit"));
        coinWon.setText(decimalFormat.format(coin));
        System.out.println("coinMap.get(\"coin_name\") : "+coinMap.get("coin_name"));
        System.out.println("coinMap.get(\"profit\") : "+coinMap.get("profit"));
        System.out.println("decimalFormat.format(coin) : "+decimalFormat.format(coin));
    }
}