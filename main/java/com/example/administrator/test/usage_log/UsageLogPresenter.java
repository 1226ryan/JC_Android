package com.example.administrator.test.usage_log;

import android.annotation.SuppressLint;

import com.example.administrator.test.DecimalFormatUtil;
import com.example.administrator.test.GsonUtil;
import com.example.administrator.test.settings.SettingsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class UsageLogPresenter implements UsageLogContract.Presenter {

    private SettingsRepository settingsRepository;
    private UsageLogContract.View view;

    private String money = "", ipAddress = "", logDate = "", logDateUtc = "", currency = "", count = "", fundName = "", title = "";    // 이용내역 response 받아오기
    private int listCount, page, OFFSET;

    private UsageLogListItem usageLogListItem;
    private List<UsageLogListItem> itemList = new ArrayList<>();

    public UsageLogPresenter(SettingsRepository settingsRepository, UsageLogContract.View view, int page, int OFFSET) {
        this.settingsRepository = settingsRepository;
        this.view = view;
        this.page = page;
        this.OFFSET = OFFSET;
    }

    @Override
    public void start() {
        view.pagingView(listCount, OFFSET, itemList);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getUsageDetailItem(int pageNumber) {
        JsonObject jsonObjectPage = new JsonObject();
        jsonObjectPage.addProperty("page_num", pageNumber);

        settingsRepository
                .usageDetail(pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    if (jsonObject.get("result").getAsString().equals(Defines.CODE_1000)) {
                        Type nameType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                        List<Map<String, Object>> rows = (List<Map<String, Object>>) GsonUtil.getInstance().fromJson(jsonObject.get("rows").toString(), nameType);
                        listCount = rows.size();

                        getItem(rows);
                    } else {

                    }
                }, throwable -> throwable.printStackTrace());
    }

    private void getItem(List<Map<String, Object>> rows) {
        for (int i = rows.size() - 1; i >= 0; i--) {
            Set key = rows.get(i).keySet();

            for (Iterator iterator = key.iterator(); iterator.hasNext(); ) {
                String keyName = (String) iterator.next();
                String valueName = String.valueOf(rows.get(i).get(keyName));

                if (keyName.equals("ip_address")) {
                    ipAddress = valueName;      // ip 주소(000.000.000.000)
                } else if (keyName.equals("log_date")) {
                    logDate = valueName;        // 해당 시간(yyyy-mm-dd hh:mm:ss)
                } else if (keyName.equals("log_date_utc")) {
                    logDateUtc = valueName;     // 해당 시간(yyyy-mm-dd hh:mm:ss) UTC
                } else if (keyName.equals("money")) {
                    money = valueName;          // 화폐 금액
                } else if (keyName.equals("currency")) {
                    currency = valueName;       // 암호화폐 통화 종류
                } else if (keyName.equals("count")) {
                    count = valueName;          // coin 개수
                } else if (keyName.equals("title")) {
                    title = valueName;          // 로그의 이름 key 값 (계좌 입/출금, 서비스 접속, 펀드가입/탈퇴, 암호화폐 구입 등)
                } else if (keyName.equals("fund_name")) {
                    if( !rows.get(i).get(keyName).equals("") ) {
                        ObjectMapper oMapper = new ObjectMapper();

                        // object -> Map
                        Map<String, Object> map = oMapper.convertValue(rows.get(i).get(keyName), Map.class);
                        if(SharedPreferenceUtil.getInstance().getSystemCountryLanguage().equals(Defines.KO)) {
                            fundName = String.valueOf(map.get(Defines.KO));
                        } else {
                            fundName = String.valueOf(map.get(Defines.EN));
                        }
                    }
                } else {
                    System.out.println("서버에서 받아오는 key 가 존재하지 않습니다.");
                }
            }

            EnglishToKorea(title);
        }
    }

    private void EnglishToKorea(String title) {      // 영어로 들어온 title을 한국어로 바꿔줌
        String strText = null;
        String strTimeIp = null;

        if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_login))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_login_korea);
            strText = title;
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_deposit))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_deposit_korea);
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_fund_join))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_fund_join_korea);
            strText = title + "\n" + fundName + "\n" + DecimalFormatUtil.getFormatNumber(Double.valueOf(count));
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_fund_cancel))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_fund_cancel_korea);
            strText = title + "\n" + fundName;
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_coin_purchase))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_coin_purchase_korea);
            strText = title + "\n" + currency + "\n" + count;
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_coin_deposit))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_coin_deposit_korea);
            strText = currency + " " + title + "\n" + count;
            strTimeIp = convertUtcToLocal(logDateUtc);
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_coin_withdraw))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_coin_withdraw_korea);
            strText = currency + " " + title + "\n" + count;
            strTimeIp = convertUtcToLocal(logDateUtc);
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_withdraw_approve))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_withdraw_approve_korea);          // 대기
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        } else if (title.equals(BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_withdraw_wait))) {
            title = BitPartnerApplication.getInstance().getString(R.string.UsageLogActivity_withdraw_wait_korea);          // 대기
            strTimeIp = convertUtcToLocal(logDateUtc) + "\nIP : " + ipAddress;
        }

        usageLogListItem = new UsageLogListItem(strText, strTimeIp);
        itemList.add(page * OFFSET, usageLogListItem);

        view.showRecycler(itemList);
    }

    private String convertUtcToLocal(String utcTime) {
        String localTime = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateUtcTime = dateFormat.parse(utcTime);   // v표준시를 Date 포맷으로 변경
            long longUtcTime = dateUtcTime.getTime();       // 표준시 Date 포맷을 long 타입의 시간으로 변경

            TimeZone zone = TimeZone.getDefault();          // TimeZone을 통해 시간차이 계산(썸머타임 고려 getRawOffset 대신 getOffset 함수 활용)
            int offset = zone.getOffset(longUtcTime);
            long longLocalTime = longUtcTime + offset;

            Date dateLocalTime = new Date();                // long 타입의 로컬 시간을 Date 포맷으로 변경
            dateLocalTime.setTime(longLocalTime);

            localTime = dateFormat.format(dateLocalTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return localTime;
    }
}
