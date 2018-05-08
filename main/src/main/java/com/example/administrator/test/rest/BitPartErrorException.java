package com.example.administrator.test.rest;

import android.support.annotation.Nullable;

import com.example.administrator.test.rxbus.Events;
import com.example.administrator.test.rxbus.RxEventBus;
import com.google.gson.JsonObject;

import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.TokenRow;
import co.bitpartner.data.model.command.RefreshTokenCommand;
import co.bitpartner.rxbus.Events;
import co.bitpartner.rxbus.RxEventBus;
import co.bitpartner.util.GsonUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-02-26.
 */

public class BitPartErrorException {
    private static final String TAG = BitPartErrorException.class.getSimpleName();

    private volatile static BitPartErrorException instance;

    public static BitPartErrorException getInstance() {
        if (instance == null) {
            synchronized (BitPartErrorException.class) {
                if (instance == null)
                    instance = new BitPartErrorException();
            }
        }

        return instance;
    }

    private Single<JsonObject> call;
    public void checkErrorCode(final Single<JsonObject> call, final ResultCallback<JsonObject> callback) {
        this.call = call;
        this.call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    String result = jsonObject.get("result").getAsString();

                    if (result.equals(Defines.CODE_8410) || result.equals(Defines.CODE_8400) || result.equals(Defines.CODE_4030)) {

                        switch (result) {
                            case Defines.CODE_8410: //acess token 만료
                                refreshAccessToken();
                                break;
                            case Defines.CODE_8400: //refresh token 만료
                            case Defines.CODE_4030: //invalid user
                                sendEventBus();
                                break;
                        }
                    }
                    else
                        callback.onSuccess(jsonObject);
                }, throwable -> {
                    callback.onFailure(throwable);
                });
    }


    private void sendEventBus() {
        RxEventBus.getInstance().post(new Events.DialogFloatEvent());
    }

    private void refreshAccessToken() {
        SharedPreferenceUtil spUtil = SharedPreferenceUtil.getInstance();
        RefreshTokenCommand tokenCommand = new RefreshTokenCommand(spUtil.getHashKey(), spUtil.getUUID(), spUtil.getRefreshToken());
        BitPartnerClient.getInstance().refreshAccessToken(tokenCommand, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                TokenRow row = (TokenRow) GsonUtil.getInstance().fromJson(jsonObject.get("row").toString(), TokenRow.class);
                SharedPreferenceUtil.getInstance().setAccessToken(row.getAccessToken());

                call.retry();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int TOKEN_EXPIRED = 2;
    public static final int RETRY_EXCEPTION = 3;

    public int checkErrorCode(JsonObject jsonObject) {
        String result = jsonObject.get("result").getAsString();
        if (result.equals(Defines.CODE_8410) || result.equals(Defines.CODE_8400) || result.equals(Defines.CODE_4030) || result.equals(Defines.CODE_1800)) {

            switch (result) {
                case Defines.CODE_8410: //acess token 만료
                    refreshAccessToken();
                    break;
                case Defines.CODE_8400: //refresh token 만료
                case Defines.CODE_4030: //invalid user
                    sendEventBus();
                    break;
                case Defines.CODE_1800:
                    return RETRY_EXCEPTION;
            }
//            if (BitPartnerApplication.DEBUG == true)
//                sendErrorCodeEvent(result);

            return FAIL;
        }
        else {
//            if (!result.equals(Defines.CODE_1000) && BitPartnerApplication.DEBUG == true)
//                sendErrorCodeEvent(result);

            return SUCCESS;
        }
    }

    private void sendErrorCodeEvent(String result) {
        RxEventBus.getInstance().post(new Events.ErrorCodeEvent(result));
    }


}
