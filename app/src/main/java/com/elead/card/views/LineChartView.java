package com.elead.card.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.elead.card.entity.PositionXYEntity;
import com.elead.utils.DimenUtils;
import com.elead.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 折线图
 */
public class LineChartView extends BaseFlingView {

    private Activity activity;
    private float XPoint;
    private float YPoint;
    private float XScale;
    private float YScale;
    private float XLength;
    private float YLength;
    private int textYHight, textXHight, textXWidth;
    private Paint paint, paint2, textYPaint, textXPaint, strokePaint, circlePaint, circleOutPaint;

    private List<Float> data = new ArrayList<>();
    private List<String> XLabel = new ArrayList<String>();
    private List<PositionXYEntity> positionList = new ArrayList<>();
    private boolean isInitPositionList = false;

    private String[] YLabel = new String[5];

    public LineChartView(Context context) {
        this(context,null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        positionList.clear();
        paint = new Paint();
        paint2 = new Paint();
        textYPaint = new Paint();
        textXPaint = new Paint();
        strokePaint=new Paint();
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //获取屏幕宽度
        activity = (Activity)getContext();
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        XLength=metric.widthPixels;
        YLength=XLength/3;
        YScale = YLength/5;
        XScale = XLength/8;
        YPoint = YLength+XScale;
        XPoint = XScale;
        XLength = XLength-XScale - XScale/3;
        XScale = XLength/8;
        int temp = 100;
        for (int i = 0; i < YLabel.length; i++) {
            YLabel[i] = temp + ((i + 1)*10)+"";
        }
        Random random = new Random();
        for (float i = 0; i < 10; i++){
            String str = "Jan"+" "+(int)(20+i);
            XLabel.add(str);
            float number = random.nextInt(150);

            data.add(number);
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.BLUE);
        paint.setAlpha(127);
        paint.setStrokeWidth(4);

        //文字画笔
        textYPaint.setAntiAlias(true); // 去锯齿
        textYPaint.setColor(Color.GRAY); // 画笔颜色
        textYPaint.setTextSize(DimenUtils.sp2px(activity, 12));// 设置轴文字大小
        textYPaint.setAlpha(127);
        textYHight = (int) (Util.getHalfTextHeigth(textYPaint, "1"));

        textXPaint.setAntiAlias(true); // 去锯齿
        textXPaint.setColor(Color.GRAY); // 画笔颜色
        textXPaint.setTextSize(DimenUtils.sp2px(activity, 10));// 设置轴文字大小
        textXHight = (int) (Util.getHalfTextHeigth(textXPaint, "1")*2);
        textXWidth = (int) (Util.getHalfTextWidth(textXPaint, "Jan 22"));

        //虚线笔
        strokePaint.setColor(Color.GRAY);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(4);
        PathEffect effects = new DashPathEffect(new float[]{ 10,5 }, 1);
        strokePaint.setPathEffect(effects);

        //圆
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);

        circleOutPaint.setAntiAlias(true);
        circleOutPaint.setStyle(Paint.Style.STROKE);
        circleOutPaint.setStrokeWidth(6);
        circleOutPaint.setColor(activity.getResources().getColor(cn.finalteam.mygallery.R.color.fontcolors1));
        init(FlingType.X,data.size()*XScale+XScale-XScale*7-XPoint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)(XPoint + data.size()*XScale+XScale), (int)YPoint+ textXHight +20*2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画Y轴
        /*canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);*/

        // Y轴箭头
        /*canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
                + 6, paint); // 箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
                + 6, paint);*/

        // 添加刻度和文字
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                strokePaint.setColor(Color.BLUE);
            } else {
                strokePaint.setColor(Color.GRAY);
            }
            strokePaint.setAlpha(50);
            // 虚线
            Path path = new Path();
            path.moveTo(XPoint, YPoint - ((i+1) * YScale));
            path.lineTo(XPoint+data.size()*XScale+XScale,YPoint - ((i+1) * YScale));
            canvas.drawPath(path, strokePaint);

            canvas.drawText(YLabel[i], XPoint - XPoint*2/3, YPoint - ((i+1) * YScale) + textYHight, textYPaint);// 文字
        }

        // 画X轴
        canvas.drawLine(XPoint, YPoint, XPoint + data.size()*XScale+XScale, YPoint, paint);

        //X轴文字
        for (int i = 0, size = XLabel.size(); i < size; i++){
            canvas.drawText(XLabel.get(i), XPoint+((i+1)*XScale) - textXWidth, YPoint + textXHight +20, textXPaint);// 文字
        }

        // 绘折线
        float baseX=0, baseY=0;
        float maxNumber;
        float baseYScale = YScale*5f/150f;
        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAlpha(80);
        maxNumber = Collections.max(data);
        LinearGradient shader = new LinearGradient(XPoint, YPoint-baseYScale*maxNumber, XPoint,
                YPoint, new int[] { activity.getResources().getColor(cn.finalteam.mygallery.R.color.qianlv), Color.WHITE }, null,
                Shader.TileMode.CLAMP);
        paint2.setShader(shader);
        float no = 0;
        if (data.size() > 1) {
            Path path = new Path();
            Path path4 = new Path();
            path.moveTo(XPoint, YPoint - data.get(0) * baseYScale-2);
            path4.moveTo(XPoint, YPoint-2);
            path.lineTo(XPoint + XScale, YPoint - data.get(0) * baseYScale-2);
            path4.lineTo(XPoint, YPoint - data.get(0) * baseYScale-2);
            for (int i = 0; i < data.size(); i++) {
                float x = XPoint + i * XScale + XScale;
                float y = YPoint - data.get(i) * baseYScale-2;
                no = data.get(i);
                if (!isInitPositionList) {
                    PositionXYEntity xyEntity = new PositionXYEntity();
                    int base = DimenUtils.dip2px(mContext,10);
                    RectF rectF = new RectF(x-base,y-base,x+base,y+base);
                    xyEntity.setRectF(rectF);
                    xyEntity.setX(x);
                    xyEntity.setY(y);
                    xyEntity.setNumber(no);
                    positionList.add(xyEntity);
                }
                path.lineTo(x, y);
                path4.lineTo(x, y);
                /*if (data.get(i) == maxNumber) {
                    baseX = x;
                    baseY = y;
                }*/

                if (mSelectPosition == i) {
                    canvas.drawCircle(x, y, 10, circlePaint);
                    canvas.drawCircle(x, y, 10, circleOutPaint);
                    String s = (int)no+"";
                    canvas.drawText(s, x - Util.getHalfTextWidth(textXPaint, s),
                            y - textXHight - 20, textXPaint);// 文字
                }

            }
            path.lineTo(XPoint + data.size()*XScale+XScale, YPoint- data.get(data.size()-1)*baseYScale-2);
            path4.lineTo(XPoint + data.size()*XScale+XScale, YPoint- data.get(data.size()-1)*baseYScale-2);
            path4.lineTo(XPoint+data.size()*XScale+XScale, YPoint-2);
            canvas.drawPath(path, paint);
            canvas.drawPath(path4, paint2);
            isInitPositionList = true;
        }

        /*if (Util.isNotEmpty(positionList)) {
            for (int i = 0,size = positionList.size(); i <size; i++) {
                PositionXYEntity xyEntity = positionList.get(i);
                float x = xyEntity.getX();
                float y = xyEntity.getY();
                if (xyEntity.isDrawCircel()) {
                    canvas.drawCircle(x, y, 10, circlePaint);
                    canvas.drawCircle(x, y, 10, circleOutPaint);
                    String s = (int)xyEntity.getNumber()+"";
                    canvas.drawText(s, x - Util.getHalfTextWidth(textXPaint, s),
                            y - textXHight - 20, textXPaint);// 文字
                }
            }
        }*/
    }

    private float clickPositionX, clickPositionY, upPositionX, upPositionY;
    private int mSelectPosition = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        clickPositionX = event.getX()+flingOffset;
        clickPositionY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                upPositionX = event.getX() + flingOffset;
                upPositionY = event.getY();
                if (clickPositionX == upPositionX && clickPositionY == upPositionY
                        && Util.isNotEmpty(positionList)) {
                    for (int i = 0, size = positionList.size(); i < size; i++) {
                        PositionXYEntity xyEntity = positionList.get(i);
                        if (null != xyEntity && xyEntity.getRectF().contains(clickPositionX, clickPositionY)) {
                            mSelectPosition = i;
                            invalidate();
                            break;
                        }
                    }
                }
        }
        /*if (event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL) { //手指抬起
            upPositionX = event.getX()+flingOffset;
            upPositionY = event.getY();
            if (clickPositionX == upPositionX && clickPositionY == upPositionY
                                                && Util.isNotEmpty(positionList)) {
                for (int i = 0,size = positionList.size(); i <size; i++){
                    PositionXYEntity xyEntity = positionList.get(i);
                    if (null != xyEntity && xyEntity.getRectF().contains(clickPositionX,clickPositionY)){
                        mSelectPosition = i;
                        invalidate();
                        break;
                    }
                }
            }
        }*/

        return super.onTouchEvent(event);
    }

    public interface ClickListener{
        public void onClick();
    }
}