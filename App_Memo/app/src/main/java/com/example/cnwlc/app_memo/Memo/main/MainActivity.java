package com.example.cnwlc.app_memo.Memo.main;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.cnwlc.app_memo.Common.BaseActivity;
import com.example.cnwlc.app_memo.R;
import com.example.cnwlc.app_memo.UserDB;


import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.mainA_recycler_view)
    RecyclerView recyclerView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void initView() {
        sendSMS("01083025038", "hihi");

//        Realm.init(this);
//        realm = Realm.getDefaultInstance();
//        RealmResults<UserDB> userList = getUserList();
//        Log.i(TAG, ">>>> userList.size : "+userList.size()); // :0
//
//        //유저 정보 데이터 DB 저장
////        insertUserData();
//        Log.i(TAG, ">>>> userList.size : "+userList.size()); // :1
//
//        //유저 정보 삭제
//        deleteuserData();
//        Log.i(TAG, ">>>>>   userList.size :  " + userList.size()); // :0
    }

    // 유저 정보 데이터 리스트 반환
    private RealmResults<UserDB> getUserList() {
        return realm.where(UserDB.class).findAll();
    }

    // 유저 정보 데이터 DB 저장
    private void insertUserData() {
        realm.beginTransaction();
        UserDB user = realm.createObject(UserDB.class, "Choo");
        user.setAge(29);
        realm.commitTransaction();
    }

    // 유저 정보 삭제
    private void deleteuserData(){
        realm.beginTransaction();

        RealmResults<UserDB> userList = realm.where(UserDB.class).findAll();
        userList.remove(0);
        realm.commitTransaction();
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        String strMessage = "인증번호 : " + message;

        // 각각 위에서부터 문자 전송, 문자 수신에 관련하여 sendTextMessage()에 넘겨줄 값들입니다
        PendingIntent senTPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "SMS 전송", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getApplicationContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getApplicationContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getApplicationContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getApplicationContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "SMS 전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "SMS 전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, strMessage, senTPI, deliveredPI);
    }
}
