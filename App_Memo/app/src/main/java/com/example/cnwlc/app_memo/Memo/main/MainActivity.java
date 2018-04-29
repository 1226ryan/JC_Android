package com.example.cnwlc.app_memo.Memo.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.cnwlc.app_memo.Common.BaseActivity;
import com.example.cnwlc.app_memo.R;
import com.example.cnwlc.app_memo.UserDB;
import com.example.cnwlc.app_memo.Util.SendSmsUtil;


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
        SendSmsUtil.getInstance().sendSMS(MainActivity.this, "01083025038", "hihi");

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
    private void deleteuserData() {
        realm.beginTransaction();

        RealmResults<UserDB> userList = realm.where(UserDB.class).findAll();
        userList.remove(0);
        realm.commitTransaction();
    }
}
