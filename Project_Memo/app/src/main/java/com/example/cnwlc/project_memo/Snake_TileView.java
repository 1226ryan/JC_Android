package com.example.cnwlc.project_memo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class Snake_TileView extends View {

    /**
     * Parameters controlling the size of the tiles and their range within view.
     * Width/Height are in pixels, and Drawables will be scaled to fit to these dimensions.
     * X/Y Tile Counts are the number of tiles that will be drawn.
     */
    protected static int mTileSize;

    protected static int mXTileCount;
    protected static int mYTileCount;

    private static int mXOffset;
    private static int mYOffset;


    /**
     * A hash that maps integer handles specified(지정된) by the subclasser to the
     * drawable that will be used for that reference
     */
    private Bitmap[] mTileArray;

    /**
     * A two-dimensional array of integers in which the number represents(대표하다) the
     * index of the tile that should be drawn at that locations
     */
    private int[][] mTileGrid;

    private final Paint mPaint = new Paint();

    // 함수 정의
    public Snake_TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }
    public Snake_TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TileView);
        mTileSize = a.getInt(R.styleable.TileView_tileSize, 36);
        a.recycle();
    }



    /**
     * Resets the internal array of Bitmaps used for drawing tiles, and
     * sets the maximum index of tiles to be inserted
     */

    public void resetTiles(int tilecount) {
        mTileArray = new Bitmap[tilecount];
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mXTileCount = (int) Math.floor(w / mTileSize);
        mYTileCount = (int) Math.floor(h / mTileSize);

        mXOffset = ((w - (mTileSize * mXTileCount)) / 2);
        mYOffset = ((h - (mTileSize * mYTileCount)) / 2);

        mTileGrid = new int[mXTileCount][mYTileCount];
        clearTiles();
    }

    /**
     * Function to set the specified Drawable as the tile for a particular(특별한, 개개의) integer key.
     * 하나하나 인트형 키 타일로서 지정된 Drawable을 설정하기 위한 함수
     */
    public void loadTile(int key, Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, mTileSize, mTileSize);
        tile.draw(canvas);

        mTileArray[key] = bitmap;
    }

    /**
     * Resets all tiles to 0 (empty)
     */
    public void clearTiles() {
        for (int x = 0; x < mXTileCount; x++) {
            for (int y = 0; y < mYTileCount; y++) {
                setTile(0, x, y);
            }
        }
    }

    /**
     * Used to indicate that a particular tile (set with loadTile and referenced by an integer)
     * should be drawn at the given x/y coordinates
     * during the next invalidate/draw cycle.
     */
    public void setTile(int tileindex, int x, int y) {
        mTileGrid[x][y] = tileindex;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int x = 0; x < mXTileCount; x += 1) {
            for (int y = 0; y < mYTileCount; y += 1) {
                if (mTileGrid[x][y] > 0) {
                    canvas.drawBitmap(mTileArray[mTileGrid[x][y]],
                            mXOffset + x * mTileSize,
                            mYOffset + y * mTileSize,
                            mPaint);
                }
            }
        }
    }
}
