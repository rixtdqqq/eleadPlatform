package com.elead.card.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.elead.card.entity.PositionXYEntity;
import com.elead.eplatform.R;
import com.elead.utils.DimenUtils;
import com.elead.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 折线图,可带通过不通过
 */
public class EPCardTypeLineChartView extends BaseFlingView {

    private Activity activity;
    private float XPoint;
    private float YPoint;
    private float XScale;
    private float YScale;
    private float XLength;
    private float YLength;
    private float mWidth, mHeight;
    private float incrementY, incrementZ, marginTop;
    private float circleRadiusOut, circleRadius, centerCircleX, centerCircleY;
    private int textYHight, textXHight, textXWidth;
    private Paint linePaint, paint, paint2, textYPaint, textXPaint, strokePaint, circlePaint, circleOutPaint,
                        textPaint, bmpPaint;
    Resources resources;

    private List<Float> data = new ArrayList<>();
    private List<String> XLabel = new ArrayList<String>();
    private List<PositionXYEntity> positionList = new ArrayList<>();
    private boolean isInitPositionList = false;
    private int lineColor, cireleColor, bgLineColor, axisLineColor,
                cireleColorBig, cireleColorOutBig, fontColor;

    private String[] YLabel = new String[5];

    public EPCardTypeLineChartView(Context context) {
        this(context,null);
    }

    public EPCardTypeLineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EPCardTypeLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        positionList.clear();
        paint = new Paint();
        linePaint = new Paint();
        paint2 = new Paint();
        textYPaint = new Paint();
        textXPaint = new Paint();
        textPaint = new Paint();
        strokePaint=new Paint();
        bmpPaint = new Paint();
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //获取屏幕宽度
        activity = (Activity)getContext();
        resources = activity.getResources();
        lineColor = resources.getColor(R.color.tuhuang);
        cireleColor = resources.getColor(R.color.data_time_color);
        axisLineColor = resources.getColor(R.color.qianlv);
        bgLineColor = resources.getColor(R.color.color_464848);
        cireleColorBig = resources.getColor(R.color.elead_1eafad);
        cireleColorOutBig = resources.getColor(R.color.qianlv);
        fontColor = resources.getColor(R.color.fontcolors4);
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        mWidth = metric.widthPixels;
        mHeight = mWidth/1.41f;
        marginTop = mHeight/9.6f;
        incrementY = mHeight/17.1f;
        incrementZ = incrementY/2f;
        XLength=mWidth/1.17f;
        YLength=mHeight/1.42f+incrementZ;
        YScale = (YLength-incrementY-incrementZ)/4f;
        XScale = XLength/6;
        YPoint = YLength+marginTop;
        XPoint = mWidth/9;
        circleRadiusOut = mWidth/5.68f/2f;
        circleRadius = circleRadiusOut-8f;
        centerCircleX = mWidth-mWidth/24f-circleRadiusOut;
        centerCircleY = marginTop+circleRadiusOut;
        int temp = 35;
        for (int i = 0; i < YLabel.length; i++) {
            YLabel[i] = temp * i+"°";
        }
        Random random = new Random();
        for (int i = 0; i < 16; i++){
            String str = "";
            int k = random.nextInt(60);
            if (i%2!=0) {
                if (k < 10) {
                    str = "0"+i%2+":"+"0"+k;
                } else {
                    str = "0"+i%2+":"+k;
                }
            } else {
                if (k < 10) {
                    str = "0"+i/2+":"+"0"+k;
                } else {
                    str = "0"+i/2+":"+k;
                }
            }
            XLabel.add(str);
            float number = random.nextInt(140);
            if (i == 0) {
                number = 0;
            } else if (i == 3) {
                number = 140;
            } else if (i == 4) {
                number = 132;
            }
            data.add(number);
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(lineColor);
        paint.setStrokeWidth(4);

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true); // 去锯齿
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(4);

        //文字画笔
        textYPaint.setAntiAlias(true); // 去锯齿
        textYPaint.setColor(fontColor); // 画笔颜色
        textYPaint.setTextSize(DimenUtils.sp2px(activity, 12));// 设置轴文字大小
        textYHight = (int) (Util.getHalfTextHeigth(textYPaint, "1"));

        textXPaint.setAntiAlias(true); // 去锯齿
        textXPaint.setColor(fontColor); // 画笔颜色
        textXPaint.setTextSize(DimenUtils.sp2px(activity, 10));// 设置轴文字大小
        textXHight = (int) (Util.getHalfTextHeigth(textXPaint, "0")*2);
        textXWidth = (int) (Util.getHalfTextWidth(textXPaint, "00:00"));

        textPaint.setAntiAlias(true); // 去锯齿

        //虚线笔
        strokePaint.setColor(Color.GRAY);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1);
        /*PathEffect effects = new DashPathEffect(new float[]{ 10,5 }, 1);
        strokePaint.setPathEffect(effects);*/

        //圆
        circlePaint.setAntiAlias(true);

        circleOutPaint.setAntiAlias(true);
        circleOutPaint.setStyle(Paint.Style.STROKE);
        circleOutPaint.setStrokeWidth(4);
        init(FlingType.X,data.size()*XScale-XScale*5-XPoint-(mWidth-XLength-XPoint));

        bmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bmpPaint.setStyle(Paint.Style.STROKE);
        bmpPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)(XPoint + data.size()*XScale+XScale), (int)mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint.setColor(cireleColor);
        circleOutPaint.setColor(lineColor);
        circleOutPaint.setStrokeWidth(4);
        // 画Y轴
        /*canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);*/

        // Y轴箭头
        /*canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
                + 6, paint); // 箭头
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
                + 6, paint);*/

        strokePaint.setColor(axisLineColor);
        // 添加刻度和文字
        for (int i = 0; i < 5; i++) {
            /*if (i == 2) {
                strokePaint.setColor(Color.BLUE);
            } else {
                strokePaint.setColor(Color.GRAY);
            }*/
            canvas.drawText(YLabel[i], XPoint - XPoint*3/4, YPoint - (i * YScale) - incrementZ + textYHight, textYPaint);// 文字
            // 虚线
            /*Path path = new Path();
            path.moveTo(XPoint, YPoint - ((i+1) * YScale)-incrementZ);
            path.lineTo(XPoint+data.size()*XScale,YPoint - ((i+1) * YScale)-incrementZ);
            canvas.drawPath(path, strokePaint);*/
            if (i != 4)
            canvas.drawLine(XPoint, YPoint - ((i+1) * YScale)-incrementZ, XPoint + data.size()*XScale, YPoint - ((i+1) * YScale)-incrementZ, strokePaint);

        }

        // 画Y轴
        canvas.drawLine(XPoint-2, marginTop, XPoint-2, YPoint+2, linePaint);
        // 画X轴
        canvas.drawLine(XPoint, YPoint, XPoint + data.size()*XScale, YPoint, linePaint);

        //X轴文字
        for (int i = 0, size = XLabel.size(); i < size; i++){
            canvas.drawText(XLabel.get(i), XPoint+((i+1)*XScale)-XScale/2 - textXWidth, YPoint + textXHight +20, textXPaint);// 文字
        }

        // 绘折线
        float baseX=0, baseY=0;
        float maxNumber;
        float baseYScale = YScale*4f/140f;
        /*paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAlpha(80);
        maxNumber = Collections.max(data);
        LinearGradient shader = new LinearGradient(XPoint, YPoint-baseYScale*maxNumber, XPoint,
                YPoint, new int[] { activity.getResources().getColor(cn.finalteam.mygallery.R.color.qianlv), Color.WHITE }, null,
                Shader.TileMode.CLAMP);
        paint2.setShader(shader);*/
        float no = 0;
        if (data.size() > 1) {
            Path path = new Path();
            Path path4 = new Path();
            path.moveTo(XPoint+XScale/2, YPoint - data.get(0) * baseYScale-2 - incrementZ);
            path4.moveTo(XPoint+XScale/2, YPoint-2 - incrementZ);
            /*path.lineTo(XPoint + XScale, YPoint - data.get(0) * baseYScale-2);
            path4.lineTo(XPoint, YPoint - data.get(0) * baseYScale-2);*/
            for (int i = 0; i < data.size(); i++) {
                float x = XPoint + i * XScale + XScale/2;
                float y = YPoint - data.get(i) * baseYScale-2 - incrementZ;
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

                /*if (mSelectPosition == i) {
                    canvas.drawCircle(x, y, 10, circlePaint);
                    canvas.drawCircle(x, y, 10, circleOutPaint);
                    String s = (int)no+"";
                    canvas.drawText(s, x - Util.getHalfTextWidth(textXPaint, s),
                            y - textXHight - 20, textXPaint);// 文字
                }*/

            }
//            path.lineTo(XPoint + data.size()*XScale+XScale, YPoint- data.get(data.size()-1)*baseYScale-2);
//            path4.lineTo(XPoint + data.size()*XScale+XScale, YPoint- data.get(data.size()-1)*baseYScale-2);
            path4.lineTo(XPoint+data.size()*XScale+XScale/2, YPoint-2);
            canvas.drawPath(path, paint);
//            canvas.drawPath(path4, paint2);
            isInitPositionList = true;
            for (int j = 0, sizej = positionList.size(); j < sizej; j ++) {
                canvas.drawCircle(positionList.get(j).getX(), positionList.get(j).getY(), 10, circlePaint);
                canvas.drawCircle(positionList.get(j).getX(), positionList.get(j).getY(), 10, circleOutPaint);
            }
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
        circlePaint.setColor(cireleColorBig);
        circleOutPaint.setColor(cireleColorOutBig);
        circleOutPaint.setStrokeWidth(8);
        canvas.drawCircle(centerCircleX, centerCircleY, circleRadius, circlePaint);
        canvas.drawCircle(centerCircleX, centerCircleY, circleRadius, circleOutPaint);
        textPaint.setTextSize(DimenUtils.sp2px(activity, 14));// 设置轴文字大小
        textPaint.setColor(Color.WHITE);
        float textHeight, textWidth,textHeight1, textWidth1;
        String s = "通过";
        textHeight = Util.getHalfTextHeigth(textPaint, s);
        textWidth = Util.getHalfTextWidth(textPaint, s);
        canvas.drawText(s, centerCircleX-textWidth, centerCircleY+textHeight,textPaint);
        textPaint.setTextSize(DimenUtils.sp2px(activity, 8));// 设置轴文字大小
        textPaint.setColor(resources.getColor(R.color.fontcolors5));
        String s1 = "高温测试";
        textHeight1 = Util.getHalfTextHeigth(textPaint, s1);
        textWidth1 = Util.getHalfTextWidth(textPaint, s1);
        canvas.drawText(s1, centerCircleX-textWidth1, centerCircleY-textHeight-textHeight1/2,textPaint);

        Bitmap bmp= BitmapFactory.decodeResource(resources, R.drawable.elead_right);
        int bmpWidth=bmp.getWidth()/2;
        int bmpHeight=bmp.getHeight()/2;
        canvas.drawBitmap(bmp, centerCircleX-bmpWidth, centerCircleY+textHeight+bmpHeight/2, bmpPaint);
    }
}