package com.elead.card.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * 环形统计图
 *
 * @author gly
 * @since 2016年10月22日
 */
public class ShapArcView extends BaseView {

    private Paint topTextPaint, bottomTextPaint, circlePaint;
    private Context mContext;
    private int colorTop = Color.parseColor("#58c84d");
    private int colorBottom = Color.parseColor("#5488ac");
    private int colorBack = Color.parseColor("#e1e7f0");
    private int mHeight;
    private int mWidth;
    private int doughnutWidth;
    private int topTextHeight;
    private String topText = "Views";
    private String bottomText = "4782";
    private int bottomTextHeight;
    private float endAngle;
    private float startAngle;
    private Paint backPaint;
    private float density;

    public ShapArcView(Context context) {
        this(context, null);

    }

    public ShapArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        density = mContext.getResources().getDisplayMetrics().density;
        topTextPaint = new Paint();
        topTextPaint.setColor(Color.parseColor("#7e8e9f"));
        topTextPaint.setTextAlign(Align.CENTER);

        bottomTextPaint = new Paint();
        bottomTextPaint.setColor(Color.parseColor("#515974"));
        bottomTextPaint.setTextAlign(Align.CENTER);
        bottomTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeCap(Cap.ROUND);
        circlePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));

        backPaint = new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setColor(colorBack);
        backPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        RectF rectF = new RectF(
                (mWidth > mHeight ? Math.abs(mWidth - mHeight) / 2 : 0)
                        + doughnutWidth / 2,
                (mHeight > mWidth ? Math.abs(mHeight - mWidth) / 2 : 0)
                        + doughnutWidth / 2, mWidth
                - (mWidth > mHeight ? Math.abs(mWidth - mHeight) / 2
                : 0) - doughnutWidth / 2, mHeight
                - (mHeight > mWidth ? Math.abs(mHeight - mWidth) / 2
                : 0) - doughnutWidth / 2);

        canvas.drawArc(rectF, 0, 360, false, backPaint);
        canvas.drawArc(rectF, startAngle, endAngle, false, circlePaint);
        canvas.drawText(topText, mWidth / 2, mHeight / 2 - 3 * density,
                topTextPaint);
        canvas.drawText(bottomText, mWidth / 2, mHeight / 2 + bottomTextHeight,
                bottomTextPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        float pecH = 10.80f;
        topTextHeight = (int) (mHeight / pecH * 0.89f);
        topTextPaint.setTextSize(topTextHeight);

        bottomTextHeight = (int) (mHeight / pecH * 1.66f);
        bottomTextPaint.setTextSize(bottomTextHeight);

        doughnutWidth = (int) (mHeight / pecH * 1.4f);
        circlePaint.setStrokeWidth(doughnutWidth);
        backPaint.setStrokeWidth(doughnutWidth);
        LinearGradient shader = new LinearGradient(mWidth / 2, 0, mWidth / 2,
                mHeight, new int[]{colorTop, colorBottom}, null,
                Shader.TileMode.CLAMP);
        circlePaint.setShader(shader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        float childWidthSize = getMeasuredWidth();
        float childHeightSize = childWidthSize;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void init(float percent, String topText, String bottomText,
                     String colorTop, String colorBottom) {
        this.startAngle = (float) (Math.random() * 360f);
        this.endAngle = endAngle + 360f * percent;
        this.topText = topText;
        this.bottomText = bottomText;
        this.colorTop = Color.parseColor(colorTop);
        this.colorBottom = Color.parseColor(colorBottom);
        invalidate();
    }

}
