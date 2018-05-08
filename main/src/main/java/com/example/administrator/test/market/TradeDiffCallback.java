package com.example.administrator.test.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.PreCoin;

/**
 * Created by Administrator on 2018-02-26.
 */

public class TradeDiffCallback extends DiffUtil.Callback {
    private final List<PreCoin> oldTrades;
    private final List<PreCoin> newTrades;

    public TradeDiffCallback(List<PreCoin> oldTrades, List<PreCoin> newTrades) {
        this.oldTrades = oldTrades;
        this.newTrades = newTrades;
    }

    @Override
    public int getOldListSize() {
        return oldTrades.size();
    }

    @Override
    public int getNewListSize() {
        return newTrades.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        final PreCoin oldPreCoin = oldTrades.get(oldItemPosition);
        final PreCoin newPreCoin = newTrades.get(newItemPosition);

        return oldPreCoin.getName().equals(newPreCoin.getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final PreCoin oldPreCoin = oldTrades.get(oldItemPosition);
        final PreCoin newPreCoin = newTrades.get(newItemPosition);

        return oldPreCoin.getMarketPrice().equals(newPreCoin.getMarketPrice());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        final PreCoin oldPreCoin = oldTrades.get(oldItemPosition);
        final PreCoin newPreCoin = newTrades.get(newItemPosition);

        Float oldMarketPrice = Float.parseFloat(oldPreCoin.getMarketPrice());
        Float newMarketPrice = Float.parseFloat(newPreCoin.getMarketPrice());

        Bundle payload = new Bundle();
        if (!oldPreCoin.getMarketPrice().equals(newPreCoin.getMarketPrice())) {
            if (newMarketPrice - oldMarketPrice > 0) {
                payload.putString(Defines.RECYCLER_UPDATE, "up");
            } else {
                payload.putString(Defines.RECYCLER_UPDATE, "down");
            }
        }

        return payload;
    }
}
