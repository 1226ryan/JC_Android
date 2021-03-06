package com.example.cnwlc.project_memo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;

public class SnakeView extends Snake_TileView {

    private static final String TAG = "SnakeView";

    /**
     * Current mode of application: READY to run, RUNNING, or you have already lost.
     * static final ints are used instead of an enum(식별번호, 열거형) for performance reasons.
     */
    private boolean isStart = false;
    private int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;

    /**
     * Current direction the snake is headed.
     */
    private int mGameTime = 0;
    private int mDirection = NORTH;
    private int mNextDirection = NORTH;
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;

    /**
     * Labels for the drawables that will be loaded into the TileView class
     */
    private static final int RED_STAR = 1;
    private static final int YELLOW_STAR = 2;
    private static final int GREEN_STAR = 3;
//    private static final int SNAKE_STAR = 4;
//    private static final int NULL_STAR = 5;

    /**
     * mScore: used to track the number of apples captured
     * mMoveDelay (Speed): number of milliseconds between snake movements.
     * This will decrease as apples are captured.
     */
    public long mScore = 0;
    public long mMoveDelay = 150;
//    public int mLife = 3;
    /**
     * mLastMove: tracks the absolute time when the snake last moved, and is used
     * to determine if a move should be made based on mMoveDelay.
     */
    private long mLastMove;

    /**
     * mStatusText: text shows to the user in some run states
     */
    private TextView mStatusText;
    private TextView mTextTime;
    private TextView mTextScore;
    private TextView mTextSpeed;

    /**
     * mSnakeTrail: a list of Coordinates(좌표) that make up the snake's body
     * mAppleList: the secret location of the juicy apples the snake craves.
     */
    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

    /**
     * Everyone needs a little randomness in their life (어디에나 필요한 약간의 랜덤수)
     */
    private static final Random RNG = new Random();

    /**
     * Create a simple handler that we can use to cause animation to happen. (애니메이션을 발생 시키기위한 간단한 핸드러를 만든다)
     * We set ourselves as a target and we can use the sleep() function to cause an update/invalidate to occur at a later date.
     * (우리는 업데이트와 무효화를 위해 다음 시간을 발생 시키기 위해 sleep()를 사용한다)
     */
    private RefreshHandler mRedrawHandler = new RefreshHandler();
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            SnakeView.this.update();
            SnakeView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int hh = mGameTime / 3600;
            int mm = (mGameTime % 3600) / 60;
            int sec = mGameTime % 60;

            String strTime = String.format("%02d : %02d : %02d", hh, mm, sec);
            sendEmptyMessageDelayed(0, 1000);
            mTextTime.setText(strTime);
            mGameTime++;
        }
    };

    /**
     * Constructs a SnakeView based on inflation from XML (XML로부터 SnakeView를 만들어 낸다)
     */
    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSnakeView();
    }
    public SnakeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSnakeView();
    }
    private void initSnakeView() {
        setFocusable(true);

        Resources r = this.getContext().getResources();

        resetTiles(6);
        loadTile(RED_STAR, getDrawable(r, R.drawable.redstar, null));
        loadTile(YELLOW_STAR, getDrawable(r, R.drawable.yellowstar, null));
        loadTile(GREEN_STAR, getDrawable(r, R.drawable.greenstar, null));
//        loadTile(SNAKE_STAR, getDrawable(r, R.drawable.snakestar, null));
//        loadTile(NULL_STAR, getDrawable(r, R.drawable.nullstar, null));

    }

    private void initNewGame() {
        mSnakeTrail.clear();
        mAppleList.clear();

        // (7,7)에서 시작해 북쪽으로 먼저 이동하는 뱀을 만듬
        mSnakeTrail.add(new Coordinate(7, 7));
        mSnakeTrail.add(new Coordinate(6, 7));
        mSnakeTrail.add(new Coordinate(5, 7));
        mSnakeTrail.add(new Coordinate(4, 7));
        mSnakeTrail.add(new Coordinate(3, 7));
        mSnakeTrail.add(new Coordinate(2, 7));
        mNextDirection = SOUTH;

        // 사과 시작점 랜덤
        addRandomApple();
        addRandomApple();
        addRandomApple();

        mMoveDelay = 150;
        mScore = 0;
    }

    public void setTextView(TextView time, TextView score, TextView speed) {
        mTextTime = time;
        mTextScore = score;
        mTextSpeed = speed;
    }

    /**
     * Given a ArrayList of coordinates,
     * we need to flatten(평평하게 하다) them into an array of ints
     * before we can stuff(채우다) them into a map for flattening and storage.
     * 좌표들의 배열목록이 주어지고,
     * 우리는 평평하게 하고 저장하기 위한 맵 안을 좌표들이 채우기 전에 인트형 배열로 그것들을 평평하게 할 필요가 있다.
     *
     * @param cvec : a ArrayList of Coordinate objects
     * @return : a simple array containing the x/y values of the coordinates as [x1,y1,x2,y2,x3,y3...]
     */
    private int[] coordArrayListToArray(ArrayList<Coordinate> cvec) {
        int count = cvec.size();
        int[] rawArray = new int[count * 2];

        for (int index = 0; index < count; index++) {
            Coordinate c = cvec.get(index);
            rawArray[2 * index] = c.x;
            rawArray[2 * index + 1] = c.y;
        }

        return rawArray;
    }

    /**
     * Save game state so that the user does not lose anything
     * if the game process is killed while we are in the background.
     *
     * @return a Bundle with this view's state
     */
    public Bundle saveState() {
        Bundle map = new Bundle();

        map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
        map.putInt("mDirection", Integer.valueOf(mDirection));
        map.putInt("mNextDirection", Integer.valueOf(mNextDirection));
        map.putLong("mMoveDelay", Long.valueOf(mMoveDelay));
        map.putLong("mScore", Long.valueOf(mScore));
        map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));

        return map;
    }

    /**
     * Given a flattened array of ordinate pairs, we reconstitute(재구성하다) them into a ArrayList of Coordinate objects
     *
     * @param rawArray : [x1,y1,x2,y2,...]
     * @return a ArrayList of Coordinates
     */
    private ArrayList<Coordinate> coordArrayToArrayList(int[] rawArray) {
        ArrayList<Coordinate> coordArrayList = new ArrayList<Coordinate>();

        int coordCount = rawArray.length;
        for (int index = 0; index < coordCount; index += 2) {
            Coordinate c = new Coordinate(rawArray[index], rawArray[index + 1]);
            coordArrayList.add(c);
        }

        return coordArrayList;
    }

    /**
     * Restore game state if our process is being relaunched
     *
     * @param icicle a Bundle containing the game state
     */
    public void restoreState(Bundle icicle) {
        setMode(PAUSE);

        mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
        mDirection = icicle.getInt("mDirection");
        mNextDirection = icicle.getInt("mNextDirection");
        mMoveDelay = icicle.getLong("mMoveDelay");
        mScore = icicle.getLong("mScore");
        mSnakeTrail = coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
    }

    /**
     * handles key events in the game. Update the direction our snake is traveling based on the DPAD.
     * Ignore events that would cause the snake to immediately turn back on itself.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            processKey(NORTH);
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            processKey(SOUTH);
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            processKey(WEST);
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            processKey(EAST);
            return true;
        }
        return super.onKeyDown(keyCode, msg);
    }

    public void processKey(int dir) {
        if (dir == NORTH) {
            if (mMode == READY | mMode == LOSE) {
                /**
                 * At the beginning of the game, or the end of a previous one, we should start a new game.
                 */
                initNewGame();
                setMode(RUNNING);
                update();
                if (isStart == false) {
                    isStart = true;
                    mHandler.sendEmptyMessage(0);
                    mTextScore.setText("Score : " + mScore);
                    mTextSpeed.setText("Delay : " + mMoveDelay);
                }
                return;
            }

            if (mMode == PAUSE) {
                /**
                 * If the game is merely paused, we should just continue where we left off.
                 */
                setMode(RUNNING);
                update();
                if (isStart == false) {
                    isStart = true;
                    mHandler.sendEmptyMessage(0);
                    mTextScore.setText("Score : " + mScore);
                    mTextSpeed.setText("Delay : " + mMoveDelay);
                }
                return;
            }

            if (mDirection != SOUTH) {
                mNextDirection = NORTH;
                return;
            }
        }

        if (dir == SOUTH) {
            if (mDirection != NORTH) {
                mNextDirection = SOUTH;
            }
        }

        if (dir == WEST) {
            if (mDirection != EAST) {
                mNextDirection = WEST;
            }
        }

        if (dir == EAST) {
            if (mDirection != WEST) {
                mNextDirection = EAST;
            }
        }
    }

    /**
     * Sets the TextView that will be used to give information (such as "Game Over" to the user.)
     *
     * @param newView
     */
    public void setTextView(TextView newView) {
        mStatusText = newView;
    }

    /**
     * Updates the current mode of the application (RUNNING or PAUSED or the like)
     * as well as sets the visibility of textview for notification
     *
     * @param newMode
     */
    public void setMode(int newMode) {
        int oldMode = mMode;
        mMode = newMode;

        if (newMode == RUNNING & oldMode != RUNNING) {
            mStatusText.setVisibility(View.INVISIBLE);
            update();
            return;
        }

        Resources res = getContext().getResources();
        CharSequence str = "";

        if (newMode == PAUSE) {
            str = res.getText(R.string.mode_pause);
            if (isStart) {
                mHandler.removeMessages(0);
                isStart = false;
            }
        }
        if (newMode == READY) {
            str = res.getText(R.string.mode_ready);
        }
        if (newMode == LOSE) {
            str = res.getString(R.string.mode_lose_prefix)
                    + res.getString(R.string.mode_lose_suffix)
                    + mScore;
            if (isStart) {
                mHandler.removeMessages(0);
                isStart = false;
                mGameTime = 0;
            }
        }

        mStatusText.setText(str);
        mStatusText.setVisibility(View.VISIBLE);
    }

    /**
     * Selects a random location within the garden that is not currently covered by the snake.
     * Currently _could_ go into an infinite loop if the snake currently fills the garden,
     * but we'll leave discovery of this prize to a truly excellent snake-player.
     */
    private void addRandomApple() {
        Coordinate newCoord = null;
        boolean found = false;

        while (!found) {
            // Choose a new location for our apple
            int newX = 1 + RNG.nextInt(mXTileCount - 2);
            int newY = 1 + RNG.nextInt(mYTileCount - 2);
            newCoord = new Coordinate(newX, newY);

            // Make sure it's not already under the snake
            boolean collision = false;
            int snakelength = mSnakeTrail.size();

            for (int index = 0; index < snakelength; index++) {
                if (mSnakeTrail.get(index).equals(newCoord)) {
                    collision = true;
                }
            }
            // if we're here and there's been no collision, then we have a good location for an apple.
            // Otherwise, we'll circle back and try again
            found = !collision;
        }

        if (newCoord == null) {
            Log.e(TAG, "Somehow ended up with a null newCoord!");
        }
        mAppleList.add(newCoord);
    }

    /**
     * Handles the basic update loop, checking to see if we are in the running state,
     * determining if a move should be made, updating the snake's location.
     */
    public void update() {
        if (mMode == RUNNING) {
            long now = System.currentTimeMillis();

            if (now - mLastMove > mMoveDelay) {
                clearTiles();
                updateWalls();
                updateSnake();
                updateApples();
                mLastMove = now;
            }
            mRedrawHandler.sleep(mMoveDelay);
        }
    }

    /**
     * Draws some walls.
     */
    private void updateWalls() {
        for (int x = 0; x < mXTileCount; x++) {
            setTile(GREEN_STAR, x, 0);
            setTile(GREEN_STAR, x, mYTileCount - 1);
        }
        for (int y = 1; y < mYTileCount - 1; y++) {
            setTile(GREEN_STAR, 0, y);
            setTile(GREEN_STAR, mXTileCount - 1, y);
        }
    }

    /**
     * Draws some apples.
     */
    private void updateApples() {
        for (Coordinate c : mAppleList) {
            setTile(YELLOW_STAR, c.x, c.y);
        }
    }

    /**
     * Figure out which way the snake is going, see if he's run into anything (the walls, himself, or an apple).
     * If he's not going to die, we then add(더하다) to the front and subtract(덜다) from the rear in order to simulate motion.
     * If we want to grow him, we don't subtract from the rear.
     */
    private void updateSnake() {
        boolean growSnake = false;

        // grab the snake by the head
        Coordinate head = mSnakeTrail.get(0);
        Coordinate newHead = new Coordinate(1, 1);

        mDirection = mNextDirection;

        switch (mDirection) {
            case EAST: {
                newHead = new Coordinate(head.x + 1, head.y);
                break;
            }
            case WEST: {
                newHead = new Coordinate(head.x - 1, head.y);
                break;
            }
            case NORTH: {
                newHead = new Coordinate(head.x, head.y - 1);
                break;
            }
            case SOUTH: {
                newHead = new Coordinate(head.x, head.y + 1);
                break;
            }
        }

        // Collision detection
        // For now we have a 1-square wall around the entire arena
        if ((newHead.x < 1) || (newHead.y < 1) || (newHead.x > mXTileCount - 2) || (newHead.y > mYTileCount - 2)) {
            setMode(LOSE);
            return;
        }

        // Look for collisions with itself
        int snakelength = mSnakeTrail.size();
        for (int snakeindex = 0; snakeindex < snakelength; snakeindex++) {
            Coordinate c = mSnakeTrail.get(snakeindex);

            if (c.equals(newHead)) {
                setMode(LOSE);
                return;
            }
        }

        // Look for apples
        int applecount = mAppleList.size();
        for (int appleindex = 0; appleindex < applecount; appleindex++) {
            Coordinate c = mAppleList.get(appleindex);
            if (c.equals(newHead)) {
                mAppleList.remove(c);
                addRandomApple();

                mScore++;
                if (mMoveDelay > 50) {
                    mMoveDelay -= 1;
                }
                mTextScore.setText("Score : " + mScore);
                mTextSpeed.setText("Delay : " + mMoveDelay);

                growSnake = true;
            }
        }

        // push a new head onto the ArrayList and pull off the tail
        mSnakeTrail.add(0, newHead);
        // except if we want the snake to grow
        if (!growSnake) {
            mSnakeTrail.remove(mSnakeTrail.size() - 1);
        }

        int index = 0;
        for (Coordinate c : mSnakeTrail) {
            if (index == 0) {
                setTile(YELLOW_STAR, c.x, c.y);
            } else {
                setTile(RED_STAR, c.x, c.y);
            }
            index++;
        }
    }

    /**
     * Simple class containing two integer values and a comparison function.
     * There's probably something I should use instead, but this was quick and easy to build.
     */
    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int newX, int newY) {
            x = newX;
            y = newY;
        }

        public boolean equals(Coordinate other) {
            if (x == other.x && y == other.y) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Coordinate: [" + x + "," + y + "]";
        }
    }
}