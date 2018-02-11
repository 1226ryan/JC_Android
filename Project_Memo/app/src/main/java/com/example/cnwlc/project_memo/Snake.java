package com.example.cnwlc.project_memo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Snake extends AppCompatActivity {

    private SnakeView mSnakeView;
    private TextView mTime;
    private TextView mScore;
    private TextView mSpeed;
    private static String ICICLE_KEY = "snake-view";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        // TextView 정의
        mTime  = (TextView) findViewById(R.id.time);
        mScore = (TextView) findViewById(R.id.score);
        mSpeed = (TextView) findViewById(R.id.speed);
        // SnakeView 함수 객체 생성하고 그 뷰에 넣을 텍스트 생성
        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setTextView((TextView) findViewById(R.id.text));
        // TextView 에 텍스트 넣기
        mTime.setText("00 : 00 : 00");
        mScore.setText("Score : " + 0);
        mSpeed.setText("Delay : " + 0);
        // 정의한 TextView를 SnakeView에서 나타내기
        mSnakeView.setTextView(mTime, mScore, mSpeed);

        // 저장된 데이터가 없을 경우 Ready 모드를 셋 시켜줌
        if (savedInstanceState == null) {
            // We were just launched -- set up a new game
            mSnakeView.setMode(SnakeView.READY);
        }
        // 저장된 데이터가 있을 경우 가져와서 상태를 재저장하거나 Pause상태로 만들어줌
        else {
            // We are being restored
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }
    }

    // 종료된 경우가 아닌 onPause 상태에서 일시정지 시켜줌
    @Override
    protected void onPause() {
        super.onPause();
        // Pause the game along with the activity
        mSnakeView.setMode(SnakeView.PAUSE);
    }

    // 인스턴스 상태를 저장해 출력해줌
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }

    /** 방향키
     * 1 => NORTH
     * 2 => SOURTH
     * 3 => EAST
     * 4 => WEST
     */
    public void onUpClicked(View v) {
        mSnakeView.processKey(1);
    }
    public void onDownClicked(View v) {
        mSnakeView.processKey(2);
    }
    public void onRightClicked(View v) {
        mSnakeView.processKey(3);
    }
    public void onLeftClicked(View v) {
        mSnakeView.processKey(4);
    }
}