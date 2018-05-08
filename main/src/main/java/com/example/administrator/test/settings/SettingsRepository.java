package com.example.administrator.test.settings;

import com.example.administrator.test.rest.BitPartErrorException;
import com.example.administrator.test.rest.BitPartnerClient;
import com.google.gson.JsonObject;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SettingsRepository implements SettingsDataSource {

    private static SettingsRepository instance;
    public static SettingsRepository getInstance() {
        if(instance == null)
            instance = new SettingsRepository();

        return instance;
    }

    private BitPartErrorException errorCheck;   // error check 불러오기
    private BitPartnerClient client;            // client 불러오기

    private SettingsRepository() {
        client = BitPartnerClient.getInstance();
        errorCheck = BitPartErrorException.getInstance();
    }

    private SettingsServiceInterface service;       // service 지정 -> response 받아오기

    @Override
    public Single<JsonObject> usageDetail(int page_num) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("page_num", page_num);

        return service
                .usageDetail(jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> alarmSetting() {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hashkey", SharedPreferenceUtil.getInstance().getHashKey());
        jsonObject.addProperty("uid", SharedPreferenceUtil.getInstance().getUUID());
        jsonObject.addProperty("notification_allow", SharedPreferenceUtil.getInstance().getAlarmAllow());
        jsonObject.addProperty("notification_silence", SharedPreferenceUtil.getInstance().getAlarmSilence());
        jsonObject.addProperty("login_mail_receive", SharedPreferenceUtil.getInstance().getAlarmReceive());

        return service
                .alarmSetting(jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> dropOut() {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hashkey", SharedPreferenceUtil.getInstance().getHashKey());
        jsonObject.addProperty("uid", SharedPreferenceUtil.getInstance().getUUID());

        return service
                .dropOut(jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> create2ndPwd(String password) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);

        return service
                .create2ndPwd(jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> reset2ndPwd(String authToken, String password) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", password);

        return service
                .reset2ndPwd("Bearer " + authToken, jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> authEmail(String email, String lang) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("lang", lang);

        return service
                .authEmail(jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> agreement(String countryCode) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        return service
                .agreement(countryCode)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> sendEmailCode(String authToken, String lang) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lang", lang);

        return service
                .sendEmailCode("Bearer " + authToken, jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> confirmEmailCode(String authToken, String code) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);

        return service
                .confirmEmailCode("Bearer " + authToken, jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else
                        return false;
                })
                .toSingle();
    }

    @Override
    public Single<JsonObject> secondPassword(String authToken, String secondPassword) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", secondPassword);

        return service
                .secondPassword("Bearer " + authToken, jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else if (isSuccess == BitPartErrorException.FAIL)
                        return false;
                    else if (isSuccess == BitPartErrorException.RETRY_EXCEPTION)
                        throw new RetryException();
                    else
                        return false;
                })
                .retryWhen((Function<? super Flowable<Throwable>, ? extends Publisher<?>>) throwableFlowable ->
                        throwableFlowable.take(3).flatMap((Function<Throwable, Flowable<?>>) throwable -> {
                            if (throwable instanceof RetryException)
                                return Flowable.timer(2, TimeUnit.SECONDS);
                            else
                                return throwableFlowable;
                        }))
                .toSingle();
    }

    public Single<JsonObject> checkGlobalUser(String uuid, String hashKey) {
        service = client.getBitpartService(SettingsServiceInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hashkey", hashKey);
        jsonObject.addProperty("uid", uuid);

        return service
                .checkGlobalUser(jsonObject)
                .subscribeOn(Schedulers.io())
                .filter(it-> {
                    int isSuccess = errorCheck.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else if (isSuccess == BitPartErrorException.FAIL)
                        return false;
                    else
                        return false;
                })
                .toSingle();
    }
}
