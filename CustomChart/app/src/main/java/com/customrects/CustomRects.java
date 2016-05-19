package com.customrects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mwg on 16-5-18.
 */
public class CustomRects extends View {
    private static final String TAG = "CustomRects";

    private float defaultTextSize = 15;
    private Paint mPaint;
    private int widthInterval = 50;
    private int destHeight = 400;
    private float mScale = 1;
    private int rectCount = 10;

    public CustomRects(Context context) {
        this(context, null);
    }

    public CustomRects(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRects(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(defaultTextSize * mScale);
        mPaint.setAntiAlias(true);
        //mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

      //  Log.d(TAG, "height is " + getHeight());
        if (destHeight > getHeight() - 10) {
            destHeight = getHeight() - 10;
        }

        canvas.translate((getWidth() - widthInterval * rectCount * mScale) / 2,(getHeight() - destHeight * mScale) /2);
        for (int i = 0; i < rectCount; i++) {
            float srcHeight = (float) (Math.random() * destHeight * mScale);
            RectF rect = new RectF(i * widthInterval * mScale + 5 * mScale, srcHeight, (i + 1) * widthInterval * mScale, destHeight * mScale);
            canvas.drawRect(rect, mPaint);

          String text = "" + (destHeight - (int) srcHeight);
            mPaint.setTextSize(defaultTextSize * mScale);
            float textLength = mPaint.measureText(text, 0, text.length());
            Paint.FontMetrics textFontMetric = mPaint.getFontMetrics();
            float begin = ((widthInterval - 5) * mScale - textLength) / 2 + i * widthInterval * mScale + 5 * mScale;
            float baseLine = srcHeight  - textFontMetric.bottom;
            canvas.drawText(text, begin, baseLine, mPaint);
        }
    }

    private int mLastX;
    private int mLastY;
    private int mFirstPointerId;

    private int mOldDest;
    private int mCurrentDest;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "down index: " + event.getActionIndex() + ", event pointer id: "+ event.getPointerId(event.getActionIndex()));
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mFirstPointerId = event.getPointerId(event.getActionIndex());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "down point index: " + event.getActionIndex() + ", event pointer id: "+ event.getPointerId(event.getActionIndex()));
                if(event.getPointerCount() > 1) {
                    int x1 = (int) event.getX(0);
                    int y1 = (int) event.getY(0);
                    int x2 = (int) event.getX(1);
                    int y2 = (int) event.getY(1);

                    mOldDest =Math.max(1, (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getPointerCount() == 1) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    if(event.getPointerId(event.getActionIndex()) == mFirstPointerId) {
                        int offsetX = mLastX - x;
                        int offsetY = mLastY - y;
                        scrollBy(offsetX, offsetY);
                    }
                    mLastX = x;
                    mLastY = y;
                } else if (event.getPointerCount() > 1) {
                    int x1 = (int) event.getX(0);
                    int y1 = (int) event.getY(0);
                    int x2 = (int) event.getX(1);
                    int y2 = (int) event.getY(1);
                    mCurrentDest= Math.max(1, (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 -y1)));
                    mScale = (float)mCurrentDest / (float)mOldDest * mScale;
                //    Log.d(TAG, "scale is " + mScale + ", mCurrent dest is " + mCurrentDest + ", mOld dest is " + mOldDest);
                   mOldDest = mCurrentDest;
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "up  index: " + event.getActionIndex() + ", event pointer id: "+ event.getPointerId(event.getActionIndex()));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "up point index: " + event.getActionIndex() + ", event pointer id: "+ event.getPointerId(event.getActionIndex()));
                break;
        }

        return true;
    }
}
