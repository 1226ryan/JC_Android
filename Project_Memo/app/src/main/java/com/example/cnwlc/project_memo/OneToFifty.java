package com.example.cnwlc.project_memo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class OneToFifty extends Activity implements View.OnClickListener {

    private Button m_btn_start = null; // start 버튼
    private Button m_btn_cancel = null; // cancel 버튼
    private Button m_btns[] = new Button[25]; // 버튼1 ~ 25
    private TextView m_tv_time = null;

    private OneToFifty_StopWatch m_sw = null;
    private OneToFifty_StopWatch m_sw2 = null;
//    private StopWatch m_sw3 = null;

    private int m_iStep = 1; // 1 ~ 50현재진행 숫자
    private int m_iNext = 1;
    private int m_ArrNum[] = new int[25];
    private int m_btnNum[] = new int[51];
    private boolean m_bStart = false; // 시작인지 여부
    private boolean m_bBlink = false;
    private boolean m_bBlink2 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_fifty);
        // 함수 호출
        SetBinddingButtons();

        // 1~50 '인트형 스텝' 현재진행 숫자, 시작여부(boolean start), 시간초 텍스트 정의
        m_iStep = 1;
        m_bStart = false;
        m_tv_time = (TextView) findViewById(R.id.tv_time);
        m_tv_time.setText("00:00:00");
        m_tv_time.setTextColor(Color.BLACK);

        // stopwatch 호출
        m_sw  = new OneToFifty_StopWatch();
        m_sw2 = new OneToFifty_StopWatch();
//        m_sw3 = new StopWatch();
    }

    // 버튼 묶음 정의
    private void SetBinddingButtons() {
        m_btn_start = (Button) findViewById(R.id.btn_start);
        m_btn_start.setOnClickListener(this);
        m_btn_cancel = (Button) findViewById(R.id.btn_cancel);
        m_btn_cancel.setOnClickListener(this);

        // 리소스를 정의해서 Drawable 안에 정의해놓은 round를 가져와 .setBackgroundDrawable(shape)로 정의해줌 -> 백그라운드 정의
        Resources res = getResources();
        Drawable shape = res.getDrawable(R.drawable.round);

        //버튼 정의
        for (int i = 0; i < 25; i++) {
            // m_btns[i] = (Button) findViewById(m_btn_ids[i]);
            m_btns[i] = (Button) findViewById(R.id.btn_1 + i);
            Log.d("button", ""+m_btns[i]);
            m_btns[i].setOnClickListener(this);
            m_btns[i].setVisibility(View.INVISIBLE);
            m_btns[i].setTextSize(20);
            m_btns[i].setBackgroundColor(Color.parseColor("#8ff9f3"));
            m_btns[i].setTextColor(Color.parseColor("#000000"));
            m_btns[i].setPadding(1, 1, 1, 1);
            m_btns[i].setBackgroundDrawable(shape);
        }
    }

    // 초기값정의
    public void InitValue() {
        m_iStep = 1;
        m_bStart = false;
        m_btn_start.setText("Start");
        m_tv_time.setText("00:00:00");
    }

    // 클릭시 이벤트 발생
    public void onClick(View v) {
        // 시작 버튼을 누를 시 if문, 중지 버튼을 누를 시 else문으로 이동
        if (v == m_btn_start) {
            if (!m_bStart) { // m_bstart가 false 라면 -> true로 변경
                // 초기 값 함수 불러오기 , m_sw로 StopWatch 클래스의 refresh 불러오기
                InitValue();
                m_sw.refresh();
                m_sw2.refresh();
//                m_sw3.refresh();
            }
            // m_bstart가 false 라면 -> true로 변경된 값이 들어감
            m_bStart = !m_bStart;
            if (m_bStart) {
                // 1 ~ 25버튼 생성 및 x, y좌표로 숫자 섞어주기 - 함수 호출
                initNumberArr(1);
                shakeNumber();
                // 버튼의 높이 길이 구하기
                int bWidth = m_btns[1].getWidth();
                int bHeight = m_btns[1].getHeight();

                // m_btns -> 즉, 누를 버튼 정의
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        m_btns[i * 5 + j].setVisibility(View.VISIBLE);
                        m_btns[i * 5 + j].layout(i*bWidth+6, j*bHeight, i*bWidth+6+bWidth, j*bHeight+bHeight);
                        m_btns[i * 5 + j].setText("" + m_ArrNum[i * 5 + j]);
                        // m_ArrNum[] = [25]로 정의되어 있는 배열의 주소값을 m_btnNum에 넣어주고 주소값에 맞는 '값'을 할당해줌
                        m_btnNum[m_ArrNum[i * 5 + j]] = i * 5 + j;
                        m_btns[i * 5 + j].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    }
                }
                // 쓰레드 및 start() - 함수 호출
                startThread();
                m_sw.start();
                m_sw2.start();
//                m_sw3.start();
                // 26 ~ 50버튼 생성 및 섞어주기 - 함수 호출
                initNumberArr(26);
                shakeNumber();
            } else {
                /**
                 * 왜 stopTread가 2개? -> 시작 쓰레드와 힌트 쓰레드가 2개니까
                 */
                stopThread();
                stopThread2();
            }
            // false 면 stop, true 면 start
            m_btn_start.setText(m_bStart ? "Stop" : "Start");
        } else if(v == m_btn_cancel) {
            finish();
        } else {
            int x = Integer.parseInt( ((Button)v).getText().toString() );
            if (x == m_iStep) {
                stopThread2();
                m_sw2.refresh();
                m_sw2.start();
                ((Button) v).setTextColor(Color.parseColor("#000000"));

                if (m_iStep >= 26) {
                    ((Button) v).setVisibility(View.INVISIBLE);
                } else {
                    ((Button) v).setText("" + m_ArrNum[25 - m_iStep]);
                    m_btnNum[m_ArrNum[25 - m_iStep]] = m_btnNum[m_iStep];
                }

                m_iStep++;
            }

            if (m_iStep == 51) {
                m_bStart = false;
                m_sw.stop();
            }
        }
    }
    // 초기값 정의
    public void initNumberArr(int nStartNum) {
        for (int i = 0; i < 25; i++) {
            m_ArrNum[i] = i + nStartNum;
        }
    }
    // 정해진 숫자 섞음
    public void shakeNumber() {
        int x = 0;
        int y = 0;
        int tmp = 0;
        Random _ran = new Random();

        for (int i = 0; i < 100; i++) {
            x = _ran.nextInt(25);
            y = _ran.nextInt(25);

            if (x == y) continue;

            tmp = m_ArrNum[x];
            m_ArrNum[x] = m_ArrNum[y];
            m_ArrNum[y] = tmp;
        }
    }

    // Thread 객체 생성, start/stop 쓰레드 정의
    // volatile : 멀티쓰레드에서 최적화를 위해 사용(사용 안하면 데이터가 지맘대로 뽑힘) -> 다시 사용 시 공부
    private volatile Thread theThread1;
    public synchronized void startThread() {
        if (theThread1 == null) {
            // 백그라운드Thread1 정의 -> 어떻게 run 될건지 runnable 구현
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
                while (m_bStart) {
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
                double ell = m_sw.getFormatF();
                String strTime = String.format("%02d:%02d:%02d", (int)(ell / 60), (int)(ell % 60), (int)((ell * 100) % 100));
                m_tv_time.setText(strTime);

                if (m_sw2.getElapsedTimeMilli() >= 5000)
                    startThread2();

                super.handleMessage(msg);
            }
        };
    };

    // Thread2 는 시간 초과 시 힌트를 주는 쓰레드
    private volatile Thread theThread2;
    public synchronized void startThread2() {
        if (theThread2 == null) {
            theThread2 = new Thread(backgroundTread2);
            m_bBlink = true;
            theThread2.start();
        }
    }
    public synchronized void stopThread2() {
        if (theThread2 != null) {
            Thread tmpThread2 = theThread2;
            theThread2 = null;
            m_bBlink = false;
            tmpThread2.interrupt();
        }
    }
    private Runnable backgroundTread2 = new Runnable() {
        public void run() {
            if (Thread.currentThread() == theThread2) {
                while (m_bBlink) {
                    try {
                        if (m_iStep > 25)
                            m_iNext = m_iStep - 25;
                        else
                            m_iNext = m_iStep;

                        theHandle2.sendMessage(theHandle2.obtainMessage());
                        Thread.sleep(500);

                    } catch (final InterruptedException e) {
                        return;
                    } catch (final Exception e) {
                        return;
                    }
                }
            }
        }
        Handler theHandle2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (m_bBlink2 == true) {
                    m_btns[m_btnNum[m_iStep]].setTextColor(Color.parseColor("#FFFF00"));
                    m_bBlink2 = false;
                } else {
                    m_btns[m_btnNum[m_iStep]].setTextColor(Color.parseColor("#B90000"));
                    m_bBlink2 = true;
                }
                super.handleMessage(msg);
            }
        };
    };
}