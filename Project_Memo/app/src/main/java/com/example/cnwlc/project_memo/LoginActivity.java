package com.example.cnwlc.project_memo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    final static int REQUEST_CODE = 1;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    CheckBox AutoLoginCheckBox;

    EditText Eid;
    EditText Epw;

    String Sid;
    String Spw;
    String idpwcheck;
    String[] ididid;
    String[] pwpwpw;

    SharedPreferences pref = null;
    SharedPreferences.Editor edit;

    // 회원수
    int intRegister;
    int member;

    JSONObject jobIDPW = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView2) ;
        imageView1.setImageResource(R.drawable.memo);

        AutoLoginCheckBox = (CheckBox) findViewById(R.id.autologinCB);

        Eid = (EditText) findViewById(R.id.id);
        Epw = (EditText) findViewById(R.id.pw);

        pref = getSharedPreferences("login", MODE_PRIVATE);
        edit = pref.edit();
        member = pref.getInt("saveIntReg", 1);
        intRegister = member;
    }

    public void onClick(View view) throws JSONException {
        idpwcheck = jobIDPW.optString(Eid.getText().toString(), "아이디가 없습니다.");

        switch (view.getId()) {
            case R.id.loginBtn:
                if (Eid.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                } else if(Epw.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    if ( idpwcheck.equals("아이디가 없습니다.") ) {
                        Toast.makeText(getApplicationContext(), "아이디와 비밀번호가 등록되어 있는지 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                    if ( !idpwcheck.equals("아이디가 없습니다.") &&
                            Epw.getText().toString().equals(jobIDPW.getString(Eid.getText().toString())) ) {
                        mHandler = new Handler();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog = ProgressDialog.show(LoginActivity.this, "", "잠시만 기다려 주세요.", true);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                                mProgressDialog.dismiss();

                                                Intent intentLogin = new Intent(LoginActivity.this, MemoMainActivity.class);
                                                intentLogin.putExtra("New_id", Eid.getText().toString());
                                                startActivity(intentLogin);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 1000);
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "아이디와 비밀번호가 맞는지 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.registBtn:
                Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                // 동시에 사용 가능
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.imsiLoginBtn :mHandler = new Handler();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog = ProgressDialog.show(LoginActivity.this,"", "잠시만 기다려 주세요.",true);
                        mHandler.postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (mProgressDialog!=null && mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                        Intent intentLogin = new Intent(LoginActivity.this, MemoMainActivity.class);
                                        startActivity(intentLogin);
                                    }
                                } catch ( Exception e ) {
                                    e.printStackTrace();
                                }
                            }
                        }, 1500);
                    }
                });
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // setResult를 통해 받아온 요청번호, 상태, 데이터

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            Sid = data.getStringExtra("id");
            Spw = data.getStringExtra("pw");
            intRegister++;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        System.out.println("IntRegister onResume : " + intRegister);

        ididid = new String[intRegister];
        pwpwpw = new String[intRegister];
        ididid[0] = "admin";
        pwpwpw[0] = "1234";

        pref = getSharedPreferences("login", MODE_PRIVATE);
        edit = pref.edit();

        if(Sid == null && Spw == null) {
            Sid = pref.getString("id_" + (intRegister - 1), "ididididid");
            Spw = pref.getString("pw_" + (intRegister - 1), "pwpwpwpwpw");
//            System.out.println("if in Sid : "+Sid);
//            System.out.println("if in Spw : "+Spw);
        }
//        System.out.println("Sid : "+Sid);
//        System.out.println("Spw : "+Spw);

        edit.putString(Sid, Spw);
        edit.putInt("saveIntReg", intRegister);

        for (int i = 0; i < intRegister-1; i++) {
            ididid[intRegister - 1 - i] = pref.getString("id_" + (intRegister - 1 - i), "ididid");
            pwpwpw[intRegister - 1 - i] = pref.getString("pw_" + (intRegister - 1 - i), "pwpwpw");
        }

        ididid[intRegister - 1] = Sid;
        pwpwpw[intRegister - 1] = Spw;

//        for (int i = 0; i < intRegister; i++) {
//            System.out.println("ididid["+ i +"] : "+ididid[i]);
//            System.out.println("pwpwpw["+ i +"] : "+pwpwpw[i]);
//        }

        for (int i = 0; i <intRegister; i++) {
            edit.putString("id_" + i, ididid[i]);
            edit.putString("pw_" + i, pwpwpw[i]);
        }
        for (int i = 0; i <intRegister; i++) {
            System.out.println("editput ididid["+ i +"] : "+pref.getString("id_" + i, "ididididid"));
            System.out.println("editput pwpwpw["+ i +"] : "+pref.getString("pw_" + i, "pwpwpwpwpw"));
        }

        edit.commit();

        jobIDPW = new JSONObject();
        for (int i = 0; i < intRegister; i++) {
            try {
                jobIDPW.put(ididid[i], pwpwpw[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("OnPause jobj : " + jobj);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        System.out.println("IntRegister onPause : " + intRegister);

        Eid.setText("");
        Epw.setText("");

//        edit.clear();
//        edit.commit();
    }
}