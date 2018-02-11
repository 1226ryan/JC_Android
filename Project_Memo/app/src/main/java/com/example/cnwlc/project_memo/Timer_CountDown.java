package com.example.cnwlc.project_memo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Timer_CountDown extends Activity {

    private long startTime = 0;
    private long stopTime = 0;
    private long elapsed = 0;
    private boolean running = false;
    double ftime = 0.0;
    int EditToInt;

    EditText CountDownEt;

    TextView CountDownTv;
    TextView TimerTv;

    Button CountDownBtn;
    Button TimerStartBtn;
    Button TimerStopBtn;
    Button CancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_countdown);

        CountDownEt = (EditText) findViewById(R.id.CDEt);
        CountDownTv = (TextView) findViewById(R.id.CDTv);
        TimerTv = (TextView) findViewById(R.id.TimerTv);

        CountDownBtn = (Button) findViewById(R.id.CountDownBtn);
        TimerStartBtn = (Button) findViewById(R.id.TimerStartBtn);
        TimerStopBtn = (Button) findViewById(R.id.TimerStopBtn);
        CancelBtn = (Button) findViewById(R.id.CancelBtn);

        TimerTv.setText("00 : 00 : 00");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CountDownBtn :
                String str = CountDownEt.getText().toString();
                CountDownTv.setText(str);

                if(str.equals("")) {
                    Toast.makeText(getApplicationContext(), "시간을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    EditToInt = Integer.parseInt(str);

                    if(EditToInt == 0) {
                        Toast.makeText(getApplicationContext(), "0은 카운트다운이 안 돼", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CountDownBtn.setEnabled(false);

                    final Handler CountDownHandler = new Handler() {
                        public void handleMessage(Message msg) {
                            if(msg.what == 1) {
                                Log.d("Number : ", "1");
                            } else if(msg.what == 2) {
                                Log.d("Number : ", "2");
                            }
                            CountDownTv.setText(""+EditToInt);

                            if(EditToInt == 0) {
                                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);

                                Toast.makeText(getApplicationContext(), "카운트가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                CountDownBtn.setEnabled(true);
                            }
                        }
                    };
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            for(;EditToInt>0; EditToInt--) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                CountDownHandler.sendEmptyMessage(1);

                                Message msg = Message.obtain();
                                msg.what = 2;
                                CountDownHandler.sendMessage(msg);
                            }
                        }
                    };
                    Thread thread = new Thread(r);
                    thread.start();
                }
                break;
            case R.id.TimerStartBtn :
                startThread();
                TimerStartBtn.setEnabled(false);
                break;
            case R.id.TimerStopBtn :
                stopThread();
                TimerStartBtn.setEnabled(true);
                break;
            case R.id.CancelBtn :
                finish();
                break;
        }
    }

    //elaspsed time in milliseconds
    public long getElapsedTimeMilli() {
        if (running) {
            elapsed = ((System.nanoTime() - startTime) / 1000000);
        } else {
            elapsed = ((stopTime - startTime) / 1000000);
        }

        return elapsed;
    }
    public double getFormatF() {
        long ltime = getElapsedTimeMilli();
        double nTime = ltime / 1000.0;
        ftime = Double.parseDouble(String.format("%02f", nTime));

        return ftime;
    }

    // Thread 객체 생성, start/stop 쓰레드 정의
    // volatile : 멀티쓰레드에서 최적화를 위해 사용(사용 안하면 데이터가 지맘대로 뽑힘) -> 다시 사용 시 공부
    private volatile Thread theThread1;
    public synchronized void startThread() {
        if (theThread1 == null) {
            this.startTime = System.nanoTime();
            this.running = true;

            theThread1 = new Thread(backgroundTread1);
            theThread1.start();
        }
    }
    public synchronized void stopThread() {
        if (theThread1 != null) {
            Thread tmpThread = theThread1;
            theThread1 = null;
            tmpThread.interrupt();
        }
    }
    // Handler에게 시간 메세지를 얻고 0.01초 간격으로 Thread.메세지를 보냄
    // Handler를 통해 5초 이상 누르지 못한다면, 힌트를 주는 Thread2 발생(main이 아닌 sub 쓰레드 발동)
    private Runnable backgroundTread1 = new Runnable() {
        public void run() {
            if (Thread.currentThread() == theThread1) {
                while (true) {
                    try {
                        theHandle.sendMessage(theHandle.obtainMessage());
                        Thread.sleep(10);
                    } catch (final InterruptedException e) {
                        return;
                    } catch (final Exception e) {
                        return;
                    }
                }
            }
        }

        Handler theHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                double ell = getFormatF();
                String strTime = String.format("%02d:%02d:%02d",
                        (int)(ell / 60), (int)(ell % 60), (int)((ell * 100) % 100));
                TimerTv.setText(strTime);

                super.handleMessage(msg);
            }
        };
    };
}
