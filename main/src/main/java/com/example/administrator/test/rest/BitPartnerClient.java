package com.example.administrator.test.rest;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import co.bitpartner.app.common.Defines;
import co.bitpartner.data.model.DropOutCommand;
import co.bitpartner.data.model.FundJoinCommand;
import co.bitpartner.data.model.TokenRow;
import co.bitpartner.data.model.command.AddInvestCommand;
import co.bitpartner.data.model.command.AlarmSettingCommand;
import co.bitpartner.data.model.command.AuthCheckCommand;
import co.bitpartner.data.model.command.AuthNumberCommand;
import co.bitpartner.data.model.command.FcmSendCommand;
import co.bitpartner.data.model.command.LoginCommand;
import co.bitpartner.data.model.command.PurchaseCoinCommand;
import co.bitpartner.data.model.command.RefreshTokenCommand;
import co.bitpartner.data.model.command.WalletBtcWithdrawCommand;
import co.bitpartner.util.GsonUtil;
import co.bitpartner.util.SharedPreferenceUtil;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-02-02.
 */

public class BitPartnerClient {

    private static final String TAG = BitPartnerClient.class.getSimpleName();

    private static String BASE_URL = Defines.BASE_RELEASE_URL;

    private volatile static BitPartnerClient instance;
    private BitPartErrorException errorHandlingInstance = BitPartErrorException.getInstance();

    public static BitPartnerClient getInstance() {
        if (instance == null) {
            synchronized (BitPartnerClient.class) {
                if (instance == null)
                    instance = new BitPartnerClient();
            }
        }

        return instance;
    }

    private OkHttpClient okHttpClient;
    private BitPartnerApiService service;
    private Retrofit retrofit;
    private BitPartnerClient() {
        okHttpClient = makeOkHttpClient(makeLoggingInterceptor(), makeHttpConfigInterceptor(), networkErrorInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(BitPartnerApiService.class);
    }

    private OkHttpClient makeOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Interceptor httpConfigInterceptor, Interceptor networkErrorInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(httpConfigInterceptor)
                .addInterceptor(networkErrorInterceptor)
                .build();
    }

    public  <S> S getBitpartService(Class<S> service) {
        return retrofit.create(service);
    }

    /**
     * 회사에서 접속시 개발용, 실제용 url 선택
     * base url에 따른 retrofit builder 재설정
     * @param baseUrl - 개발용 or 실제용
     */
    public void setRetrofitBuilder(String baseUrl, int pos) {
        if (pos != 0) { //dev
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

            service = retrofit.create(BitPartnerApiService.class);
        }
    }

    /**
     * 재시도 횟수
     */
    private static final int RETRY_COUNT = 3;

    /**
     * 재시도 딜레이 타임
     */
    private static final int RETRY_DELAY_TIME = 1000;
    private Interceptor networkErrorInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);

                int tryCount = 0;
                while ((response == null || !response.isSuccessful()) && tryCount < RETRY_COUNT) {
                    Log.e(TAG, "intercept#request fail tryCount : " + tryCount);

                    try {
                        Thread.sleep(RETRY_DELAY_TIME);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    tryCount++;

                    // Request 재 시도
                    response = chain.proceed(request);
                }

                if (response == null) {
                    throw new IOException("REQUEST FAIL");
                }

                return response;
            }
        };
    }

    private HttpLoggingInterceptor makeLoggingInterceptor() {       // Retrofit에서 통신 과정의 로그를 확인하기 위함. 로그의 level을 지정
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }
    // 토큰 얻어와서 요청값에 넣어줌
    private Interceptor makeHttpConfigInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String accessToken = SharedPreferenceUtil.getInstance().getAccessToken();

                Request original = chain.request();
                Request request;

                if (original.header("Authorization") == null) {
                    Request.Builder reqBuilder = original.newBuilder()
                            .addHeader("Authorization", "Bearer " + accessToken);
                    request = reqBuilder.build();

                } else {
                    Request.Builder reqBuilder = original.newBuilder();
                    request = reqBuilder.build();

                }

                return chain.proceed(request);
            }
        };
    }

    public void tryLogin(String appBrand, String appModel, String appVersion, String phoneOS, LoginCommand loginCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Callback must not be null");

        Single<JsonObject> call = service.tryLogin(appBrand, appModel, appVersion, phoneOS, loginCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public Single<JsonObject> tryLogin(String appBrand, String appModel, String appVersion, String phoneOS, LoginCommand loginCommand) {
        Single<JsonObject> call = service.tryLogin(appBrand, appModel, appVersion, phoneOS, loginCommand);
        return call.
                subscribeOn(Schedulers.io()).
                filter(it -> {
                    int isSuccess = errorHandlingInstance.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS)
                        return true;
                    else if (isSuccess == BitPartErrorException.TOKEN_EXPIRED)
                        refreshAccessToken(call);

                    return false;
                })
                .toSingle();
    }

    private void refreshAccessToken(Single<JsonObject> call) {
        SharedPreferenceUtil spUtil = SharedPreferenceUtil.getInstance();
        RefreshTokenCommand tokenCommand = new RefreshTokenCommand(spUtil.getHashKey(), spUtil.getUUID(), spUtil.getRefreshToken());

        service.refreshAccessToken(tokenCommand)
                .subscribe(it -> {
                    int isSuccess = errorHandlingInstance.checkErrorCode(it);
                    if (isSuccess == BitPartErrorException.SUCCESS) {
                        TokenRow row = (TokenRow) GsonUtil.getInstance().fromJson(it.get("row").toString(), TokenRow.class);
                        SharedPreferenceUtil.getInstance().setAccessToken(row.getAccessToken());


                        call.retry();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    public void requestAgreement(final ResultCallback callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.requestRegisterAgreement();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // RegisterRequestActivity 에서 sms 요청 번호 사용
    public void requestSmsAuthNumber(AuthNumberCommand authNumberCommand, final ResultCallback callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.requestSmsAuthNumber(authNumberCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // RegisterSmsAuthActivity 에서 sms 요청 번호 확인 시 사용
    public void checkSmsAuthNumber(String appBrand, String appModel, String phoneOS, AuthCheckCommand authCheckCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.checkSmsAuthNumber(appBrand, appModel, phoneOS, authCheckCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // AssetActivity 에서 사용
    public void getAsset(JsonObject jsonObject, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getUserAsset(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getFundsInOffer(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getFundsInOffer();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // RegisterSmsAuthActivity 에서 사용자 레벨 등 받아오기
    public void initializeBitpartner(JsonObject jsonObject, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.initializeBitpartner(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // RegisterSmsAuthActivity 에서 fcm token 확인
    public void sendFcmToken(FcmSendCommand fcmSendCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.sendFcmToken(fcmSendCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // 설정 -> 보안 -> 핸드폰 인증
    public void getDanalSMSAuth(String hashKey, String uid, String endPoint, final ResultCallback<ResponseBody> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Call<ResponseBody> call = service.getDanalSMSAuth(hashKey, uid, endPoint);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    // 설정 -> 보안 -> 이메일인증
    public void getEamilAuth(JsonObject jsonObject, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getAuthEmail(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void refreshAccessToken(RefreshTokenCommand tokenCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.refreshAccessToken(tokenCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // 설정 -> 보안 -> 6자리 비밀번호
    public void getCreateSecondPw(JsonObject jsonObject, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getSecondPw(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void joinFund(FundJoinCommand fundJoinCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.joinFund(fundJoinCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // UsageLogActivity 에서 사용자 레벨 등 받아오기
    public void getUsageDetail(JsonObject jsonObject, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getUsageDetail(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    // BTC Wallet 주소 조회
    public void inquireWalletAddr(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.inquireWalletAddr();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    // BTC Wallet 발급
    public void IssueWalletAddress(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.issueWalletAddress();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // setting -> logout -> 회원탈퇴
    public void dropOut(DropOutCommand dropOutCommand, final ResultCallback callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.dropOutMember(dropOutCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // 코인 API -> 특정 코인 구매
    public void buyUser(PurchaseCoinCommand purchaseCoinCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.purchaseCoinUser(purchaseCoinCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // 유저 API -> 유저의 계좌 정보
    public void getUserAccountInfo(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getUserAccountInfo();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void getUserCoin(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getUserCoin();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // wallet -> bitcoinio -> withdraw
    public void getWithdrawBtc(WalletBtcWithdrawCommand withdrawCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.walletBtcWithdraw(withdrawCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    // 설정 -> 보안등급 -> 한도
    public void getSecurityAgreement(final ResultCallback<ResponseBody> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Call<ResponseBody> call = service.securityAgreement();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    // 설정 -> 보안등급 -> 알람설정
    public void getAlarmSetting(AlarmSettingCommand alarmSettingCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Completable call = service.alarmSetting(alarmSettingCommand);
//        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
//            @Override
//            public void onSuccess(@Nullable JsonObject jsonObject) {
//                callback.onSuccess(jsonObject);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
    }

    // setting -> app version
    public void getAppVersion(JsonObject jsonObject, final ResultCallback callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.appVersion(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getBTCWithdrawInfo(JsonObject currency, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getBTCWithdrawInfo(currency);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getFundDetailInfo(JsonObject fundId, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getFundDetailInfo(fundId);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getPrivateAsset(JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getPrivateAsset(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public void checkSecondPwd(String authToken, JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.checkSecondPwd(authToken, jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //join fund, confirmEmailCode, checkSecondPwd, reset2ndPwd
//    public BitPartnerApiService setTestRetrofitBuilder() {
//        OkHttpClient okHttpClient = makeOkHttpClient(makeLoggingInterceptor(), makeHttpConfigInterceptor());
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://dev.bfdevs.com:8887")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        BitPartnerApiService service = retrofit.create(BitPartnerApiService.class);
//        return service;
//    }

    public void sendEmailWithCode(String authToken, JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.sendEmailWithCode(authToken, jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void confirmEmailCode(String authToken, JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.confirmEmailCode(authToken, jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void fundCancel(JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.fundCancel(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getCancelInfo(JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.getCancelInfo(jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
    // 회사 ip check
    public void getDevIpCheck(final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.devIpCheck();
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }




    // 비밀번호 재설정
    public void reset2ndPwd(String authToken, JsonObject jsonObject, ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.reset2ndPwd(authToken, jsonObject);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }




    // 펀드 추가 투자
    public void fundAddInvest(AddInvestCommand addInvestCommand, final ResultCallback<JsonObject> callback) {
        if (callback == null)
            throw new IllegalArgumentException("Call must not be null");

        Single<JsonObject> call = service.addInvest(addInvestCommand);
        BitPartErrorException.getInstance().checkErrorCode(call, new ResultCallback<JsonObject>() {
            @Override
            public void onSuccess(@Nullable JsonObject jsonObject) {
                callback.onSuccess(jsonObject);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}














