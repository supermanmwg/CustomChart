package com.customrects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mwg on 16-5-18.
 */
public class CustomRects extends View {
    private Paint mPaint;
    private int widthInterval = 50;
    private int destHeight = 500;

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
        mPaint.setAntiAlias(true);
        //mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate((getWidth() - widthInterval * 30) / 2,600);
        for (int i = 0; i < 30; i++) {
            int srcHeight = (int) (Math.random() * destHeight);
            Rect rect = new Rect(i * widthInterval + 5, srcHeight, (i+1) * widthInterval, destHeight);
            canvas.drawRect(rect, mPaint);

            String text = "" + (500  - srcHeight);
            float textLength = mPaint.measureText(text, 0, text.length());
           // Paint.FontMetrics textFontMetric = ;
            float begin =(widthInterval - 5 - textLength) / 2 + i * widthInterval + 5;
            canvas.drawText(text, begin, srcHeight - 10, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
