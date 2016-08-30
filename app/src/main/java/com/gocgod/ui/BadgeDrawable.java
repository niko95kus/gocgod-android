package com.gocgod.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by Kevin on 8/29/2016.
 */
public class BadgeDrawable extends Drawable {

    private float mTextSize;
    private Paint mBadgePaint;
    private Paint mTextPaint;
    private Rect mTxtRect = new Rect();

    private String mCount = "";
    private boolean mWillDraw = false;

    public BadgeDrawable(Context context) {
        //mTextSize = context.getResources().getDimension(R.dimen.shop_cart_badge);

        mBadgePaint = new Paint();
        mBadgePaint.setColor(Color.RED);
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextSize(15);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mWillDraw) {
            return;
        }

        Rect localRect = getBounds();
        float width = localRect.right - localRect.left;
        float height = localRect.bottom - localRect.top;
        float circleRadius;
        circleRadius = Math.min(width, height)/4.0f+ 2.5F;
        if (Integer.parseInt(this.mCount) < 10) {
            circleRadius = Math.min(width, height)/4.0f+ 2.5F;
        } else {
            circleRadius = Math.min(width, height)/4.0f+ 4.5F;
        }

        float circleX = width - circleRadius + 6.2F;
        float circleY = circleRadius - 9.5f;
        canvas.drawCircle(circleX, circleY, circleRadius, this.mBadgePaint);
        this.mTextPaint.getTextBounds(this.mCount, 0, this.mCount.length(), this.mTxtRect);
        float textY = circleY + (this.mTxtRect.bottom - this.mTxtRect.top) / 2.0F;
        float textX = circleX;

        if (Integer.parseInt(this.mCount) >= 10) {
            textX = textX - 1.0F;
            textY = textY - 1.0F;
        }
        canvas.drawText(this.mCount, textX, textY , this.mTextPaint);
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    public void setCount(int count) {
        this.mCount = Integer.toString(count);
        if (count > 0) {
            this.mWillDraw = true;
            invalidateSelf();
        }
    }

    @Override
    public void setAlpha(int alpha) {
        // do nothing
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // do nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
