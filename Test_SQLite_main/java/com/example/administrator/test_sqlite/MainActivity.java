package com.example.administrator.test_sqlite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateDatabase, btnInsertDatabase, btnSelectAllData;
    private ListView lvPeople;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreateDatabase = (Button) findViewById(R.id.btnCreateButton);
        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etDBName = new EditText(MainActivity.this);
                etDBName.setHint("DB명을 입력하세요.");

                // Dialog로 Database의 이름을 입력받는다.
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Database 이름을 입력하세요.")
                        .setMessage("Database 이름을 입력하세요.")
                        .setView(etDBName)
                        .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (etDBName.getText().toString().length() > 0) {
                                    dbHelper = new DBHelper(MainActivity.this, etDBName.getText().toString(), null, 1);
                                    dbHelper.testDB();
                                }
                            }
                        }).setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });

        btnInsertDatabase = (Button) findViewById(R.id.btnInsertButton);
        btnInsertDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText etName = new EditText(MainActivity.this);
                etName.setHint("이름을 입력하세요.");
                final EditText etAge = new EditText(MainActivity.this);
                etAge.setHint("나이를 입력하세요.");
                final EditText etPhone = new EditText(MainActivity.this);
                etPhone.setHint("전화번호를 입력하세요.");
                layout.addView(etName);
                layout.addView(etAge);
                layout.addView(etPhone);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("정보를 입력하세요").setView(layout).setPositiveButton("등록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString();
                        String age = etAge.getText().toString();
                        String phone = etPhone.getText().toString();
                        if (dbHelper == null) {
                            dbHelper = new DBHelper(MainActivity.this, "TEST", null, 1);
                        }
                        Person person = new Person();
                        person.setName(name);
                        person.setAge(age);
                        person.setPhone(phone);
                        dbHelper.addPerson(person);
                    }
                }).setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
            }
        });

        lvPeople = (ListView) findViewById(R.id.lvPeople);
        btnSelectAllData = (Button) findViewById(R.id.btnSelectAllData);
        btnSelectAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ListView를 보여준다.
                lvPeople.setVisibility(View.VISIBLE);

                // DB Helper가 Null이면 초기화 시켜준다.
                if (dbHelper == null) {
                    dbHelper = new DBHelper(MainActivity.this, "TEST", null, 1);
                }

                // 1. Person 데이터를 모두 가져온다.
                List people = dbHelper.getAllPersonData();

                // 2. ListView에 Person 데이터를 모두 보여준다.
                lvPeople.setAdapter(new PersonListAdapter(people, MainActivity.this));
            }
        });
    }
}

