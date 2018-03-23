package com.elead.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.elead.module.TbSelecterInfo;


@SuppressLint("DrawAllocation")
public class CusRadioButton extends View {

    private Paint textPaint, messageCountPaint;
    private String text = "";
    private Bitmap defaultBitmap;
    private Bitmap checkedBitmap;
    private Matrix matrix;
    private float bitmapHeight;
    private int defaultTextColor;
    private int checkedTextColor;
    private float vertSpace;
    private String unReadMessageCount = "";

    private boolean isCallBack;
    private boolean isChecked;
    private float messageCountH;
    private float bmScale;

    public CusRadioButton(Context context) {
        super(context);
        inIt(context);
    }

    public CusRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inIt(context);
    }

    public CusRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        inIt(context);
    }

    private float textHeight;
    private float mWidth;
    private float mHeight;

    public void setChecked(boolean checked) {
        isChecked = checked;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isCallBack = false;
                break;
            case MotionEvent.ACTION_UP:
                isCallBack = true;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void inIt(Context context) {
//        unReadMessageCount = (int) (Math.random() * 10);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Align.CENTER);
        // textPaint.setFakeBoldText(true);
        // textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        messageCountPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        messageCountPaint.setTypeface(Typeface.DEFAULT_BOLD);
        messageCountPaint.setTextAlign(Align.CENTER);
        matrix = new Matrix();
        matrix.mapRect(new RectF(0, 0, mWidth, bitmapHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (isChecked) {
            canvas.drawBitmap(checkedBitmap, matrix, null);
            textPaint.setColor(checkedTextColor);
        } else {
            textPaint.setColor(defaultTextColor);
            canvas.drawBitmap(defaultBitmap, matrix, null);
        }
        if (!"".equals(text)) {
            canvas.drawText(text, mWidth / 2, mHeight - vertSpace, textPaint);
        }

        if (!TextUtils.isEmpty(unReadMessageCount)) {
            messageCountPaint.setColor(Color.parseColor("#ff0000"));
            float r = messageCountH / 2;
            float messageY = r;
            float messageX = mWidth - mWidth* 0.3f;
            canvas.drawCircle(messageX, messageY, messageY, messageCountPaint);
            messageCountPaint.setColor(Color.WHITE);
            canvas.drawText(unReadMessageCount + "", messageX,
                    messageY + messageCountPaint.getTextSize() / 3f, messageCountPaint);
        }
        // if (mt >= getWidth()) {
        // int INDEX = (int) (getWidth() / mt * text.length());
        // textPaint.setTextAlign(Align.LEFT);
        // StringBuilder builder = new StringBuilder();
        // builder.append(text.substring(0, INDEX - 1));
        // builder.append("..");
        // canvas.drawText(builder.toString(), 0,
        // mHeight - textHeight / 2, textPaint);
        // } else {
        // textPaint.setTextAlign(Align.CENTER);
        // canvas.drawText(text, getWidth() / 2,
        // (getHeight() + textHeight) / 2, textPaint);
        // }
    }

    public void setUnReadMessageCount(int unReadMessageCount) {
        if (unReadMessageCount >= 99) {
            this.unReadMessageCount = 99 + "+";
        } else if (0 == unReadMessageCount) {
            this.unReadMessageCount = "";
        } else
            this.unReadMessageCount = unReadMessageCount + "";
        invalidate();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        matrix.reset();
        mWidth = getWidth();
        mHeight = getHeight();

        if (null == text || !"".equals(text)) {
            textHeight = mHeight / 10f * 2f;
            vertSpace = textHeight / 2f;
            textPaint.setTextSize(textHeight);
            bitmapHeight = (mHeight - textHeight) / 10f * 8f;
            messageCountH = mHeight / 3;
            messageCountPaint.setTextSize(messageCountH / 2f);
        } else {
            bitmapHeight = mHeight;
        }
        bmScale = defaultBitmap.getWidth() < defaultBitmap.getHeight() ? bitmapHeight
                / defaultBitmap.getWidth()
                : bitmapHeight / defaultBitmap.getHeight();
        if (null == defaultBitmap || null == checkedBitmap) {
            return;
        }
        matrix.postScale(bmScale, bmScale);
        matrix.postTranslate((mWidth - defaultBitmap.getWidth() * bmScale) / 2f,
                mHeight - defaultBitmap.getHeight() * bmScale - textHeight
                        - vertSpace);
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
    }

    public void inItDrawables(TbSelecterInfo tbSelecterInfo) {
        this.defaultTextColor = tbSelecterInfo.getDefaultTvColor();
        this.checkedTextColor = tbSelecterInfo.getCheckedTvColor();
        this.defaultBitmap = BitmapFactory.decodeResource(getResources(),
                tbSelecterInfo.getDefaultIcon());
        this.checkedBitmap = BitmapFactory.decodeResource(getResources(),
                tbSelecterInfo.getCheckedIcon());
        this.text = tbSelecterInfo.getText();
        invalidate();
    }

    public boolean isChecked() {
        return isChecked;
    }
}
