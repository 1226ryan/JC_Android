package com.example.administrator.test_recyclerview.another2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test_recyclerview.AppUtil;
import com.example.administrator.test_recyclerview.Permission;
import com.example.administrator.test_recyclerview.PermissionRequester;
import com.example.administrator.test_recyclerview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardViewActivity extends AppCompatActivity {

    @BindView(R.id.btnShow) Button btnSelection;
    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.textView) TextView tv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        ButterKnife.bind(this);

        init();
        permission();
    }
    private void init() {
        studentList = new ArrayList<Student>();

        for (int i = 1; i <= 15; i++) {
            Student st = new Student("Student " + i, "androidstudent" + i + "@gmail.com", false);
            studentList.add(st);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(studentList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                List<Student> stList = ((CardViewDataAdapter) mAdapter).getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    Student singleStudent = stList.get(i);
                    if (singleStudent.isSelected()) {
                        data = data + "\n" + singleStudent.getName();
                    }
                }
                Toast.makeText(CardViewActivity.this, "Selected Students: \n" + data, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void permission() {
        int result = new PermissionRequester.Builder(CardViewActivity.this)
                .setTitle("권한 요청")
                .setMessage("권한을 요청합니다.")
                .setPositiveButtonName("네")
                .setNegativeButtonName("아니요.")
                .create()
                .request(Manifest.permission.READ_CONTACTS, 1000, new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {
                        Log.d("RESULT______________", "취소함.");
                    }
                });

        Log.d("RESULT______________", "result : "+result);
        Log.d("RESULT______________", "Permission.ALREADY_GRANTED : "+Permission.ALREADY_GRANTED);
        if (result == PermissionRequester.ALREADY_GRANTED) {
            Log.d("RESULT______________", "권한이 이미 존재함.");
            if (ActivityCompat.checkSelfPermission(CardViewActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(CardViewActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "READ_CONTACTS / WRITE_CONTACTS", Toast.LENGTH_SHORT).show();
            }
        } else if (result == PermissionRequester.NOT_SUPPORT_VERSION) {
            Log.d("RESULT______________", "마쉬멜로우 이상 버젼 아님.");
        } else if (result == PermissionRequester.REQUEST_PERMISSION) {
            Log.d("RESULT______________", "요청함. 응답을 기다림.");
        }
    }

    /**
     * 신규로 권한을 요청해 그 응답이 돌아왔을 경우 실행됨.
     * @param requestCode : 권한 요청시 전송했던 코드.
     * @param permissions : 요청한 권한
     * @param grantResults : 해당 권한에 대한 결과
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            for(int i=0; i<permissions.length; i++) {
                System.out.println("RESULT______________ permission : "+permissions[i]);
            }
            for(int i=0; i<grantResults.length; i++) {
                System.out.println("RESULT______________ grantResults : "+grantResults[i]);
            }
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { /* * 권한 획득 완료 * 해야 할 일을 수행한다. */
                Log.d("RESULT______________", "권한 획득 완료");
                Log.d("RESULT______________", "권한 획득 완료 : " +ActivityCompat.checkSelfPermission(CardViewActivity.this, Manifest.permission.READ_CONTACTS));
                Log.d("RESULT______________", "권한 획득 완료 : " +ActivityCompat.checkSelfPermission(CardViewActivity.this, Manifest.permission.WRITE_CONTACTS));
                Log.d("RESULT______________", "권한 획득 완료 PackageManager.PERMISSION_GRANTED : " +PackageManager.PERMISSION_GRANTED);
                if (ActivityCompat.checkSelfPermission(CardViewActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(CardViewActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-1111"));
//                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "READ_CONTACTS / WRITE_CONTACTS 요청완료", Toast.LENGTH_SHORT).show();
                }
            } else { /* * 권한 획득 실패 * 대안을 찾거나 기능의 수행을 중지한다. */
                Log.d("RESULT______________", "권한 획득 실패");
            }
        }
    }






    @OnClick({R.id.action_settings, R.id.item1})
    public void onClick(View v) {
        if(v.getId()==R.id.action_settings){
            Toast.makeText(this, "통화내역", Toast.LENGTH_SHORT).show();
            getHistory();
        } else if(v.getId()==R.id.item1) {
            Toast.makeText(this, "주소록", Toast.LENGTH_SHORT).show();
            contacts();
        }
    }


    /**
     * 주소록 정보 가져오기.
     */
    public void contacts(){
        Cursor cursor = managedQuery(ContactsContract.Contacts.CONTENT_URI,
                new String[] {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.PHOTO_ID
                },
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
        );

        String str = "";
        while (cursor.moveToNext()){
            try {
                String v_id = cursor.getString(0);
                String v_display_name = cursor.getString(1);
                String v_phone = contactsPhone(v_id);
                String v_email = contactsEmail(v_id);

                System.out.println("id = " + v_id);
                System.out.println("display_name = " + v_display_name);
                System.out.println("phone = " + v_phone);
                System.out.println("email = " + v_email);
                str += v_display_name + "("+v_phone+")\n";
            }catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        tv.setText(str);
        cursor.close();
    }

    /**
     * 주소록 상세정보(전화번호) 가져오기.
     */
    public String contactsPhone(String p_id){
        String reuslt = null;

        if(p_id==null || p_id.trim().equals("")){
            return reuslt;
        }

        Cursor cursor = managedQuery(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                },
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + p_id,
                null,
                null
        );
        while (cursor.moveToNext()){
            try {
                reuslt = cursor.getString(0);
            }catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        cursor.close();

        return reuslt;
    }

    /**
     * 주소록 상세정보(이메일주소) 가져오기.
     */
    public String contactsEmail(String p_id){
        String reuslt = null;

        if(p_id==null || p_id.trim().equals("")){
            return reuslt;
        }

        Cursor cursor = managedQuery(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                new String[] {
                        ContactsContract.CommonDataKinds.Email.DATA
                },
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + p_id,
                null,
                null
        );
        while (cursor.moveToNext()){
            try {
                reuslt = cursor.getString(0);
            }catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        cursor.close();

        return reuslt;
    }


    /*
     * 안드로이드 수신/발신 정보 가져오기 CallLog 이용
     * androidManifest.xml에 추가
     * <uses-permission android:name="android.permission.READ_CONTACTS" />
     */
    public void getHistory() {
        //String[] projection = { CallLog.Calls.CONTENT_TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION, CallLog.Calls.DATE };

        Cursor cur = managedQuery(
                CallLog.Calls.CONTENT_URI,
                null,
                CallLog.Calls.TYPE + "= ?",
                new String[]{ String.valueOf(CallLog.Calls.INCOMING_TYPE) },  //new String[]{ String.valueOf(CallLog.Calls.OUTGOING_TYPE) },
                CallLog.Calls.DEFAULT_SORT_ORDER
        );

        System.out.println("db count=" + String.valueOf(cur.getCount()));
        System.out.println("db count=" + CallLog.Calls.CONTENT_ITEM_TYPE);
        System.out.println("db count=" + CallLog.Calls.CONTENT_TYPE);

        if(cur.moveToFirst() && cur.getCount() > 0) {
            while(cur.isAfterLast() == false) {
                StringBuffer sb = new StringBuffer();

                sb.append("call type=").append(cur.getString(cur.getColumnIndex(CallLog.Calls.TYPE)));
                sb.append(", cashed name=").append(cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                sb.append(", content number=").append(cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER)));
                sb.append(", duration=").append(cur.getString(cur.getColumnIndex(CallLog.Calls.DURATION)));
                sb.append(", new=").append(cur.getString(cur.getColumnIndex(CallLog.Calls.NEW)));
                sb.append(", date=").append(AppUtil.millis2Time(cur.getLong(cur.getColumnIndex(CallLog.Calls.DATE)))).append("]");

                System.out.println("call history[" + sb.toString());

                cur.moveToNext();
            }
        }

        cur.close();
    }
}