package com.elead.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.elead.utils.DensityUtil;


public class ImageTextView extends ImageButton {

    private Paint paint;
    private String text = "";
    private Context context;
    private int textSize = 18;
    private int currTW;
    private float childWidthSize;

    public ImageTextView(Context context) {
        this(context, null);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inIt(context);
    }


    private float textHeight;

    private void inIt(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        textHeight = DensityUtil.dip2px(getContext(), textSize);
        paint.setTextSize(textHeight / 0.85f);
        paint.setColor(Color.WHITE);
//        paint.setFakeBoldText(true);
//        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float mt = paint.measureText(text);
        if (mt >= getWidth()) {
            int index = (int) (getWidth() / mt * text.length());
            paint.setTextAlign(Align.LEFT);
            StringBuilder builder = new StringBuilder();
            builder.append(text.substring(0, index - 1));
            builder.append("..");
            canvas.drawText(builder.toString(), 0,
                    (getHeight() + textHeight) / 2, paint);
        } else {
            paint.setTextAlign(Align.CENTER);
            canvas.drawText(text, getWidth() / 2f,
                    (getHeight() + textHeight) / 2f, paint);
        }
    }

    public void setTextColor(int color) {
        this.paint.setColor(color);
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setTextAttribute(String text, int textSize, int color) {
        this.text = text;
        this.paint.setColor(color);
        this.textSize = textSize;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childHeightSize = getMeasuredHeight();
        if (text.length() > 0) {
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            currTW = bounds.left + bounds.width();
            float padding = childHeightSize / 2;
            childWidthSize = currTW + padding;
        } else {
            childWidthSize = childHeightSize;
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}
