package com.example.cnwlc.project_memo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginRegisterActivity extends Activity {

    EditText editid;
    EditText editpw;
    EditText editpwconfirm;
    EditText editcellphone;
    EditText editcertification;

    SharedPreferences pref = null;
    SharedPreferences.Editor edit;

    String[] SBid;
    String[] SBpw;
    int members;
    int first = 0, temp, count;
    boolean possibleId = false;
    boolean possibleNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        pref = getSharedPreferences("login", MODE_PRIVATE);
        edit = pref.edit();
        members = pref.getInt("saveIntReg", 0);

        SBid = new String[members];
        SBpw = new String[members];
        for (int i = 0; i < members; i++) {
            SBid[i] = pref.getString("id_" + i, "idid");
            SBpw[i] = pref.getString("pw_" + i, "pwpw");
        }
        for (int i = 0; i < members; i++) {
            System.out.println("Register_SBid[" + i + "] : " + SBid[i]);
            System.out.println("Register_SBpw[" + i + "] : " + SBpw[i]);
        }


        editid = (EditText) findViewById(R.id.idEdit);
        editpw = (EditText) findViewById(R.id.pwEdit);
        editpwconfirm = (EditText) findViewById(R.id.pwEdit2);
        editcellphone = (EditText) findViewById(R.id.cellphoneEdit);
        editcertification = (EditText) findViewById(R.id.certificationEdit);

        int colorOnCreate = Color.parseColor("#000000");
        editid.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editpw.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editpwconfirm.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editcellphone.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editcertification.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);

        editid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String id = editid.getText().toString();

                if ( id.length() >= 1 ) {
                    int color = Color.parseColor("#00ff00");
                    editid.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                } else {
                    int color = Color.parseColor("#000000");
                    editid.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        editpwconfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = editpw.getText().toString();
                String confirm = editpwconfirm.getText().toString();

                if (password.equals(confirm) && (password.length() >= 4)) {
                    int color = Color.parseColor("#00ff00");
                    editpw.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    editpwconfirm.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                } else {
                    int color = Color.parseColor("#ff0000");
                    editpw.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    editpwconfirm.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editcellphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cellphone = editcellphone.getText().toString();

                if ( cellphone.length() > 10 ) {
                    int color = Color.parseColor("#00ff00");
                    editcellphone.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                } else {
                    int color = Color.parseColor("#ff0000");
                    editcellphone.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        editcertification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String certification = editcertification.getText().toString();

                if ( !certification.equals("") ) {
                    int color = Color.parseColor("#00ff00");
                    editcertification.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                } else {
                    int color = Color.parseColor("#000000");
                    editcertification.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    String strNumber;
    String strCerNum;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IDcheckBtn:
                for (int i = 0; i < members; i++) {
                    if (SBid[i].equals(editid.getText().toString())) {
                        Toast.makeText(LoginRegisterActivity.this, "중복된 아이디입니다!", Toast.LENGTH_SHORT).show();
                        count++;
                    }
                }
                temp = first;
                first = count;
                System.out.println("count : " + count);
                System.out.println("temp : " + temp);
                System.out.println("first : " + first);
                if (temp == count) {
                    Toast.makeText(LoginRegisterActivity.this, "사용 가능한 아이디입니다!", Toast.LENGTH_SHORT).show();
                    possibleId = true;
                }
                break;
            case R.id.okBtn:
                // 이메일 입력 확인
                if (editid.getText().toString().length() == 0) {
                    Toast.makeText(LoginRegisterActivity.this, "id를 입력하세요!", Toast.LENGTH_SHORT).show();
                    editid.requestFocus();
                    return;
                }
                // 비밀번호 입력 확인
                if (editpw.getText().toString().length() < 4) {
                    Toast.makeText(LoginRegisterActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    editpw.requestFocus();
                    return;
                }
                // 비밀번호 확인 입력 확인
                if (editpwconfirm.getText().toString().length() < 4) {
                    Toast.makeText(LoginRegisterActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    editpwconfirm.requestFocus();
                    return;
                }
                // 비밀번호 일치 확인
                if (!editpw.getText().toString().equals(editpwconfirm.getText().toString())) {
                    Toast.makeText(LoginRegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    editpw.setText("");
                    editpwconfirm.setText("");
                    editpw.requestFocus();
                    return;
                }
                if (editcellphone.getText().toString().length() < 10) {
                    Toast.makeText(LoginRegisterActivity.this, "전화번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    editcellphone.requestFocus();
                    return;
                }
                if (editcertification.getText().toString().length() == 0) {
                    Toast.makeText(LoginRegisterActivity.this, "인증번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    editcertification.requestFocus();
                    return;
                }
                // 중복 체크 안할 경우
                if (possibleId != true) {
                    Toast.makeText(LoginRegisterActivity.this, "중복 체크를 진행하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 중복 체크 했을 경우
                if (possibleId == true && possibleNumber == true) {
                    // 회원 아이디/비번 넘기기
                    Intent intentOk = new Intent(this, LoginActivity.class);
                    intentOk.putExtra("id", editid.getText().toString());
                    intentOk.putExtra("pw", editpw.getText().toString());

                    setResult(RESULT_OK, intentOk);
                    finish();
                }
                break;
            case R.id.noBtn:
                finish();
                // finish()를 쓸 경우 onDestroy 를 출력하게 된다.
                break;
            case R.id.cellphoneBtn:
                strNumber = editcellphone.getText().toString();
                if (strNumber.equals("")) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int certificationNumber = (int) (Math.random() * 10000);
                    strCerNum = String.valueOf(certificationNumber);
                    Toast.makeText(getApplicationContext(), "인증 번호 : "+strCerNum, Toast.LENGTH_LONG).show();
//                    sendSMS(strNumber, strCerNum);
                }
                break;
            case R.id.certificationBtn:
                if (editcertification.getText().toString().equals(strCerNum)) {
                    Toast.makeText(getApplicationContext(), "인증에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    possibleNumber = true;
                } else {
                    Toast.makeText(getApplicationContext(), "인증에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    possibleNumber = false;
                }
                break;
        }
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
        /**
         * destinationAddress : 수신자 전화번호
         * scAddress : 서비스 센터 전화번호. 일반적으로 null사용
         * sentIntent : SMS 전송 성공, 실패 등의 이벤트를 알리기 위한 PendingIntent
         * deleveryIntent : 답장이 올경우 SMS수신 이벤트를 알리기 위한 PendingIntent
         */
    }

    @Override
    protected void onStop() {
        super.onStop();

        int colorOnCreate = Color.parseColor("#000000");
        editid.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editpw.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editpwconfirm.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editcellphone.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
        editcertification.getBackground().setColorFilter(colorOnCreate, PorterDuff.Mode.SRC_IN);
    }
}