package com.example.cnwlc.project_memo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmMain extends Activity {
    //View
    private TextView TText;
    private TextView DText;
    //Today 년,월,일,시,분
    private int tYear;
    private int tMonth;
    private int tDay;
    private int tHour;
    private int tMinute;
    //D-day 년,월,일,시,분
    private int dYear;
    private int dMonth;
    private int dDay;
    private int dHour;
    private int dMinute;
    //Dialog
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;

    AlarmManager mAlarmMgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // 알람 관리자 핸들을 구한다
        mAlarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);

        //View 참조
        TText = (TextView)findViewById(R.id.Ttext);
        DText = (TextView)findViewById(R.id.Dtext);

        //현재 날짜,시간 가져오기
        final Calendar c = Calendar.getInstance();
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        tHour = c.get(Calendar.HOUR_OF_DAY);
        tMinute = c.get(Calendar.MINUTE);

        dYear = tYear;
        dMonth = tMonth;
        dDay = tDay;

        //텍스트뷰 갱신
        updateDisplay();
    }
    public void onClick(View view) {
        switch (view.getId()) {
            //날짜설정 이벤트
            case R.id.pickDate :
                showDialog(DATE_DIALOG_ID);
                break;
            //시간설정 이벤트
            case R.id.pickTime :
                showDialog(TIME_DIALOG_ID);
                break;
            case R.id.setting:
                Intent intent = new Intent(AlarmMain.this, AlarmReceive.class);
                PendingIntent pender = PendingIntent.getBroadcast(AlarmMain.this, 0, intent, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(dYear, dMonth, dDay, dHour, dMinute);

                mAlarmMgr.set(AlarmManager.RTC, calendar.getTimeInMillis(), pender);
                Toast.makeText(getApplicationContext(), "알람이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.cancle :
                Toast.makeText(getApplicationContext(), "알람을 취소 하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    //텍스트뷰 갱신
    private void updateDisplay(){
        TText.setText(String.format("%d년 %d월 %d일 %d시 %d분", tYear, tMonth+1, tDay, tHour, tMinute));
        DText.setText(String.format("%d년 %d월 %d일\n %d시 %d분", dYear, dMonth+1, dDay, dHour, dMinute));
    }

    //DatePicker 리스너
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dYear = year;
                    dMonth = monthOfYear;
                    dDay = dayOfMonth;
                    updateDisplay();
                }
            };
    //TimePicker 리스너
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    dHour = hourOfDay;
                    dMinute = minute;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, dYear, dMonth, dDay);

            case TIME_DIALOG_ID :
                return new TimePickerDialog(this, mTimeSetListener, dHour, dMinute, false);
        }
        return null;
    }
}