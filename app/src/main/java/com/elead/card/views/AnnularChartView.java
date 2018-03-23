package com.elead.card.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.elead.eplatform.R;
import com.elead.utils.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 环形图
 */
public class AnnularChartView extends View {

    private List<AnnularChartData> datas = new ArrayList<>();
    private Context mContext;

    /*
     * 默认文本颜色
     */
    private int textColor = 0xff000000;

    /*
     * 默认起始绘制角度 3点方向为0，默认12点方向
     */
    private float startAngle = -90;

    private Paint paint;// 扇形画笔
    private Paint bmpPaint;
    private Paint textPaint;// 文本画笔
    private Paint textLeftTopPaint;// 文本画笔Left Top
    private Paint paintPoint;// 右边色块画笔
    private RectF rectf;

    /**
     * 矩形边长
     */
    private int rectfSize = 500;

    /*
	 * 2.突出部分圆心距离原位置的圆心
	 */
    private float distance = 20;

    private int width, height;

    String leftTopStr = "Distribution";

    private float textRightHeight, textSize, pointSpace, pointX;
    private float total;

    public AnnularChartView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub

    }

    public AnnularChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public AnnularChartView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        AnnularChartData cd0 = new AnnularChartData(Color.CYAN, 345, "Bounce",null);
        AnnularChartData cd1 = new AnnularChartData(Color.GRAY, 456, "Visitors",null);
        AnnularChartData cd2 = new AnnularChartData(Color.GREEN, 789, "Registered",null);
        AnnularChartData cd3 = new AnnularChartData(Color.BLUE, 123, "Registered",null);
        AnnularChartData cd4 = new AnnularChartData(Color.RED, 246, "OpitonB",null);
        datas.add(cd0);
        datas.add(cd1);
        datas.add(cd2);
        datas.add(cd3);
        datas.add(cd4);
        total = 0;
        for(int i=0,size=datas.size();i<size;i++){
            total += datas.get(i).getProgress();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        bmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bmpPaint.setStyle(Paint.Style.STROKE);
        bmpPaint.setColor(Color.WHITE);

        paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintPoint.setStyle(Paint.Style.STROKE);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        textLeftTopPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textLeftTopPaint.setTextSize(30);
        textLeftTopPaint.setColor(Color.BLACK);

    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setData(List<AnnularChartData> datas) {
        if(Util.isNotEmpty(datas)) {
            total = 0;
            for(int i=0,size=datas.size();i<size;i++){
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
        point.x = width / 2-width/6;
        point.y = height / 2+height/24;
        if (datas == null || datas.size() == 0) {
            return;
        }
        textRightHeight = getHalfTextHeigthTop(paintPoint,"Registered");
        textSize = height/22f;
        pointSpace = height/15f + textSize/2;
        pointX = width -width/3f;
        paintPoint.setStrokeWidth(textSize);
        textPaint.setTextSize(textSize);

        float sweepAngle = 0;
        float totalAngle = 0;
        angles = new ArrayList<Float[]>();
        for (int i = 0, size = datas.size(); i < size; i++) {
            AnnularChartData chartData = datas.get(i);
            paint.setColor(chartData.getColor());
            if (i == size-1) {
                sweepAngle = 360-totalAngle;
            } else {
                sweepAngle = 360 * chartData.getProgress()/total;
            }
            totalAngle += sweepAngle;
//			sweepAngle = 360f * chartData.getProgress()/total;

            angles.add(new Float[]{startAngle % 360, sweepAngle % 360});

            if (select >= 0 && i == select) {
                selectOval = new RectF(left, top, right, bottom);
                canvas.drawArc(selectOval, startAngle, sweepAngle, true, paint);
            } else {
                if(i != size-1) {
                    canvas.drawArc(rectf, startAngle, sweepAngle+0.5f, true, paint);
                } else {
                    canvas.drawArc(rectf, startAngle, sweepAngle, true, paint);
                }
            }

            /**
             * 绘制分割线
             */
//            canvas.drawArc(selectOval, startAngle, sweepAngle, true, bmpPaint);

            startAngle = startAngle + sweepAngle;

            float pointY = (height - datas.size() * pointSpace) / 2 + i
                    * pointSpace+height/7.6f - textSize;
            paintPoint.setColor(chartData.getColor());
            canvas.drawPoint(pointX, pointY - textRightHeight, paintPoint);
            textPaint.setColor(chartData.getColor());
            canvas.drawText(datas.get(i).getName(),
                    pointX + paintPoint.getStrokeWidth(), pointY, textPaint);
            canvas.drawText(formatFloat(chartData.getProgress()*100f/total)+"%",
                    width-width/9, pointY, textPaint);

        }

        paint.setColor(Color.WHITE);
        canvas.drawCircle(point.x, point.y, rectfSize * 1 / 3, paint);
        float textWidth1 = textPaint.measureText("Registered");
        float textWidth2 = textPaint.measureText("90000");
        float textHeight1 = getHalfTextHeigth(textPaint,"Registered");
        textPaint.setColor(Color.BLUE);
        canvas.drawText("Registered", width / 2-textWidth1/2-width/6, height / 2 - textHeight1+height/24, textPaint);
        canvas.drawText("90000", width / 2-textWidth2/2-width/6, height / 2 + textHeight1+textHeight1/2+height/24, textPaint);

        Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.refresh);
        int bmpWidth=bmp.getWidth();
        int bmpHeight=bmp.getHeight();
        int bmpX = width-width/20-bmpWidth/2;
        int bmpY = width/20-bmpHeight/2;
        canvas.drawBitmap(bmp, bmpX, bmpY, bmpPaint);

        float textHeightLeftTop = getHalfTextHeigthTop(textLeftTopPaint,leftTopStr);
        canvas.drawText(leftTopStr, width/20-textHeightLeftTop/2, width/20+textHeightLeftTop/2, textLeftTopPaint);
    }

    /**
     * 以布局中设置的宽度为准，高度数据舍弃 矩形的边长为：宽度——2*distance
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        width = measureWidth(widthMeasureSpec);
        height = width*2/3;
        // 设置宽高
        setMeasuredDimension(width, height);
        rectfSize = width/2;
        left = (width - rectfSize) / 2-width/6;
        top = (height - rectfSize) / 2+height/24;
        right = (width + rectfSize) / 2-width/6;
        bottom = (height + rectfSize) / 2+height/24;
        rectf = new RectF(left+distance, top+distance, right-distance,bottom-distance);
    }

    private float downX, downY, upX, upY,
            absx, absy, tanX, tanY, coordinateX, coordinateY;
    private ArrayList<Float[]> angles;
    private int select = -1;
    private RectF selectOval = new RectF();
    private float left, top, right, bottom;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //圆心坐标
        float centerX = rectf.centerX();
        float centerY = rectf.centerY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) { //按下
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
        } else if (event.getAction() == MotionEvent.ACTION_UP) { //手指抬起
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

    // 根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
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

    public static float getHalfTextHeigth(Paint paint,String text) {
        try {
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            return rect.height()/1.5f;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static float getHalfTextHeigthTop(Paint paint,String text) {
        try {
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            return rect.height()/2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private float formatFloat(float ft){
        int   scale  =   2;//设置位数
        int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        android.util.Log.d("mujun1","ft:"+ft);
        BigDecimal   bd  =   new  BigDecimal((double)ft);
        bd   =  bd.setScale(scale,roundingMode);
        return bd.floatValue();
    }
}
