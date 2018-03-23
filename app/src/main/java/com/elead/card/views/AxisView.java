package com.elead.card.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.elead.utils.DimenUtils;
import com.elead.utils.Util;

import static cn.finalteam.mygallery.R.color.qianlv;
import static cn.finalteam.mygallery.R.color.fontcolors1;
import static cn.finalteam.mygallery.R.color.fontcolors4;

/**
 * @desc 轴图
 * @auth Created by Administrator on 2016/11/1 0001.
 */

public class AxisView extends View {

    private Activity activity;
    private Paint circlePaint, circlePaintIn, linePaint, textPaint;
    private float XLength, YLength, XScale, XStart, YStart, textWidth, textHeight, lineStrokeWidth = 6;
    private String[] statusArray = new String[] {"已审核","已监控","已验收","待验收审核","待付款","待评价"};

    /**
     * 圆形标点的半径
     */
    private float radius = 22;

    public AxisView(Context context) {
        this(context,null);
    }

    public AxisView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AxisView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)XLength, (int)YLength);
    }

    private void init(){
        //获取屏幕宽度
        activity = (Activity)getContext();
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        XLength=metric.widthPixels;
        XScale = XLength/6f;
        YLength = XLength/3f;
        XStart = XScale/2f;
        YStart = YLength/3f-lineStrokeWidth/2f;

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineStrokeWidth);
        linePaint.setColor(activity.getResources().getColor(qianlv));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(activity.getResources().getColor(qianlv));

        circlePaintIn = new Paint();
        circlePaintIn.setAntiAlias(true);
        circlePaintIn.setStyle(Paint.Style.STROKE);
        circlePaintIn.setStrokeWidth(4);
        circlePaintIn.setColor(activity.getResources().getColor(fontcolors1));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(DimenUtils.dip2px(activity, 14));
        textWidth = Util.getHalfTextWidth(textPaint, statusArray[0]);
        textHeight = Util.getHalfTextHeigth(textPaint, statusArray[0]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(XStart, YStart, XLength-XStart, YStart, linePaint);

        for (int i = 0, lenth = statusArray.length; i < lenth; i ++){
            if (i == 3) {
                circlePaint.setColor(Color.parseColor("#fa9450"));
                textPaint.setColor(Color.parseColor("#fa9450"));
                canvas.drawText(statusArray[i].substring(0,3), XStart-textWidth+i*XScale, YStart+radius+textHeight*3f, textPaint);
                canvas.drawText(statusArray[i].substring(3), XStart-textWidth+i*XScale + textWidth/3f,
                        YStart+radius+textHeight*4f/3f+textHeight*3f+textHeight*4f/3f, textPaint);
            } else if (i > 3){
                circlePaint.setColor(activity.getResources().getColor(qianlv));
                textPaint.setColor(activity.getResources().getColor(fontcolors4));
                drawText(canvas, i);
            } else {
                circlePaint.setColor(activity.getResources().getColor(qianlv));
                textPaint.setColor(Color.WHITE);
                drawText(canvas, i);
            }
            drawCircle(canvas, i);
        }
    }

    private void drawCircle(Canvas canvas, int position){
        canvas.drawCircle(XStart+position*XScale, YStart, radius, circlePaint);
        canvas.drawCircle(XStart+position*XScale, YStart, radius-6, circlePaintIn);
    }

    private void drawText(Canvas canvas, int position){
        canvas.drawText(statusArray[position], XStart-textWidth+position*XScale, YStart+radius+textHeight*3f, textPaint);
    }
}
