package com.elead.upcard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.elead.eplatform.R;
import com.elead.upcard.entity.UpCardErrorEntity;
import com.elead.upcard.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class SignErrorView extends View {

    private UpCardErrorEntity upCardErrorEntity;
    private Paint mLinePaint, paintPoint;
    private float density;
    private int color1 = Color.parseColor("#2ec7c9");
    private int color2 = Color.parseColor("#fd964c");
    private int mWidth;
    private int mHeight;
    private float[] xPoints;
    private float y;
    private Bitmap blueBitmap, yellowBitmap;
    private List<UpCardErrorEntity> upCardErrorEntitys;
    private float marginLR, l1, l2;
    private float wp;
    private float timeBitscale;
    private float timeBitY;
    private float timeTextY;
    private Paint mTextPaint;

    private float timePaddL;
    private Paint mBottomTextPaint;
    private RectF rect;
    private Paint rectPaint;
    private Paint rectTextPaint;

    public SignErrorView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public SignErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public SignErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        density = getContext().getResources().getDisplayMetrics().density;
        mLinePaint.setStrokeWidth(2 * density);
        mLinePaint.setStrokeCap(Cap.ROUND);
        mLinePaint.setColor(color1);

        paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPoint.setStrokeWidth(1 * density);
        paintPoint.setColor(Color.parseColor("#999999"));

        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.parseColor("#ebebeb"));
        rectTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectTextPaint.setColor(Color.parseColor("#8d8d8d"));
        rectTextPaint.setTextAlign(Paint.Align.CENTER);


        blueBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lvbiao);
        yellowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chengbiao);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBottomTextPaint = new Paint();
        mBottomTextPaint.setColor(Color.parseColor("#999999"));
        mBottomTextPaint.setTextAlign(Paint.Align.CENTER);


    }

    private void initTime() {
        upCardErrorEntitys = new ArrayList<>();
        List<UpCardErrorEntity> one = new ArrayList<>();
        List<UpCardErrorEntity> two = new ArrayList<>();
        List<UpCardErrorEntity> three = new ArrayList<>();

        float ontime = Utils.formatTime2Integer(upCardErrorEntity.on_time);

        String onStr = Utils.formatTime(upCardErrorEntity.on_time);
        int onType = upCardErrorEntity.on_normal ? 0 : 1;
        if (ontime <= 8.5f) {
            one.add(new UpCardErrorEntity(onStr, onType));
        } else if (ontime > 8.5f && ontime <= 18) {
            two.add(new UpCardErrorEntity(onStr, onType));
        } else {
            three.add(new UpCardErrorEntity(onStr, onType));
        }
        float offtime = Utils.formatTime2Integer(upCardErrorEntity.off_time);
        String offStr = Utils.formatTime(upCardErrorEntity.off_time);
        int offType = upCardErrorEntity.off_normal ? 0 : 1;
        if (ontime <= 8.5f) {
            one.add(new UpCardErrorEntity(offStr, offType));
        } else if (offtime > 8.5f && offtime <= 18) {
            two.add(new UpCardErrorEntity(offStr, offType));
        } else {
            three.add(new UpCardErrorEntity(offStr, offType));
        }
        if (one.size() == 1) {
            UpCardErrorEntity upCardErrorEntity = one.get(0);
            upCardErrorEntity.position = wp * 159f;
            upCardErrorEntitys.add(upCardErrorEntity);
        } else if (one.size() > 1) {
            UpCardErrorEntity upCardErrorEntity1 = one.get(0);
            UpCardErrorEntity upCardErrorEntity2 = one.get(1);
            upCardErrorEntity1.position = 95f * wp;
            upCardErrorEntity2.position = wp * 220f;
            upCardErrorEntitys.add(upCardErrorEntity1);
            upCardErrorEntitys.add(upCardErrorEntity2);
        }

        if (two.size() == 1) {
            UpCardErrorEntity upCardErrorEntity = two.get(0);
            upCardErrorEntity.position = wp * 359f;
            upCardErrorEntitys.add(upCardErrorEntity);
        } else if (two.size() > 1) {
            UpCardErrorEntity upCardErrorEntity1 = two.get(0);
            UpCardErrorEntity upCardErrorEntity2 = two.get(1);
            upCardErrorEntity1.position = wp * 400f;
            upCardErrorEntity2.position = wp * 653f;
            upCardErrorEntitys.add(upCardErrorEntity1);
            upCardErrorEntitys.add(upCardErrorEntity2);
        }


        if (three.size() == 1) {
            UpCardErrorEntity upCardErrorEntity = three.get(0);
            upCardErrorEntity.position = mWidth - wp * 189f;
            upCardErrorEntitys.add(upCardErrorEntity);
        } else if (three.size() > 1) {
            UpCardErrorEntity upCardErrorEntity1 = three.get(0);
            UpCardErrorEntity upCardErrorEntity2 = three.get(1);
            upCardErrorEntity1.position = mWidth - wp * 250f;
            upCardErrorEntity2.position = mWidth - wp * 126f;
            upCardErrorEntitys.add(upCardErrorEntity1);
            upCardErrorEntitys.add(upCardErrorEntity2);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        wp = mWidth / 1080f;
        marginLR = wp * 48f;
        l1 = wp * 256f;
        l2 = wp * 493f;
        xPoints = new float[]{marginLR, marginLR + l1, marginLR + l1 + l2,
                mWidth - marginLR};
        y = h / 301f * 223f;


        float rectWidth = wp * 260f;
        float rectHeight = wp * 72f;
        rect = new RectF((mWidth - rectWidth) / 2f, -6 * density, mWidth - (mWidth - rectWidth) / 2f, rectHeight + 3 * density);
        timeBitscale = wp * 31f / blueBitmap.getWidth();
        timeBitY = wp * 150f;
        timeTextY = wp * 109f;
        timePaddL = wp * 15f;
        mTextPaint.setTextSize(wp * 36f);
        mBottomTextPaint.setTextSize(30 * wp);
        rectTextPaint.setTextSize(30 * wp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (null == upCardErrorEntity) return;
        initTime();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (upCardErrorEntity.isSelect) {
            paint.setColor(Color.parseColor("#f9f9f9"));
        } else {
            paint.setColor(Color.parseColor("#FFFFFF"));
        }

        canvas.drawRoundRect(new RectF(0, 0, mWidth, mHeight), 3 * density, 3 * density, paint);
        mLinePaint.setColor(color1);
        mLinePaint.setStrokeCap(Cap.ROUND);
        canvas.drawLine(xPoints[0], y, xPoints[1], y, mLinePaint);
        canvas.drawLine(xPoints[2], y, xPoints[3], y, mLinePaint);

        mLinePaint.setColor(color2);
        mLinePaint.setStrokeCap(Cap.BUTT);
        canvas.drawLine(xPoints[1], y, xPoints[2], y, mLinePaint);


        canvas.drawLine(xPoints[1], y + wp * 11f, xPoints[1], y + wp * 23f, paintPoint);

        canvas.drawLine(xPoints[2], y + wp * 11f, xPoints[2], y + wp * 23f, paintPoint);

        canvas.drawText("08:30", xPoints[1], y + wp * 50f, mBottomTextPaint);
        canvas.drawText("18:00", xPoints[2], y + wp * 50f, mBottomTextPaint);

        canvas.drawRoundRect(rect, 3 * density, 3 * density, rectPaint);
        canvas.drawText(upCardErrorEntity.date, rect.centerX(), rect.centerY() + rectTextPaint.getTextSize() / 2f + 1.5f * density, rectTextPaint);


        for (UpCardErrorEntity info : upCardErrorEntitys) {
            if (TextUtils.isEmpty(info.time))return;
            Matrix matrix = new Matrix();
            Bitmap bitmap1;
            if (info.type == 1) {
                bitmap1 = yellowBitmap;
            } else {
                bitmap1 = blueBitmap;
            }
            matrix.setScale(timeBitscale, timeBitscale);
            matrix.postTranslate(info.position, timeBitY);
            canvas.drawBitmap(bitmap1, matrix, null);
            canvas.drawText(info.time, info.position + timePaddL, timeTextY + mTextPaint.getTextSize() / 3f * 2f, mTextPaint);
        }
    }


    @SuppressWarnings("unused")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize / 3.588f);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setUpCardErrorEntity(UpCardErrorEntity upCardErrorEntity) {
        this.upCardErrorEntity = upCardErrorEntity;
        Log.d("setUpCardErrorEntity",upCardErrorEntity.toString());
        postInvalidate();
    }

}
