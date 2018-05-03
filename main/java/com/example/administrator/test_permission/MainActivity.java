package com.example.administrator.test_permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPermission();
    }

    private void showPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionReadContactsResult = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (permissionReadContactsResult == PackageManager.PERMISSION_DENIED) {
                /* 사용자가 한번이라도 거부한 적이 있는지 조사해 있다면 true 반환 */
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
                        && shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)
                        && shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                    dialogUtil = DialogUtil.getDialog(InviteActivity.this, "제목", "내용", yes, intentFundA);
                    dialogUtil.show();
                } else {    /* 최초로 권한을 요청할 때 */
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, 1000);
                }
            } else {      /* 권한이 있을 때 */
                getContacts();
            }
        } else {
            getContacts();
        }
    }

    private List<InviteItem> inviteItems = new ArrayList<>();
    private List<String> arrayListContactsName = new ArrayList<>();
    private List<String> arrayListContactsPhone = new ArrayList<>();

    private void getContacts() {
        Cursor cursor = MainActivity.this.managedQuery(ContactsContract.Contacts.CONTENT_URI, new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID
        }, null, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        if(!cursor.moveToNext()) {
            intentFundActivity();
            return;
        }

        while (cursor.moveToNext()) {
            try {
                String v_id = cursor.getString(0);
                String v_display_name = cursor.getString(1);
                String v_phone = contactsPhone(v_id);

                arrayListContactsName.add(v_display_name);
                arrayListContactsPhone.add(v_phone);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        cursor.close();


        for (int i = 0; i < arrayListContactsName.size(); i++) {
            InviteItem inviteItem = new InviteItem(arrayListContactsName.get(i), arrayListContactsPhone.get(i), false);
            inviteItems.add(inviteItem);
        }

        setAdapterList(inviteItems);
    }


    public void intentFundActivity() {
        dialogUtil = DialogUtil.getDialog(InviteActivity.this, "제목", "내용", intentFundA);
        dialogUtil.show();
    }


    // 기계에서 핸드폰 번호 가져오기
    private String contactsPhone(String p_id) {
        String reuslt = null;

        if (p_id == null || p_id.trim().equals("")) {
            return reuslt;
        }

        Cursor cursor = MainActivity.this.managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + p_id,
                null, null);
        while (cursor.moveToNext()) {
            try {
                reuslt = cursor.getString(0);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        cursor.close();

        return reuslt;
    }


    void setAdapterList(List<InviteItem> list) {
        inviteRecyclerAdapter = new InviteRecyclerAdapter(list);
        recyclerView.setAdapter(inviteRecyclerAdapter);
    }



    private View.OnClickListener yes = new View.OnClickListener() {
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, 1000);
            }

            dialogUtil.dismiss();
        }
    };
    private View.OnClickListener intentFundA = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(InviteActivity.this, FundActivity.class);
            startActivity(intent);

            dialogUtil.dismiss();
        }
    };
}
