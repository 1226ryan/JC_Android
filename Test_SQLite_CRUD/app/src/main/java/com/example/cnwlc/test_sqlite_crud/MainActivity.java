package com.example.cnwlc.test_sqlite_crud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String dbName = "st_file.db";
    int dbVersion = 4;
    private SQLiteDatabase sqLiteDatabase;
    String tag = "SQLite"; // Log의 tag 로 사용
    String tableNameStudent = "student"; // DB의 table 명

    EditText etName;
    EditText etAge;
    EditText etAddress;
    Button bInsert;    // 삽입버튼
    Button bUpdate;
    Button bDelete;
    Button bSelect;
    Button bOther;
    TextView tv;   // 결과창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.editText1);
        etAge = (EditText) findViewById(R.id.editText2);
        etAddress = (EditText) findViewById(R.id.editText3);
        bInsert = (Button) findViewById(R.id.buttonInsert);
        bUpdate = (Button) findViewById(R.id.buttonUpdate);
        bDelete = (Button) findViewById(R.id.buttonDelete);
        bSelect = (Button) findViewById(R.id.buttonSelect);
        bOther = (Button) findViewById(R.id.buttonOther);
        tv = (TextView) findViewById(R.id.textView4);

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(
                this,  // 현재 화면의 제어권자
                dbName,  // 데이터베이스 이름
                null, // 커서팩토리 - null 이면 표준 커서가 사용됨
                dbVersion);

        try {
            sqLiteDatabase = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag, "데이터 베이스를 열수 없음");
            finish();
        }

        onClickEvent();


    } // end of onCreate

    private void onClickEvent() {
        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String address = etAddress.getText().toString();

                if ("".equals(name) || "".equals(age) || "".equals(address)) {
                    tv.setText("insert 실패 - 항목을 입력하세요");
                    return;// 그냥 빠져나감
                }
                int a = 0;
                try {
                    a = Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    tv.setText("insert 실패 - age는 숫자로 입력하세요");
                    return;
                }

                insert(name, a, address);
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String address = etAddress.getText().toString();

                if ("".equals(name) || "".equals(age) || "".equals(address)) {
                    tv.setText("update 실패 - 항목을 입력하세요");
                    return;// 그냥 빠져나감
                }
                int a = 0;
                try {
                    a = Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    tv.setText("insert 실패 - age는 숫자로 입력하세요");
                    return;
                }

                update(name, a, address);
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();

                if ("".equals(name)) {
                    tv.setText("delete 실패 - 항목을 입력하세요");
                    return;// 그냥 빠져나감
                }

                delete(name);
            }
        });

        bSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");//기존에 textView에 있는 값을 지우고 보여주기
                select();
            }
        });

        bOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // insert ("hong", 29, "Seoul"); // insert 문 - 삽입 추가
        // select (); // select 문 - 조회
        // update (); // update 문 - 수정 변경
        // delete (); // delete 문 - 삭제 행제거
    }

    private void delete(String name) {
        int result = sqLiteDatabase.delete(tableNameStudent, "name=?", new String[]{name});
        Log.d(tag, result + "개 row delete 성공");
        tv.setText(result + "개 row delete 성공");
        select(); // delete 후에 select 하도록
    }

    private void update(String name, int age, String address) {
        ContentValues values = new ContentValues();
        values.put("age", age);         // 바꿀값
        values.put("address", address); // 바꿀값

        int result = sqLiteDatabase.update(tableNameStudent,
                values,    // 뭐라고 변경할지 ContentValues 설정
                "name=?", // 바꿀 항목을 찾을 조건절
                new String[]{name});// 바꿀 항목으로 찾을 값 String 배열
        Log.d(tag, result + "번째 row update 성공");
        tv.setText(result + "번째 row update 성공");
        select(); // 업데이트 후에 조회하도록
    }

    private void select() {
        Cursor c = sqLiteDatabase.query(tableNameStudent, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int _id = c.getInt(0);
            String name = c.getString(1);
            int age = c.getInt(2);
            String address = c.getString(3);

            Log.d(tag, "_id:" + _id + ",name:" + name
                    + ",age:" + age + ",address:" + address);
            tv.append("\n" + "_id:" + _id + ",name:" + name
                    + ",age:" + age + ",address:" + address);

            // 키보드 내리기
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow
                    (getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void insert(String name, int age, String address) {
        ContentValues values = new ContentValues();
        // 키,값의 쌍으로 데이터 입력
        values.put("name", name);
        values.put("age", age);
        values.put("address", address);
        long result = sqLiteDatabase.insert(tableNameStudent, null, values);
        Log.d(tag, result + "번째 row insert 성공했음");
        tv.setText(result + "번째 row insert 성공했음");
        select(); // insert 후에 select 하도록
    }
}