package com.elead.card.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.elead.card.entity.AnnularChartData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * @desc 环形图带指示线
 * @auth Created by Administrator on 2016/11/4 0004. edit bu gly on 2016/11/8
 */

public class AnnularIndicatorLineChart extends View {

    private List<AnnularChartData> datas = new ArrayList<AnnularChartData>();
    private Context mContext;

	/*
     * 默认起始绘制角度 3点方向为0，默认12点方向
	 */

    private Paint paint;// 扇形画笔
    private Paint textPaint;// 文本画笔
    private RectF rectf;
    private static final String UNIT = "条";
    private RectF preRects;
    /**
     * 矩形边长
     */
    private int rectfSize = 500;

    /*
     * 2.突出部分圆心距离原位置的圆心
     */
    private float distance = 20;

    private int width, height;

    private float total;
    private float density;

    public AnnularIndicatorLineChart(Context context) {
        this(context, null);
    }

    public AnnularIndicatorLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnnularIndicatorLineChart(Context context, AttributeSet attrs,
                                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;
        density = mContext.getResources().getDisplayMetrics().density;

        List<AnnularChartData> annularChartDatas = new ArrayList<AnnularChartData>();
        AnnularChartData cd0 = new AnnularChartData(
                Color.parseColor("#37d1d3"), 1, "Bounce", "异常>30min");
        AnnularChartData cd1 = new AnnularChartData(
                Color.parseColor("#fddf57"), 1, "Bounce", "异常>30min");
        AnnularChartData cd2 = new AnnularChartData(
                Color.parseColor("#6fe2b1"), 7, "Bounce", "异常>30min");
        AnnularChartData cd3 = new AnnularChartData(
                Color.parseColor("#aa9075"), 8, "Bounce", "异常>30min");
        AnnularChartData cd4 = new AnnularChartData(
                Color.parseColor("#fddf57"), 9, "Bounce", "异常>30min");
        AnnularChartData cd5 = new AnnularChartData(
                Color.parseColor("#fba667"), 99, "Visitors", "异常<30min");
        AnnularChartData cd6 = new AnnularChartData(
                Color.parseColor("#aa9075"), 22, "Registered", "加班>2h");
        AnnularChartData cd7 = new AnnularChartData(
                Color.parseColor("#6fe2b1"), 44, "Registered", "忘记打卡");
        AnnularChartData cd8 = new AnnularChartData(
                Color.parseColor("#37d1d3"), 93, "OpitonB", "加班<2h");
        annularChartDatas.add(cd0);
        annularChartDatas.add(cd1);
        annularChartDatas.add(cd2);
        annularChartDatas.add(cd3);
        annularChartDatas.add(cd4);
        annularChartDatas.add(cd5);
        annularChartDatas.add(cd6);
        annularChartDatas.add(cd7);
        Collections.sort(annularChartDatas);
        for (int i = 0; i < annularChartDatas.size(); i++) {
            AnnularChartData annularChartData = annularChartDatas
                    .get(annularChartDatas.size() / 2);
            datas.add(annularChartData);
            annularChartDatas.remove(annularChartData);
            i--;
        }

        total = 0;
        for (int i = 0, size = datas.size(); i < size; i++) {
            total += datas.get(i).getProgress();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(density * 1);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    }

    /**
     * 设置数据
     */
    public static boolean isNotEmpty(Object entity) {
        boolean empty = false;
        if (entity instanceof Collection) {
            if (null != entity && ((Collection<?>) entity).size() > 0) {
                return true;
            }
        }
        return empty;
    }

    public void setData(List<AnnularChartData> datas) {
        if (isNotEmpty(datas)) {
            total = 0;
            for (int i = 0, size = datas.size(); i < size; i++) {
                total += datas.get(i).getProgress();
            }
            this.datas.clear();
            this.datas.addAll(datas);
            invalidate();
        }
    }

    private Point point = new Point();

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        float startAngle = 45;
        point.x = width / 2;
        point.y = height / 2;
        if (datas == null || datas.size() == 0) {
            return;
        }
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        float addChargeRect = 0;
        float sweepAngle = 0;
        float totalAngle = 0;
        angles = new ArrayList<Float[]>();
        for (int i = 0, size = datas.size(); i < size; i++) {
            AnnularChartData chartData = datas.get(i);
            paint.setColor(chartData.getColor());
            if (i == size - 1) {
                sweepAngle = 360 - totalAngle;
            } else {
                sweepAngle = 360 * chartData.getProgress() / total;
            }
            totalAngle += sweepAngle;
            // sweepAngle = 360f * chartData.getProgress()/total;

            angles.add(new Float[]{startAngle % 360, sweepAngle % 360});

            if (select >= 0 && i == select) {
                canvas.drawArc(selectOval, startAngle, sweepAngle, true, paint);
            } else {
                if (i != size - 1) {
                    canvas.drawArc(rectf, startAngle, sweepAngle + 0.5f, true,
                            paint);
                } else {
                    canvas.drawArc(rectf, startAngle, sweepAngle, true, paint);
                }
            }

            // 画圆上提示
            float r2 = rectf.width() / 2f + 5 * density;
            float r1 = rectf.width() / 1.6f;
            float angle = ((startAngle + sweepAngle + startAngle) / 2) % 360;
            if (angle <= 0) {
                angle = angle + 360;
            }
            float arcCenterX = rectf.centerX();
            float arcCenterY = rectf.centerY();

            float startX = (float) (arcCenterX + r2
                    * Math.cos(angle * 3.14f / 180f));
            float startY = (float) (arcCenterY + r2
                    * Math.sin(angle * 3.14f / 180f));

            float centerX = (float) (arcCenterX + r1
                    * Math.cos(angle * 3.14f / 180f));
            float centerY = (float) (arcCenterY + r1
                    * Math.sin(angle * 3.14f / 180f));

            float endX = 0;
            float lineWidth = 120;
            RectF tipRect = null;
            int topTextSize = 35;
            int botTextSize = 30;

            float x = 0;
            if (angle > 270 && angle <= 360) {
                one++;
                endX = centerX + lineWidth;

                x = startX;

            } else if (angle >= 0 && angle <= 90) {
                two++;
                endX = centerX + lineWidth;
                x = startX;
            } else if (angle > 90 && angle <= 180) {
                three++;
                endX = centerX - lineWidth;
                x = endX;
                tipRect = new RectF(endX, centerY - topTextSize, startX,
                        centerY + botTextSize);
            } else if (angle > 180 && angle <= 270) {
                four++;
                endX = centerX - lineWidth;
                x = endX;
            }
            tipRect = new RectF(x, centerY - topTextSize, endX, centerY
                    + botTextSize);
            if (null != preRects) {
                float chargeRect = chargeRect(tipRect, preRects, addChargeRect);
                if (chargeRect != 0) {
                    addChargeRect += chargeRect;
                    tipRect.offset(0, addChargeRect);
                    centerY = tipRect.centerY();
                }
            }
            System.out.println("addChargeRect: " + addChargeRect);
            preRects = tipRect;
            // canvas.drawRect(tipRect, paint);
            canvas.drawLine(startX, startY, centerX, centerY, paint);
            canvas.drawLine(centerX, centerY, endX, centerY, paint);
            canvas.drawCircle(startX, startY, 2.5f * density, paint);
            float textHeight, textWidth1, textWidth2, textWidth3;
            if (endX < point.x) {
                String text = (int) chartData.getProgress() + "";
                textPaint.setColor(Color.BLACK);
                textPaint.setTextSize(35);
                textWidth1 = textPaint.measureText(text);
                textHeight = getHalfTextHeigth(textPaint, text);
                canvas.drawText(text, endX, centerY - textHeight, textPaint);

                textPaint.setTextSize(25);
                textHeight = getHalfTextHeigth(textPaint, UNIT);
                canvas.drawText(UNIT, endX + textWidth1, centerY - textHeight,
                        textPaint);

            } else {
                textPaint.setTextSize(25);
                textPaint.setColor(Color.BLACK);
                textWidth2 = textPaint.measureText(UNIT);
                textHeight = getHalfTextHeigth(textPaint, UNIT);
                canvas.drawText(UNIT, endX - textWidth2, centerY - textHeight,
                        textPaint);

                String text = (int) chartData.getProgress() + "";
                textPaint.setTextSize(35);
                textWidth1 = textPaint.measureText(text);
                textHeight = getHalfTextHeigth(textPaint, text);
                canvas.drawText(text, endX - textWidth1 - textWidth2, centerY
                        - textHeight, textPaint);
            }

            String description = chartData.getDescription();
            textPaint.setColor(Color.GRAY);
            textPaint.setTextSize(20);
            textWidth3 = textPaint.measureText(description);
            textHeight = getHalfTextHeigth(textPaint, description);
            canvas.drawText(description, endX < point.x ? endX : endX
                    - textWidth3, centerY + textHeight * 2 + 5, textPaint);

            /**
             * 绘制分割线
             */
            // canvas.drawArc(selectOval, startAngle, sweepAngle, true,
            // bmpPaint);

            startAngle = startAngle + sweepAngle;

        }
        paint.setColor(Color.WHITE);
        canvas.drawCircle(point.x, point.y, rectfSize / 1.84f / 2, paint);
    }

    private float chargeRect(RectF rectF1, RectF rectF2, float addChargeRect) {
        float chaHeight = rectF1.height();
        float chaCenterY = rectF1.centerY() - rectF2.centerY();

        float chaY = 0;
        // if ( ) {
        // chaY = chaHeight - Math.abs(chaCenterY);
        // } else {
        chaY = chaHeight - chaCenterY;
        // }

        float chaWidth = (rectF1.width() + rectF1.width()) / 2f;
        float chaCenterX = Math.abs(rectF1.centerX() - rectF2.centerX());
        float chaX = chaWidth - chaCenterX;

        return (chaX > 0 && chaY > 0) ? Math.abs(chaY) : 0;
    }

    /**
     * 以布局中设置的宽度为准，高度数据舍弃 矩形的边长为：宽度——2*distance
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        width = measureWidth(widthMeasureSpec);
        height = width;
        // 设置宽高
        setMeasuredDimension(width, height);
        rectfSize = width / 2;
        left = (width - rectfSize) / 2;
        top = (height - rectfSize) / 2;
        right = (width + rectfSize) / 2;
        bottom = (height + rectfSize) / 2;
        rectf = new RectF(left + distance, top + distance, right - distance,
                bottom - distance);
        selectOval = new RectF(left, top, right, bottom);
    }

    private float downX, downY, upX, upY, absx, absy, tanX, tanY, coordinateX,
            coordinateY;
    private ArrayList<Float[]> angles;
    private int select = -1;
    private RectF selectOval = new RectF();
    private float left, top, right, bottom;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // 圆心坐标
        float centerX = rectf.centerX();
        float centerY = rectf.centerY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) { // 按下
            downX = event.getX();
            downY = event.getY();
            // 计算按下的x,y与坐标中点的距离
            absx = Math.abs(downX - centerX);
            absy = Math.abs(downY - centerY);
            // 用于计算正切值
            tanX = Math.abs(downX - centerX);
            tanY = Math.abs(downY - centerY);
            // 按下时x,y相对于中心点的坐标
            coordinateX = downX - centerX;
            coordinateY = centerY - downY;
        } else if (event.getAction() == MotionEvent.ACTION_UP) { // 手指抬起
            upX = event.getX();
            upY = event.getY();
            boolean t = ((upX - downX) * (upX - downX) + (upY - downY)
                    * (upY - downY)) < 1000;
            float a = (float) Math.sqrt(absx * absx + absy * absy);
            float clickOffest = height / 15; // density *20;
            if (a < rectf.width() / 2 + clickOffest && t
                    && a > rectfSize / 3 - clickOffest) {
                double jiaodu = getAngle(tanX, tanY, coordinateX, coordinateY);
                for (Float[] afloat : angles) {
                    float startAngle = afloat[0];
                    float endAngle = (afloat[1] + afloat[0]);
                    if (startAngle < 0) {
                        startAngle += 360;
                    }
                    if (endAngle < 0) {
                        endAngle += 360;
                    }
                    boolean isd = false;
                    if (endAngle % 360 < startAngle % 360) {
                        if (jiaodu < endAngle % 360 && jiaodu > 0) {
                            isd = true;
                        }
                    }
                    if (jiaodu > startAngle && jiaodu < endAngle || isd) {
                        select = angles.indexOf(afloat);
                        invalidate();
                        break;
                    }

                }
            }
        }

        return true;
    }

    protected double getAngle(float x, float y, float downX, float downY) {
        double angle = (float) 0.0;
        if (downX > 0 && downY < 0) {
            double c = (double) y / (double) x;
            double d = Math.toDegrees(Math.atan(c));
            angle = d;
        } else if (downX <= 0 && downY <= 0) {
            double c = (double) x / (double) y;
            double d = Math.toDegrees(Math.atan(c));
            angle = d + 90;
        } else if (downX < 0 && downY >= 0) {
            double c = (double) y / (double) x;
            double d = Math.toDegrees(Math.atan(c));
            angle = 180 + d;
        } else {
            double c = (double) x / (double) y;
            double d = Math.toDegrees(Math.atan(c));
            angle = d + 270;
        }
        return angle;
    }

    // 根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // wrap_content
        if (specMode == MeasureSpec.AT_MOST) {

        }
        // fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {

        }
        return specSize;
    }

    public static float getHalfTextHeigth(Paint paint, String text) {
        try {
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            return rect.height() / 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
