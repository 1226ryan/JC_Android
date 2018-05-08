package com.example.administrator.test.rest;

import com.google.gson.JsonObject;

import co.bitpartner.data.model.DropOutCommand;
import co.bitpartner.data.model.FundJoinCommand;
import co.bitpartner.data.model.command.AddInvestCommand;
import co.bitpartner.data.model.command.AlarmSettingCommand;
import co.bitpartner.data.model.command.AuthCheckCommand;
import co.bitpartner.data.model.command.AuthNumberCommand;
import co.bitpartner.data.model.command.FcmSendCommand;
import co.bitpartner.data.model.command.LoginCommand;
import co.bitpartner.data.model.command.PurchaseCoinCommand;
import co.bitpartner.data.model.command.RefreshTokenCommand;
import co.bitpartner.data.model.command.WalletBtcWithdrawCommand;
import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018-02-02.
 */

public interface BitPartnerApiService {

    @POST("/apis/register/auth_number_request")
    Single<JsonObject> requestSmsAuthNumber(@Body AuthNumberCommand authNumberCommand);

    @POST("/apis/register/auth_number_check")
    Single<JsonObject> checkSmsAuthNumber(@Header("USER-PHONE-BRAND") String appBrand, @Header("USER-PHONE-MODEL") String appModel, @Header("USER-PHONE-OS") String phoneOS, @Body AuthCheckCommand authCheckCommand);

    @GET("/apis/register/agreement")
    Single<JsonObject> requestRegisterAgreement();

    @POST("/apis/register/init")
    Single<JsonObject> initializeBitpartner(@Body JsonObject jsonObject);

    @POST("/apis/oauth/login")
    Single<JsonObject> tryLogin(@Header("USER-PHONE-BRAND") String appBrand, @Header("USER-PHONE-MODEL") String appModel, @Header("APP-VERSION") String appVersion, @Header("USER-PHONE-OS") String phoneOS, @Body LoginCommand loginCommand);

    @POST("/apis/users/asset")
    Single<JsonObject> getUserAsset(@Body JsonObject model);

    @POST("/apis/users/account")
    Single<JsonObject> getUserAccountInfo();

    @POST("/apis/coin/all")
    Call<JsonObject> requestCoinPrice(@Body JsonObject model);

    @POST("/apis/coin/buy_user")
    Single<JsonObject> purchaseCoinUser(@Body PurchaseCoinCommand purchaseCoinCommand);

    @POST("/apis/wallet/charge")
    Call<JsonObject> chargeWallet(@Body JsonObject model);

    @POST("/apis/fund/list_recommend")
    Single<JsonObject> getFundsInOffer();   //안씀

    @POST("/fcm/token/store")
    Single<JsonObject> sendFcmToken(@Body FcmSendCommand fcmSendCommand);

    @POST("/apis/danal/sms_auth")
    Call<ResponseBody> getDanalSMSAuth(@Header("hashkey") String hashKey, @Header("uid") String uid, @Header("endpoint") String endPoint);

    @POST("/apis/oauth/refresh")
    Single<JsonObject> refreshAccessToken(@Body RefreshTokenCommand tokenCommand);

    @POST("/apis/fund/join")
    Single<JsonObject> joinFund(@Body FundJoinCommand objectCommand);

    @POST("/apis/setting/auth_email")
    Single<JsonObject> getAuthEmail(@Body JsonObject jsonObject);

    @POST("/apis/setting/create_2nd_pwd")
    Single<JsonObject> getSecondPw(@Body JsonObject jsonObject);        // 사용안함

    @POST("/apis/setting/usage_detail")
    Single<JsonObject> getUsageDetail(@Body JsonObject jsonObject);     // 사용안함

    @POST("/apis/wallet/inquire_address")
    Single<JsonObject> inquireWalletAddr();

    @POST("/apis/wallet/issue_btc_wallet")
    Single<JsonObject> issueWalletAddress();

    @POST("/apis/setting/dropout")
    Single<JsonObject> dropOutMember(@Body DropOutCommand dropOutCommand);      // 사용안함

    @POST("/apis/wallet/withdraw")
    Call<JsonObject> issueWalletWithdraw();

    @POST("/apis/users/coin")
    Single<JsonObject> getUserCoin();

    @POST("/apis/wallet/withdraw_btc")
    Single<JsonObject> walletBtcWithdraw(@Body WalletBtcWithdrawCommand walletBtcWithdrawCommand);

    @GET("/apis/setting/security_agreement")
    Call<ResponseBody> securityAgreement();         // 사용안함

    @POST("/apis/setting/alarm_setting")
    Completable alarmSetting(@Body AlarmSettingCommand alarmSettingCommand);

    @POST("/apis/wallet/withdraw_info")
    Single<JsonObject> getBTCWithdrawInfo(@Body JsonObject currency);

    @POST("/apis/setting/min_ver")
    Single<JsonObject> appVersion(@Body JsonObject jsonObject);             // 사용안함

    @POST("/apis/fund/info")
    Single<JsonObject> getFundDetailInfo(@Body JsonObject fundId);  // 안씀

    @POST("/apis/fund/join_info")
    Single<JsonObject> getPrivateAsset(@Body JsonObject jsonObject);

    @POST("/apis/auth/2nd_pwd")
    Single<JsonObject> checkSecondPwd(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/auth/send_email_code")
    Single<JsonObject> sendEmailWithCode(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/auth/confirm_email_code")
    Single<JsonObject> confirmEmailCode(@Header("Authorization") String authToken, @Body JsonObject jsonObject);

    @POST("/apis/fund/cancel")
    Single<JsonObject> fundCancel(@Body JsonObject jsonObject);

    @POST("/apis/fund/cancel_info")
    Single<JsonObject> getCancelInfo(@Body JsonObject jsonObject);

    @GET("apis/dev_ip_check")
    Single<JsonObject> devIpCheck();

    @POST("/apis/setting/reset_2nd_pwd")
    Single<JsonObject> reset2ndPwd(@Header("Authorization") String authToken, @Body JsonObject jsonObject);     // 사용안함

    @POST("/apis/fund/add_invest")
    Single<JsonObject> addInvest(@Body AddInvestCommand addInvestCommand);
}
