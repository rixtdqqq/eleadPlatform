package com.elead.card.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.elead.card.mode.JerryChartInfo;
import com.elead.eplatform.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 饼状统计图
 * 
 * @author gly
 * @since 2016年10月22日
 */
public class JerryChartView extends BaseView {

	private static final String TAG = "JerryChartView";
	private List<JerryChartInfo> datas;

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

	/*
	 * 默认文本大小
	 */
	private float textSize = 24;

	/*
	 * 默认间隔线颜色
	 */
	private int lineColor = 0xffffffff;
	/*
	 * 默认间隔线宽度
	 */
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
	private Paint linePaint;// 分割线画笔，圆画笔
	private Paint textPaint;// 文本画笔
	private Paint topPaint;// 文本画笔
	private Paint bottomPaint;// 文本画笔
	private Paint selectPaint;// 点击画笔
	private RectF rectMin, rectMax;
	/**
	 * 矩形边长
	 */
	private float rectfSize, rectfSizeFloat;

	private float mWidth, mHeight;
	private float marginL;
	private float pointSpace;
	private float bottomTextHeight;

	public JerryChartView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub

	}

	public JerryChartView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public JerryChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.JerryChartView);
		lineColor = a.getColor(R.styleable.JerryChartView_lineColor, lineColor);
		lineWidth = a.getDimensionPixelOffset(
				R.styleable.JerryChartView_lineWidth, lineWidth);
		startAngle = a.getFloat(R.styleable.JerryChartView_startAngle,
				startAngle);
		textSize = a.getDimensionPixelSize(
				R.styleable.JerryChartView_android_textSize, (int) textSize);
		int cs = a.getInt(R.styleable.JerryChartView_chartStyle, 0);
		if (cs == 0) {
			chartStyle = ChartStyle.FANSHAPE;
		} else {
			chartStyle = ChartStyle.ANNULAR;
		}
		distance = 50;
		a.recycle();
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

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setColor(lineColor);
		linePaint.setStrokeWidth(lineWidth);
		linePaint.setStyle(Paint.Style.STROKE);

		topPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		topPaint.setTextAlign(Align.CENTER);

		bottomPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		bottomPaint.setTextAlign(Align.CENTER);
		bottomPaint.setColor(Color.parseColor("#2f3b4c"));

		textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
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
	 * @param chartStyle
	 *            扇形or环形 ChartStyle.FANSHAPE|ChartStyle.ANNULAR
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
	private int selectIndex = 0;

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
		/**
		 * 文本位置，主要区分扇形、环形；至于扇形突出部分文本位置待定 扇形：文本应位于扇形中间 环形：文本应位于环形中间
		 */
		float textLocation = 0f;
		switch (chartStyle) {
		case FANSHAPE:// 扇形
			textLocation = rectfSize * 7f / 24f;
			break;
		case ANNULAR:// 环形
			textLocation = rectfSize * 5f / 12f;
			break;
		default:
			break;
		}
		Log.e(TAG, "textLocation=" + textLocation);
		float sweepAngle = 0;

		float maxValue = 0;
		float minValue = Integer.MAX_VALUE;
		float totalValue = 0;
		JerryChartInfo maxData = null;
		JerryChartInfo minData = null;
		for (JerryChartInfo data : datas) {
			float value = data.getProgress();
			totalValue += value;
			if (value > maxValue) {
				maxValue = value;
				maxData = data;
			}
			if (value < minValue) {
				minValue = value;
				minData = data;
			}
		}
		angles = new ArrayList<Float[]>();
		for (int i = 0; i < datas.size(); i++) {
			JerryChartInfo chartData = datas.get(i);

			paint.setColor(chartData.getColor());
			sweepAngle = 360 * chartData.getProgress() / totalValue;
			angles.add(new Float[] { startAngle % 360, sweepAngle % 360 });
			RectF rectf2 = null;
			float value = (startAngle + sweepAngle / 2) % 360;
			if (value <= 0) {
				value = value + 360;
			}
			float x = (float) (Math.abs(Math.cos(Math.toRadians(value))));
			float y = (float) (Math.abs(Math.sin(Math.toRadians(value))));

			/**
			 * 1.见distance注释
			 */
			// float a = (float) (distance /
			// (Math.abs(Math.sin(Math.toRadians(sweepAngle / 2)))));

			float a = distance;

			/**
			 * 若扇形突出，绘制扇形时所需矩形的位置left，top，right，bottom
			 */
			float left = 0;
			float top = 0;
			float right = 0;
			float bottom = 0;

			/**
			 * 文本位置，textX,textY
			 */
			// float textX = 0;
			// float textY = 0;

			/**
			 * 文本 ，文本宽度
			 */
			// String text = (int) (chartData.getProgress() * 100) + "%";

			// float textWidth = textPaint.measureText(text);

			if (value > 0 && value <= 90) {
				left = (mHeight - rectfSize) / 2 + marginL + x * a;
				top = (mHeight - rectfSize) / 2 + y * a;
				right = (mHeight + rectfSize) / 2 + marginL + x * a;
				bottom = (mHeight + rectfSize) / 2 + y * a;

				// textX = mHeight / 2 + marginL + x * textLocation - textWidth
				// / 2;
				// textY = mHeight / 2 + y * textLocation + textSize / 2;
			} else if (value > 90 && value <= 180) {
				left = (mHeight - rectfSize) / 2 + marginL - x * a;
				top = (mHeight - rectfSize) / 2 + y * a;
				right = (mHeight + rectfSize) / 2 + marginL - x * a;
				bottom = (mHeight + rectfSize) / 2 + y * a;

				// textX = mHeight / 2 + marginL - x * textLocation - textWidth
				// / 2;
				// textY = mHeight / 2 + y * textLocation + textSize / 2;
			} else if (value > 180 && value <= 270) {
				left = (mHeight - rectfSize) / 2 + marginL - x * a;
				top = (mHeight - rectfSize) / 2 - y * a;
				right = (mHeight + rectfSize) / 2 + marginL - x * a;
				bottom = (mHeight + rectfSize) / 2 - y * a;

				// textX = mHeight / 2 + marginL - x * textLocation - textWidth
				// / 2;
				// textY = mHeight / 2 - y * textLocation + textSize / 2;
			} else if (value > 270 && value <= 360) {
				left = (mHeight - rectfSize) / 2 + marginL + x * a;
				top = (mHeight - rectfSize) / 2 - y * a;
				right = (mHeight + rectfSize) / 2 + marginL + x * a;
				bottom = (mHeight + rectfSize) / 2 - y * a;

				// textX = mHeight / 2 + marginL + x * textLocation - textWidth
				// / 2;
				// textY = mHeight / 2 - y * textLocation + textSize / 2;
			}
			textPaint.setColor(chartData.getTextColor());
			/**
			 * 只有扇形的情况下才支持突出显示
			 */
			// float spiltLineLength = 0;
			if (i == selectIndex) {
				// spiltLineLength = rectfSize;
				if (chartStyle == ChartStyle.FANSHAPE) {
					rectf2 = new RectF(left, top, right, bottom);
					canvas.drawArc(rectf2, startAngle, sweepAngle, true, paint);

					/**
					 * 待定 文本位置
					 */
					// canvas.drawText(text, textX, textY, textPaint);
				} else {
					RectF rectf4 = new RectF((mHeight - rectfSizeFloat) / 2
							+ marginL, (mHeight - rectfSizeFloat) / 2,
							(mHeight + rectfSizeFloat) / 2 + marginL,
							(mHeight + rectfSizeFloat) / 2);
					canvas.drawArc(rectf4, startAngle, sweepAngle, true, paint);
					// canvas.drawText(text, textX, textY, textPaint);
				}
			} else {
				// spiltLineLength = rectfSizeFloat;
				canvas.drawArc(rectMin, startAngle, sweepAngle, true, paint);
				// canvas.drawText(text, textX, textY, textPaint);
			}

			/**
			 * 绘制分割线
			 */
			canvas.drawArc(rectMax, startAngle, sweepAngle, true, linePaint);

			//
			// float angle = startAngle;
			//
			// float endX = (float) Math.cos(Math.toRadians(angle)) *
			// spiltLineLength
			// / 2f;
			// float endY = (float) Math.sin(Math.toRadians(angle)) *
			// spiltLineLength
			// / 2f;
			// canvas.drawLine(rectMax.centerX(),
			// rectMax.centerY(),rectMax.centerX()+ endX,rectMax.centerY()+
			// endY,
			// linePaint);
			//
			// float angle2 =startAngle+ sweepAngle;
			//
			// float endX2 = (float) Math.cos(Math.toRadians(angle2)) *
			// spiltLineLength
			// / 2f;
			// float endY2 = (float) Math.sin(Math.toRadians(angle2)) *
			// spiltLineLength
			// / 2f;
			// canvas.drawLine(rectMax.centerX(),
			// rectMax.centerY(),rectMax.centerX()+ endX2,rectMax.centerY()+
			// endY2,
			// linePaint);

			startAngle = startAngle + sweepAngle;

			float pointY = (mHeight - datas.size() * pointSpace) / 2 + i
					* pointSpace;
			canvas.drawPoint(mWidth - marginL, pointY - textSize / 3, paint);
			textPaint.setColor(chartData.getColor());
			canvas.drawText(datas.get(i).getText(),
					mWidth - marginL + textSize, pointY, textPaint);
		}
		switch (chartStyle) {
		case FANSHAPE:// 扇形

			break;
		case ANNULAR:// 环形
			paint.setColor(lineColor);
			canvas.drawCircle(mHeight / 2 + marginL, mHeight / 2,
					rectfSize * 1 / 3, paint);
			break;
		default:
			break;
		}
		topPaint.setColor(maxData.getColor());
		canvas.drawText(maxData.getText(), mHeight / 2 + marginL, mHeight / 2
				- textSize / 2, topPaint);
		canvas.drawText(String.valueOf(new DecimalFormat("0.0").format(maxData
				.getProgress())), mHeight / 2 + marginL, mHeight / 2
				+ bottomTextHeight, bottomPaint);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
		float ph = h / 15.56f;
		marginL = w / 38f * 7.66f;
		// 设置宽高
		pointSpace = ph * 2.8f;
		paint.setStrokeWidth(ph * 0.78f);
		bottomTextHeight = ph * 1.62f;
		distance = ph * 0.8f;
		textSize = ph * 0.88f;

		topPaint.setTextSize(textSize);
		textPaint.setTextSize(textSize);
		bottomPaint.setTextSize(bottomTextHeight);

		rectfSize = mHeight - distance * 2;
		rectfSizeFloat = mHeight;
		rectMin = new RectF((mHeight - rectfSize) / 2 + marginL,
				(mHeight - rectfSize) / 2, (mHeight + rectfSize) / 2 + marginL,
				(mHeight + rectfSize) / 2);
		rectMax = new RectF(marginL, 0, mHeight + marginL, mHeight);
	}

	/**
	 * 以布局中设置的宽度为准，高度数据舍弃 矩形的边长为：宽度——2*distance
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		float childWidthSize = getMeasuredWidth();
		float childHeightSize = childWidthSize / 38f * 15.56f;
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
