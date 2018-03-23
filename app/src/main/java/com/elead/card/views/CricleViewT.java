package com.elead.card.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class CricleViewT extends View {


    private Context mContext;
    private Paint paint;
    private int fillColor = Color.parseColor("#E2CAB5");
    private int center;
    private int innerCircle;
    private String text;

    public CricleViewT(Context context) {
        this(context, null);
    }

    public CricleViewT(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CricleViewT(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true); // 消除锯齿

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CircularImageViewo);
        fillColor = a.getColor(R.styleable.CircularImageViewo_fillColor,
                0XFFFFFFFF);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        center =getWidth()/2/ 2;
        innerCircle = dip2px(mContext, 8); // 内圆半径

        paint.setColor(fillColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(center, center, innerCircle, this.paint);
        canvas.drawText("闪光银", center+center/2, center+center/2, paint);
        paint.setTextSize(dip2px(mContext, 14));

    }


    /* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setColor(int color) {
        this.fillColor = color;
    }

    public void setText(String text) {
        this.text = text;
    }
}
