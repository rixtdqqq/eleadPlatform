package com.elead.card.views;

/**
 * Created by Administrator on 2016/11/11 0011.
 * 运营统计页环形图
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.elead.card.mode.JerryChartInfo;
import com.elead.utils.MyTextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼状统计图
 *
 * @author gly
 * @since 2016年10月22日
 */
public class JerryChartView_statistical extends BaseView {

    private static final String TAG = "JerryChartView_statistical";
    private final float density;
    private List<JerryChartInfo> datas;
    private float rightOffset;
    private float rightOffset2;

    public enum ChartStyle {
        /**
         * 扇形
         */
        FANSHAPE(0),
        /**
         * 环形
         */
        ANNULAR(1);
        private int style;

        private ChartStyle(int style) {
            this.style = style;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

    }

    private float topTextSize = 30;
    private int lineWidth = 20;

    /*
     * 默认起始绘制角度 3点方向为0，默认12点方向
     */
    private float startAngle = 180;
    /*
     * 默认饼状图样式——扇形
     */
    private ChartStyle chartStyle = ChartStyle.FANSHAPE;
    /*
     * 1.突出部分距离间隔线的垂直距离
     */
    // private int distance = 30;
    /*
     * 2.突出部分圆心距离原位置的圆心
	 */
    private float distance = 30;

    private Paint paint;// 扇形画笔
    private Paint topPaint;// 文本画笔
    private Paint bottomPaint;// 文本画笔
    private Paint selectPaint;// 点击画笔
    private Paint rightPaint;// 右侧画笔
    private RectF rectMin, rectMax;
    /**
     * 矩形边长
     */
    private float rectfSize, rectfSizeFloat;

    private float mWidth, mHeight;
    private float marginL;
    private float pointSpace;
    private float bottomTextSize;

    public JerryChartView_statistical(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub

    }

    public JerryChartView_statistical(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public JerryChartView_statistical(Context context, AttributeSet attrs,
                                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        density = getResources().getDisplayMetrics().density;
        distance = distance * density;
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(Color.parseColor("#40000000"));

        topPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        topPaint.setTextAlign(Paint.Align.CENTER);
        topPaint.setColor(Color.WHITE);

        bottomPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        bottomPaint.setTextAlign(Paint.Align.CENTER);
        bottomPaint.setColor(Color.parseColor("#7e8e9f"));

        rightPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        rightPaint.setTextAlign(Paint.Align.LEFT);
        rightPaint.setColor(Color.parseColor("#78909c"));

        // textPaint.setColor(Color.parseColor("#7d949f"));

    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setData(List<JerryChartInfo> datas) {
        this.setData(datas, null);
    }

    /**
     * 设置数据
     *
     * @param datas
     * @param chartStyle 扇形or环形 ChartStyle.FANSHAPE|ChartStyle.ANNULAR
     */
    public void setData(List<JerryChartInfo> datas, ChartStyle chartStyle) {
        if (datas == null || datas.size() == 0) {
            Log.e("TAG", "datas is null or datas.size()==0");
            return;
        }
        this.datas = datas;
        if (chartStyle != null) {
            this.chartStyle = chartStyle;
        }
        invalidate();
    }

    private ArrayList<Float[]> angles;
    private int downX;
    private int downY;
    private float absx;
    private float absy;
    private float tanX;
    private float tanY;
    private float coordinateX;
    private float coordinateY;
    private int upX;
    private int upY;
    private int selectIndex = -1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float centerX = rectMin.centerX();
        float centerY = rectMin.centerY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下时候的x，y
                downX = (int) event.getX();
                downY = (int) event.getY();
                // 计算按下的x,y与坐标中点的距离
                absx = Math.abs(downX - centerX);
                absy = Math.abs(downY - centerY);
                // 用于计算正切值
                tanX = Math.abs(downX - centerX);
                tanY = Math.abs(downY - centerY);
                // 按下时x,y相对于中心点的坐标
                coordinateX = downX - centerX;
                coordinateY = centerY - downY;
                handler.removeMessages(100);
                break;
            case MotionEvent.ACTION_UP:
                // 抬起来时候的x,y
                upX = (int) event.getX();
                upY = (int) event.getY();
                //
                boolean t = ((upX - downX) * (upX - downX) + (upY - downY)
                        * (upY - downY)) < 1000;
                float a = (float) Math.sqrt(absx * absx + absy * absy);
                float clickOffest = mHeight / 15; // density *20;
                if (a < rectMax.width() / 2 + clickOffest && t
                        && a > rectfSize / 3 - clickOffest) {
                    double jiaodu = getAngle(tanX, tanY, coordinateX, coordinateY);
                    for (Float[] afloat : angles) {
                        float startAngle = afloat[0];
                        float endAngle = (afloat[1] + afloat[0]);

                        boolean isd = false;
                        if (endAngle % 360 < startAngle % 360) {
                            if (jiaodu < endAngle % 360 && jiaodu > 0) {
                                isd = true;
                            }
                        }
                        if (jiaodu > startAngle && jiaodu < endAngle || isd) {
                            selectIndex = angles.indexOf(afloat);
                            invalidate();
                            break;
                        }

                        System.out.println("jiaodu: " + jiaodu + "---startAngle"
                                + startAngle + "----endAngle" + endAngle);
                    }
                }
                handler.sendEmptyMessageDelayed(100, 2000);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    selectIndex = -1;
                    invalidate();
                    break;

                default:
                    break;
            }
        }

        ;
    };

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

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (datas == null || datas.size() == 0) {
            Log.e("TAG", "datas is null or datas.size()==0");
            return;
        }
        float sweepAngle = 0;

        float totalValue = 0;
        for (JerryChartInfo data : datas) {
            float value = data.getProgress();
            totalValue += value;
        }
        angles = new ArrayList<Float[]>();
        for (int i = 0; i < datas.size(); i++) {
            JerryChartInfo chartData = datas.get(i);

            paint.setColor(chartData.getColor());
            sweepAngle = 360 * chartData.getProgress() / totalValue;
            angles.add(new Float[]{startAngle % 360, sweepAngle % 360});
            float value = (startAngle + sweepAngle / 2) % 360;
            if (value <= 0) {
                value = value + 360;
            }
            /**
             * 1.见distance注释
             */
            // float a = (float) (distance /
            // (Math.abs(Math.sin(Math.toRadians(sweepAngle / 2)))));

            float a = distance;

            /**
             * 只有扇形的情况下才支持突出显示
             */
            // float spiltLineLength = 0;
            if (i == selectIndex) {
                // spiltLineLength = rectfSize;
                if (chartStyle == ChartStyle.FANSHAPE) {
                    canvas.drawArc(rectMax, startAngle, sweepAngle, true, paint);
                    /**
                     * 待定 文本位置
                     */
                    // canvas.drawText(text, textX, textY, textPaint);
                } else {
                    canvas.drawArc(rectMax, startAngle, sweepAngle, true, paint);
                    // canvas.drawText(text, textX, textY, textPaint);
                }
            } else {
                // spiltLineLength = rectfSizeFloat;
                canvas.drawArc(rectMin, startAngle, sweepAngle, true, paint);
                // canvas.drawText(text, textX, textY, textPaint);
            }

            startAngle = startAngle + sweepAngle;

            float pointY = (rectfSize - (datas.size() - 1) * pointSpace) / 2 + i
                    * pointSpace + (mHeight - rectfSize) / 2;

            canvas.drawPoint(rightOffset, pointY - bottomTextSize / 3, paint);
            canvas.drawText(datas.get(i).getText(), rightOffset + bottomTextSize, pointY,
                    rightPaint);
            canvas.drawText((int) datas.get(i).getProgress() + "", rightOffset + bottomTextSize
                    + rightOffset2, pointY, rightPaint);
        }
        switch (chartStyle) {
            case FANSHAPE:// 扇形

                break;
            case ANNULAR:// 环形
                paint.setColor(Color.parseColor("#20000000"));
                canvas.drawCircle(marginL, mHeight / 2, rectfSize / 2.67f, paint);
                paint.setColor(Color.parseColor("#1b2133"));
                canvas.drawCircle(marginL, mHeight / 2, rectfSize / 3.2f, paint);
                break;
            default:
                break;
        }
        topPaint.setColor(Color.WHITE);
        canvas.drawText(MyTextUtils.getSplitByPointText((int) totalValue), marginL, mHeight / 2 - 5
                * density, topPaint);
        canvas.drawText("人天总数", marginL, mHeight / 2 + bottomTextSize,
                bottomPaint);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        float ph = h / 22f;
        marginL = w / 38f * 10.1f;
        // 设置宽高
        pointSpace = ph * 2.8f;
        paint.setStrokeWidth(ph * 0.78f);
        bottomTextSize = ph * 1.27f;
        distance = ph * 0.8f;
        topTextSize = ph * 2.3f;

        topPaint.setTextSize(topTextSize);
        bottomPaint.setTextSize(bottomTextSize);
        rightPaint.setTextSize(bottomTextSize);
        rightOffset = mWidth / 38f * 24.73f;
        rightOffset2 = mWidth / 38f * 7.09f;

        rectfSize = ph * 16.23f;
        rectfSizeFloat = ph * 18f;

        rectMin = new RectF(marginL - (rectfSize / 2), mHeight / 2 - rectfSize
                / 2, marginL + rectfSize / 2, mHeight / 2 + rectfSize / 2);
        rectMax = new RectF(marginL - rectfSizeFloat / 2, mHeight / 2
                - rectfSizeFloat / 2, marginL + rectfSizeFloat / 2, mHeight / 2
                + rectfSizeFloat / 2);
    }

    /**
     * 以布局中设置的宽度为准，高度数据舍弃 矩形的边长为：宽度——2*distance
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        float childWidthSize = getMeasuredWidth();
        float childHeightSize = childWidthSize / 38f * 22f;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    // // 根据xml的设定获取宽度
    // private int measureWidth(int measureSpec) {
    // int specMode = MeasureSpec.getMode(measureSpec);
    // int specSize = MeasureSpec.getSize(measureSpec);
    // // wrap_content
    // if (specMode == MeasureSpec.AT_MOST) {
    //
    // }
    // // fill_parent或者精确值
    // else if (specMode == MeasureSpec.EXACTLY) {
    //
    // }
    // return specSize;
    // }
    //
    // // 根据xml的设定获取高度
    // private int measureHeight(int measureSpec) {
    // int specMode = MeasureSpec.getMode(measureSpec);
    // int specSize = MeasureSpec.getSize(measureSpec);
    // // wrap_content
    // if (specMode == MeasureSpec.AT_MOST) {
    //
    // }
    // // fill_parent或者精确值
    // else if (specMode == MeasureSpec.EXACTLY) {
    //
    // }
    // return specSize;
    // }
}
