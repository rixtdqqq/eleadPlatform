package com.elead.card.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

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
public class EPCardTypeBarChartView extends View {

    private Activity activity;
    private float XPoint;
    private float YPoint;
    private float XScale;
    private float YScale;
    private float XLength;
    private float YLength;
    private float mWidth, mHeight;
    private float incrementY, marginTop;
    private float circleRadiusOut, circleRadius, centerCircleX, centerCircleY, pointX, pointY;
    private int textYHight, textXHight, textXWidth;
    private Paint linePaint, paint, paint2, textYPaint, textXPaint, strokePaint, circlePaint, circleOutPaint,
                        textPaint, bmpPaint, paint1;
    Resources resources;

    private List<Float> data = new ArrayList<>();
    private List<String> XLabel = new ArrayList<String>();
    private List<PositionXYEntity> positionList = new ArrayList<>();
    private boolean isInitPositionList = false;
    private int lineColor, cireleColor, bgLineColor, axisLineColor,
                cireleColorBig, cireleColorOutBig, fontColor,
                color1, color2;

    private String[] YLabel = new String[7];
    private String[] YLabelRight = new String[7];
    private String[] Label = new String[]{"设备型号 : GS9 KWGDS120", "实验温度范围 : -20°C～150°C",
            "试验湿度范围 : 25%～98%R.H","最大温度变化率 : 15°C/min"};
    private String[] Label1 = new String[]{"温度", "湿度"};

    public EPCardTypeBarChartView(Context context) {
        this(context,null);
    }

    public EPCardTypeBarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EPCardTypeBarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        positionList.clear();
        paint = new Paint();
        paint1 = new Paint();
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
        axisLineColor = resources.getColor(R.color.text_develop_tb_def);
        bgLineColor = resources.getColor(R.color.color_464848);
        cireleColorBig = resources.getColor(R.color.elead_1eafad);
        cireleColorOutBig = resources.getColor(R.color.qianlv);
        fontColor = resources.getColor(R.color.fontcolors4);
        color1 = resources.getColor(R.color.tuhuang);
        color2 = Color.parseColor("#2ec7c9");
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        mWidth = metric.widthPixels;
        mHeight = mWidth/1.93f;
        marginTop = mHeight/8f;
        incrementY = mHeight/14f;
        XLength=mWidth/2.16f;
        YLength=mHeight/1.6f;
        YScale = (YLength-incrementY)/6f;
        XScale = XLength/6;
        YPoint = YLength+marginTop;
        XPoint = mWidth/10.8f;
        circleRadiusOut = mWidth/5.68f/2f;
        circleRadius = circleRadiusOut-8f;
        centerCircleX = mWidth-mWidth/24f-circleRadiusOut;
        centerCircleY = marginTop/2+circleRadiusOut;
        int temp = 20;
        for (int i = 0; i < YLabel.length; i++) {
            YLabel[i] = temp * i+"";
            YLabelRight[i] = 20*i-20+"";
        }
        Random random = new Random();
        for (int i = 0; i < 5; i++){
            String str = i*10+"h";
            if (i == 0) {
                str = 1+"h";
            }
            XLabel.add(str);
            float number = random.nextInt(100);
            data.add(number);
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true); // 去锯齿
        linePaint.setColor(resources.getColor(R.color.fontcolors2));
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

        paint1.setAntiAlias(true); // 去锯齿

        //虚线笔
        strokePaint.setColor(Color.GRAY);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1);
        PathEffect effects = new DashPathEffect(new float[]{ 10,5 }, 1);
        strokePaint.setPathEffect(effects);

        //圆
        circlePaint.setAntiAlias(true);

        circleOutPaint.setAntiAlias(true);
        circleOutPaint.setStyle(Paint.Style.STROKE);
        circleOutPaint.setStrokeWidth(4);
        /*init(FlingType.X,data.size()*XScale-XScale*5-XPoint-(mWidth-XLength-XPoint));*/

        bmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bmpPaint.setStyle(Paint.Style.STROKE);
        bmpPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, (int)mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointX = (int)(XPoint+incrementY);
        pointY = (int)marginTop/2;
        circlePaint.setColor(cireleColor);
        circleOutPaint.setColor(lineColor);
        circleOutPaint.setStrokeWidth(4);
        strokePaint.setColor(axisLineColor);
        // 添加刻度和文字
        for (int i = 0; i < 7; i++) {
            canvas.drawText(YLabel[i], XPoint - XPoint*3/4, YPoint - (i * YScale) + textYHight, textYPaint);// 文字
            canvas.drawText(YLabelRight[i], XPoint + XPoint/10+XLength, YPoint - (i * YScale) + textYHight, textYPaint);// 文字
            // 虚线
            Path path = new Path();
            if (i != 6) {
                path.moveTo(XPoint, YPoint - ((i+1) * YScale));
                path.lineTo(XPoint+data.size()*XScale+XScale,YPoint - ((i+1) * YScale));
                canvas.drawPath(path, strokePaint);
            }
            if (i<4){
                float height = Util.getHalfTextHeigth(textXPaint, "设备型号");
                canvas.drawText(Label[i], XPoint+XPoint*4/5+XLength, centerCircleY+circleRadiusOut+incrementY + i*(height*2 + 5), textXPaint);// 文字
            }

            if (i<2) {
                paint.setStrokeWidth(incrementY/2);
                if (i == 0) {
                    paint.setColor(color1);
                } else {
                    paint.setColor(color2);
                }
                float height = Util.getHalfTextHeigth(textYPaint, "温度");
                float width = Util.getHalfTextWidth(textYPaint, "温度");
                canvas.drawPoint(pointX+i*(incrementY*2+width+incrementY), pointY, paint);
                canvas.drawText(Label1[i], pointX+incrementY+i*(width+incrementY*3), pointY+height, textYPaint);// 文字
            }

        }

        // 画Y轴
        canvas.drawLine(XPoint-2, marginTop, XPoint-2, YPoint+2, linePaint);
        // 画Y轴
        canvas.drawLine(XPoint+XLength-2, marginTop, XPoint+XLength-2, YPoint+2, linePaint);
        // 画X轴
        canvas.drawLine(XPoint, YPoint, XPoint + data.size()*XScale+XScale, YPoint, linePaint);
        float baseYScale = YScale*6f/120f;
        float baseWidth = (incrementY/2+incrementY/4);
        //X轴文字
        for (int i = 0, size = XLabel.size(); i < size; i++){
            float baseTextWidth = Util.getHalfTextWidth(textXPaint, XLabel.get(i));
            canvas.drawText(XLabel.get(i), XPoint+((i+1)*XScale) - baseTextWidth, YPoint + textXHight +20, textXPaint);// 文字
            paint1.setColor(color1);
            float baseX = XPoint+((i+1)*XScale) - baseWidth;
            float baseX2 = XPoint+((i+1)*XScale);
            float baseY = YPoint-data.get(i)*baseYScale;
            RectF f = new RectF(baseX, baseY, baseX2, YPoint-2);
            canvas.drawRect(f,paint1);
//            canvas.drawPoint(XPoint+((i+1)*(XScale-baseWidth)), YPoint-data.get(i)*baseYScale-2, paint);
            paint1.setColor(color2);
            baseY = YPoint-(data.get(i)+20)*baseYScale;
            RectF f1 = new RectF(baseX + baseWidth,
                    baseY, baseX + baseWidth*2, YPoint-2);
            canvas.drawRect(f1,paint1);
//            canvas.drawPoint(XPoint+((i+1)*XScale), YPoint-YScale-(data.get(i)-20)*baseYScale-2, paint1);
        }

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