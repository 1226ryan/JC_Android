package com.example.administrator.test.market;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.bitpartner.BitPartnerApplication;
import co.bitpartner.R;
import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.PreCoin;
import co.bitpartner.util.DecimalFormatUtil;

/**
 * Created by Administrator on 2018-02-20.
 */

public class TradeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;

    @BindView(R.id.trade_name)
    TextView tvTradeName;

    @BindView(R.id.trade_money)
    TextView tradeMoney;

    @BindView(R.id.trade_unit)
    TextView tradeUnit;

    @BindView(R.id.trade_fluc)
    TextView tradeFluc;

    @BindView(R.id.up_down)
    ImageView flucImg;

    @BindView(R.id.trade_percentage)
    TextView percentange;

    public TradeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(PreCoin market, Bundle bundle, Boolean mIsFirst, ProgressBar progressBar, RecyclerView marketRecycler, double currencyPrice, String countryCurrency, String systemLang) {
        if (mIsFirst == false) {
            progressBar.setVisibility(View.GONE);
            marketRecycler.setVisibility(View.VISIBLE);
        }

        String tradeKor = market.getTradeName();
        if (systemLang.equals("ko"))
            tradeKor = convertTradeWithKor(tradeKor);
        tvTradeName.setText(tradeKor);

        tradeUnit.setText(countryCurrency);
        if (tradeKor.equals("bittrex") || tradeKor.equals("비트렉스"))
            tradeUnit.setText(R.string.usd);

        double marketPrice = Double.parseDouble(market.getMarketPrice());
        String fluc = market.get_24hFluc();
        NumberFormat format = NumberFormat.getNumberInstance();

        if (tradeKor.equals("bittrex"))
            tradeMoney.setText(format.format(DecimalFormatUtil.getUsdForamt(marketPrice)));
        else
            tradeMoney.setText(format.format(DecimalFormatUtil.getUsdForamt(marketPrice / currencyPrice)));

        tradeFluc.setText(fluc);
        if (fluc.equals("0.0")) {
            tradeMoney.setTextColor(Color.parseColor("#3c3c3c"));
            tradeFluc.setTextColor(Color.parseColor("#3c3c3c"));
            percentange.setTextColor(Color.parseColor("#3c3c3c"));
            flucImg.setVisibility(View.INVISIBLE);
        } else {
            boolean negative = fluc.startsWith("-");
            if (negative) {
                tradeMoney.setTextColor(Color.parseColor("#014070"));
                tradeFluc.setTextColor(Color.parseColor("#014070"));
                percentange.setTextColor(Color.parseColor("#014070"));
                flucImg.setVisibility(View.VISIBLE);
                flucImg.setImageResource(android.R.color.transparent);
                flucImg.setImageResource(R.drawable.fluc_down);
            } else {
                tradeMoney.setTextColor(Color.parseColor("#dc143c"));
                tradeFluc.setTextColor(Color.parseColor("#dc143c"));
                percentange.setTextColor(Color.parseColor("#dc143c"));
                flucImg.setVisibility(View.VISIBLE);
                flucImg.setImageResource(android.R.color.transparent);
                flucImg.setImageResource(R.drawable.fluc_up);
            }

            if (bundle != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                    if (bundle.getString(Defines.RECYCLER_UPDATE).equals("up")) {
                        rlRoot.setBackgroundResource(R.drawable.ripple_red);
                        Animation animation = AnimationUtils.loadAnimation(BitPartnerApplication.getInstance(), R.anim.blink);
                        rlRoot.setAnimation(animation);
                    } else if (bundle.getString(Defines.RECYCLER_UPDATE).equals("down")) {
                        rlRoot.setBackgroundResource(R.drawable.ripple_blue);
                        Animation animation = AnimationUtils.loadAnimation(BitPartnerApplication.getInstance(), R.anim.blink);
                        rlRoot.setAnimation(animation);
                    }
                }
            }
        }
    }

    private String convertTradeWithKor(String tradeName) {
        String tradeKor = "";
        switch (tradeName) {
            case "upbit":
                tradeKor = "업비트";
                break;
            case "bittrex":
                tradeKor = "비트렉스";
                break;
            case "bithumb":
                tradeKor = "빗썸";
                break;
            case "korbit":
                tradeKor = "코빗";
                break;
            case "binance":
                tradeKor = "바이낸스";
                break;
            case "coinone":
                tradeKor = "코인원";
                break;
        }
        return tradeKor;
    }
}
