package com.example.administrator.test.settings;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SettingsServiceInterface {
    @POST("/apis/setting/usage_detail")
    Single<JsonObject> usageDetail(@Body JsonObject jsonObject);

    @POST("/apis/setting/alarm_setting")
    Single<JsonObject> alarmSetting(@Body JsonObject jsonObject);

    @POST("/apis/setting/dropout")
    Single<JsonObject> dropOut(@Body JsonObject jsonObject);

    @POST("/apis/setting/create_2nd_pwd")
    Single<JsonObject> create2ndPwd(@Body JsonObject jsonObject);

    @POST("/apis/setting/reset_2nd_pwd")
    Single<JsonObject> reset2ndPwd(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/setting/auth_email")
    Single<JsonObject> authEmail(@Body JsonObject jsonObject);

    @GET("/apis/register/agreement")
    Single<JsonObject> agreement(@Query("lang") String countryCode);

    @POST("/apis/auth/send_email_code")
    Single<JsonObject> sendEmailCode(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/auth/confirm_email_code")
    Single<JsonObject> confirmEmailCode(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/auth/2nd_pwd")
    Single<JsonObject> secondPassword(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/setting/check_global_user")
    Single<JsonObject> checkGlobalUser(@Body JsonObject jsonObject);
}
