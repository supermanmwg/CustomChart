package com.customrects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by mwg on 16-5-19.
 */
public class PorterDuffxfermodeWave extends View {
    private static final String TAG = "PorterDuffxfermodeWave";
    private static int DEFAULT_SPEED_DP = 15;
    private Paint mPicPaint;
    private Paint mCirclePaint;
    private Paint mBackGroundCirclePaint;
    private Bitmap mPicBitmap;
    private Bitmap mCircleBitmap;
    private Rect mPicSrcRect;
    private Rect mPicDstRect;
    private Rect mCircleRect;
    private int srcMoveOffset;
    private int srcMoveCurrentPos;
    private PorterDuffXfermode mPicPorterDuffXfermode;
    private int mPercent = 0;

    public PorterDuffxfermodeWave(Context context) {
        this(context, null);
    }

    public PorterDuffxfermodeWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PorterDuffxfermodeWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPicPaint.setDither(true);
        mPicPaint.setFilterBitmap(true);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setDither(true);
        mBackGroundCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackGroundCirclePaint.setDither(true);
        mBackGroundCirclePaint.setStyle(Paint.Style.STROKE);
        mBackGroundCirclePaint.setColor(Color.parseColor("#0F28E6"));
        srcMoveOffset = DisplayUtils.dp2px(getContext(), 15);
        mPicPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPicBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.wave_2000)).getBitmap();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    srcMoveCurrentPos += srcMoveOffset;
                    mPercent += 1;
                    if (mPercent > 200) {
                        mPercent = 0;
                    }
                    if (srcMoveCurrentPos > mPicBitmap.getWidth()) {
                        srcMoveCurrentPos = 0;
                    }
                    Log.d(TAG, "current move pos is " + srcMoveCurrentPos);
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    postInvalidate();
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mBackGroundCirclePaint);
        int count = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        Log.d(TAG, "get width is " + getWidth());
        mPicDstRect.set(0, -mPercent + 100, getWidth(), getHeight());
        mPicSrcRect.set(srcMoveCurrentPos, 0, srcMoveCurrentPos + mPicBitmap.getWidth() / 10, mPicBitmap.getHeight());
        canvas.drawBitmap(mPicBitmap, mPicSrcRect, mPicDstRect, mPicPaint);
        mPicPaint.setXfermode(mPicPorterDuffXfermode);
        canvas.drawBitmap(mCircleBitmap, mCircleRect, mCircleRect, mPicPaint);
        mPicPaint.setXfermode(null);
        canvas.restoreToCount(count);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPicSrcRect = new Rect();
        mPicDstRect = new Rect(0, 0, w, h);
        mCircleRect = new Rect(0, 0, w, h);
        mCircleBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas circleCanvas = new Canvas(mCircleBitmap);
        circleCanvas.drawCircle(w / 2, h / 2, w / 2, mCirclePaint);
    }
}
