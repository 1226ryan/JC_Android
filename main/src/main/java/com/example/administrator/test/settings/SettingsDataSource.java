package com.example.administrator.test.settings;

import com.google.gson.JsonObject;

import io.reactivex.Single;

public interface SettingsDataSource {
    Single<JsonObject> usageDetail(int page_num);

    Single<JsonObject> alarmSetting();

    Single<JsonObject> dropOut();

    Single<JsonObject> create2ndPwd(String password);

    Single<JsonObject> reset2ndPwd(String authToken, String email);

    Single<JsonObject> authEmail(String email, String lang);

    Single<JsonObject> agreement(String countryCode);

    Single<JsonObject> sendEmailCode(String authToken, String lang);

    Single<JsonObject> confirmEmailCode(String authToken, String code);

    Single<JsonObject> secondPassword(String authToken, String secondPassword);
}
