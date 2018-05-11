package com.example.administrator.test_recyclerview.invitation;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.bitpartner.BitPartnerApplication;
import co.bitpartner.R;

public class InvitePresenter implements InviteContract.Presenter {

    private InviteContract.View view;
    private Activity context;

    public InvitePresenter(InviteContract.View view, Activity context) {
        this.view = view;
        this.context = context;
    }

    private List<InviteItem> inviteItems = new ArrayList<>();
    private List<String> arrayListContactsName = new ArrayList<>();
    private List<String> arrayListContactsPhone = new ArrayList<>();
    private String totalName="";

    @Override
    public void start() {
        view.showPermission();
    }

    @Override
    public void getContacts() {
        Cursor cursor = context.managedQuery(ContactsContract.Contacts.CONTENT_URI, new String[] {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.PHOTO_ID
                }, null, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        if(!cursor.moveToNext()) {
            view.intentFundActivity();
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

        view.setAdapterList(inviteItems);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        inviteRecyclerAdapter = new InviteRecyclerAdapter(inviteItems);
//        recyclerView.setAdapter(inviteRecyclerAdapter);
    }

    @Override
    public void getContactsNamePhone(InviteRecyclerAdapter inviteRecyclerAdapter) {
        List<String> contactsName = getContactsName(inviteRecyclerAdapter);
        for (int i = 0; i < contactsName.size(); i++) {
            totalName += contactsName.get(i)+"\n";
        }
        totalName += BitPartnerApplication.getInstance().getString(R.string.IA_invite_thx);

        List<String> contactsPhone = getContactsPhone(inviteRecyclerAdapter);
        for (int i = 0; i < contactsPhone.size(); i++) {
            sendSMS(contactsPhone.get(i), BitPartnerApplication.getInstance().getString(R.string.IA_invite));
        }
    }

    // sms 보내기(보내는 사람 : 핸드폰 주인, 받는 사람 : 설정, 보내는 메시지 : 설정)
    private void sendSMS(String smsNumber, String smsText) {
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, context.getString(R.string.InviteActivity_sms_send_complete), Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, context.getString(R.string.InviteActivity_sms_send_fail), Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, context.getString(R.string.InviteActivity_sms_send_no_service_area), Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, context.getString(R.string.InviteActivity_sms_send_complete2), Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, context.getString(R.string.InviteActivity_sms_send_fail2), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }

    // 기계에서 핸드폰 번호 가져오기
    private String contactsPhone(String p_id) {
        String reuslt = null;

        if (p_id == null || p_id.trim().equals("")) {
            return reuslt;
        }

        Cursor cursor = context.managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
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



    // total name 을 return 해주는 메소드
    public String getTotalName() {
        return totalName;
    }

    // 선택한 사용자의 명수를 리턴해주는 메소드
    public int checkCount(InviteRecyclerAdapter inviteRecyclerAdapter) {
        String data = "";
        int count;
        List<InviteItem> list = inviteRecyclerAdapter.getItemList();

        for (int i = 0; i < list.size(); i++) {
            InviteItem inviteItem = list.get(i);
            if (inviteItem.isChecked()) {
                data = data + "\n" + inviteItem.getName();
            }
        }
        String[] a = data.split("\n");
        count = a.length - 1;
        return count;
    }

    // 이름을 activity 로 리턴
    public List getContactsName(InviteRecyclerAdapter inviteRecyclerAdapter) {
        List<InviteItem> list = inviteRecyclerAdapter.getItemList();
        List<String> listName = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            InviteItem inviteItem = list.get(i);
            if (inviteItem.isChecked()) {
                listName.add(inviteItem.getName());
            }
        }
        return listName;
    }

    // 핸드폰 번호를 activity 로 리턴
    public List getContactsPhone(InviteRecyclerAdapter inviteRecyclerAdapter) {
        List<InviteItem> list = inviteRecyclerAdapter.getItemList();
        List<String> listName = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            InviteItem inviteItem = list.get(i);
            if (inviteItem.isChecked()) {
                listName.add(inviteItem.getPhone());
            }
        }
        return listName;
    }
}
