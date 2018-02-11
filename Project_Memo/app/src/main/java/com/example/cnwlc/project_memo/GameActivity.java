package com.example.cnwlc.project_memo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GameActivity extends Activity {
    private boolean bOneTofifty = false;
    private boolean bSnake = false;

    TextView textView01;
    ProgressBar progress;
    BackgroundTask task;
    int value;

    Button Btn_game_1to50;
    Button Btn_game_snake;
    Button Btn_game_Cancel;
    Button Btn_game_Finish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textView01 = (TextView) findViewById(R.id.progressView);
        progress = (ProgressBar) findViewById(R.id.progress);

        Btn_game_1to50 = (Button) findViewById(R.id.Btn_1to50);
        Btn_game_snake = (Button) findViewById(R.id.Btn_Snake);
        Btn_game_Cancel = (Button) findViewById(R.id.Btn_Cancel);
        Btn_game_Finish = (Button) findViewById(R.id.Btn_Finish);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.Btn_1to50:
                task = new BackgroundTask();
                task.execute(100);

                bOneTofifty = true;
                Btn_game_snake.setEnabled(false);
                break;
            case R.id.Btn_Snake:
                task = new BackgroundTask();
                task.execute(100);

                bSnake = true;
                Btn_game_1to50.setEnabled(false);
                break;
            case R.id.Btn_Cancel:
                task.cancel(true);
                break;
            case R.id.Btn_Finish:
                finish();
                break;
        }
    }

    // OneToFifty Game - AsyncTask<Params, Progress, Result>
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
            value = 0;
            progress.setProgress(value);
        }

        protected Integer doInBackground(Integer... values) {
            while (isCancelled() == false) {
                value++;
                if (value >= 100) {
                    break;
                } else {
                    publishProgress(value);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            return value;
        }

        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0].intValue());
            textView01.setText("" + values[0].toString() + " %");
        }

        protected void onPostExecute(Integer result) {
            progress.setProgress(0);
            textView01.setText("Finished.");

            if (bOneTofifty == true) {
                Intent Game_1to50_intent = new Intent(GameActivity.this, OneToFifty.class);
                startActivity(Game_1to50_intent);
            }
            if (bSnake == true) {
                Intent Game_Snake_intent = new Intent(GameActivity.this, Snake.class);
                startActivity(Game_Snake_intent);
            }
        }

        protected void onCancelled() {
            Btn_game_snake.setEnabled(true);
            Btn_game_1to50.setEnabled(true);
            bOneTofifty = false;
            bSnake = false;

            progress.setProgress(0);
            textView01.setText("Cancelled.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Btn_game_snake.setEnabled(true);
        Btn_game_1to50.setEnabled(true);
        bOneTofifty = false;
        bSnake = false;

        textView01.setText("");
    }
}