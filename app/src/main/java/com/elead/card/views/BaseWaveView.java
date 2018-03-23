package com.elead.card.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.elead.card.mode.WaveDate;
import com.elead.card.mode.WaveDate.WaveInfo;
import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 波浪统计图
 *
 * @author gly
 * @since 2016年10月22日
 */
@SuppressLint("ViewConstructor")
public class BaseWaveView extends BaseFlingView {

	public float rectSize = 100;

	public static enum Mstyle {
		Line, Curve
	}

	private float mHeight, mWidth;
	private Mstyle mstyle = Mstyle.Line;
	private int curShowWave = 0;
	private List<List<PointF>> allPoints = new ArrayList<List<PointF>>();
	private List<int[]> colors = new ArrayList<int[]>();
	// private List<PointF> mFrontPoints = new ArrayList<PointF>();
	// private List<PointF> mBehPoints = new ArrayList<PointF>();

	private int pointNum = 6;
	private Context mContext;
	private float chartHeight = 0;
	private float marginb;
	private List<List<WaveInfo>> mChartInfos = new ArrayList<List<WaveInfo>>();
	private List<List<String>> bottomText = new ArrayList<List<String>>();

	private Paint wavePaint, linePaint, pointPaint, tTextP, bTextP, dotLineP;
	private float margint;
	private float piontRadiu;
	private float lineWidth;

	private onTouchLinstener mListener;

	private Bitmap bClickTip;
	private Matrix mMatTip;
	private int mSelectPosition = -1;

	private float max = 0;

	private float sWidth;
	private float tTextH;
	private float bTextH;
	private float bTipHeight;

	public void init(WaveDate waveDate) {
		this.mChartInfos = waveDate.getWaveInfos();
		this.bottomText = waveDate.getBottomTexts();
		this.colors = waveDate.getColor();
	}

	public BaseWaveView(Context ct) {
		this(ct, null);
	}

	public BaseWaveView(Context ct, AttributeSet attrs) {
		this(ct, attrs, 0);
	}

	@SuppressLint("NewApi")
	public BaseWaveView(Context ct, AttributeSet attrs, int defStyle) {
		super(ct, attrs, defStyle);
		this.mContext = ct;

		this.setLayerType(View.LAYER_TYPE_SOFTWARE, wavePaint);
		setBackgroundColor(Color.WHITE);
		bClickTip = BitmapFactory.decodeResource(getResources(),
				R.drawable.tip_bg);
		mMatTip = new Matrix();

		wavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(Color.parseColor("#515974"));
		pointPaint.setStyle(Style.FILL);

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setStyle(Style.STROKE);
		linePaint.setColor(Color.parseColor("#f2f6fc"));
		linePaint.setTextAlign(Align.CENTER);
		linePaint.setTypeface(Typeface.SANS_SERIF);

		tTextP = new Paint(Paint.ANTI_ALIAS_FLAG);
		tTextP.setColor(Color.parseColor("#515974"));
		tTextP.setTextAlign(Align.CENTER);
		tTextP.setTypeface(Typeface.DEFAULT_BOLD);

		bTextP = new Paint(Paint.ANTI_ALIAS_FLAG);
		bTextP.setColor(Color.parseColor("#515974"));
		bTextP.setTextAlign(Align.CENTER);

		dotLineP = new Paint(Paint.ANTI_ALIAS_FLAG);
		dotLineP.setColor(Color.parseColor("#f2f6fc"));
		dotLineP.setStyle(Style.STROKE);

	}

	public int dip2px(float dipValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (allPoints.size() == 0 || allPoints.size() == 0) {
			getpoints();
		}

		if (mChartInfos.size() > 0 && flingOffset == -1) {
			init(FlingType.X,
					(mWidth / pointNum * (mChartInfos.get(0).size() - 1)));
		}
		if (mstyle == Mstyle.Curve) {
			for (int i = 0; i < allPoints.size(); i++) {
				if (i != curShowWave) {
					drawscrollline(allPoints.get(i), canvas, colors.get(i));
				}

			}
			drawscrollline(allPoints.get(curShowWave), canvas,
					colors.get(curShowWave));
		} else {
			for (int i = 0; i < allPoints.size(); i++) {
				if (i != curShowWave) {
					drawline(allPoints.get(i), canvas, wavePaint);
				}
			}
			drawline(allPoints.get(curShowWave), canvas, wavePaint);
		}

		List<PointF> mFrontPoints = allPoints.get(curShowWave);
		int size = mFrontPoints.size();
		for (int i = 0; i < size; i++) {
			float x = mFrontPoints.get(i).x;
			float y = mFrontPoints.get(i).y;

			canvas.drawLine(x, y, x, mHeight - marginb, dotLineP);
			canvas.drawCircle(x, y, piontRadiu, pointPaint);
			canvas.drawCircle(x, y, piontRadiu, linePaint);

			WaveInfo chartInfo = mChartInfos.get(curShowWave).get(i);

			if (bottomText.size() > 1) {
				canvas.drawLine(x, mHeight - marginb + marginb * 0.8f, x,
						mHeight - marginb * 0.8f, linePaint);
			}

			if (i == mSelectPosition) {
				Rect bounds = new Rect();

				linePaint.getTextBounds(chartInfo.clickTip, 0,
						chartInfo.clickTip.length(), bounds);
				int textWidth = bounds.left + bounds.width();

				RectF dst = new RectF(x - textWidth * 0.6f, y - tTextH * 2
						- piontRadiu, x + textWidth * 0.6f, y - piontRadiu);

				NinePatch np = new NinePatch(bClickTip,
						bClickTip.getNinePatchChunk(), null);
				np.draw(canvas, dst);

				canvas.drawText(chartInfo.clickTip, x, y - 2.5f - tTextH / 3
						* 2 - piontRadiu, linePaint);
			}
		}
		Paint tPaint;
		for (int i = 0; i < bottomText.size(); i++) {
			List<String> list = bottomText.get(i);
			if (i == 0 || bottomText.size() == 1) {
				tPaint = tTextP;
			} else {
				tPaint = bTextP;
			}
			for (int j = 0; j < list.size(); j++) {
				float lineSpace = 0;
				if (bottomText.size() > 1) {
					lineSpace = tPaint.getTextSize() * 1.5f * i
							- tPaint.getTextSize() * 0.3f;
				} else {
					lineSpace = tPaint.getTextSize() * 0.3f;
				}
				canvas.drawText(list.get(j), allPoints.get(i).get(j).x + sWidth
						/ 2, mHeight - marginb / 2 + lineSpace, tPaint);
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int downX = (int) event.getX();
		// 初始化VelocityTracker
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (Math.abs(downX - event.getX()) < 10 * density) {
				int position = -1;
				List<PointF> mFrontPoints = allPoints.get(curShowWave);
				for (PointF poF : mFrontPoints) {
					position++;
					if (pointToRect(poF).contains(event.getX() + flingOffset,
							event.getY())) {
						mSelectPosition = position;
						invalidate();
						break;
					}
				}
			}
		}

		return super.onTouchEvent(event);
	}

	private RectF pointToRect(PointF p) {
		return new RectF(p.x - rectSize / 2, p.y - rectSize / 2, p.x + rectSize
				/ 2, p.y + rectSize / 2);
	}

	public interface onTouchLinstener {
		public void onItemLongClick(int position);

		public void onValueChange(float value);
	}

	private void drawscrollline(List<PointF> ps, Canvas canvas, int[] js) {
		PointF startp;
		PointF endp;
		Path path = new Path();
		Path linepath = new Path();

		for (int i = 0; i < ps.size() - 1; i++) {

			startp = ps.get(i);
			startp.x = startp.x;

			endp = ps.get(i + 1);
			endp.x = endp.x;

			float wt = (startp.x + endp.x) / 2;
			PointF p3 = new PointF();
			PointF p4 = new PointF();
			p3.y = startp.y;
			p3.x = wt;
			p4.y = endp.y;
			p4.x = wt;

			path.moveTo(startp.x, startp.y);
			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
			linepath.moveTo(startp.x, startp.y);
			linepath.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
			path.lineTo(endp.x, getHeight() - marginb);
			path.lineTo(startp.x, getHeight() - marginb);

		}
		LinearGradient shader = new LinearGradient(0, mHeight - marginb, 0,
				margint, js, null, Shader.TileMode.CLAMP);
		wavePaint.setShader(shader);
		canvas.drawPath(path, wavePaint);
		canvas.drawPath(linepath, linePaint);
	}

	private void getpoints() {
		if (max == 0) {

			for (int o = 0; o < mChartInfos.size(); o++) {
				List<WaveInfo> list = mChartInfos.get(o);
				int size = list.size();
				for (int i = 0; i < size; i++) {
					WaveInfo chartInfo = list.get(i);
					float tvalue = chartInfo.tvalue;
					float bvalue = chartInfo.bvalue;
					if (tvalue > max) {
						max = tvalue;
					}
					if (bvalue > max) {
						max = bvalue;
					}
				}
				sWidth = mWidth / (pointNum - 1);
			}
		}

		float ph = chartHeight / max;

		float offset = mHeight - marginb;
		for (int i = 0; i < mChartInfos.size(); i++) {
			List<WaveInfo> list = mChartInfos.get(i);
			int size = list.size();
			List<PointF> pointFs = new ArrayList<PointF>();
			for (int j = 0; j < size; j++) {
				float x = sWidth * j;
				pointFs.add(new PointF(x, offset - ph * list.get(j).tvalue));
			}
			allPoints.add(pointFs);
		}
	}

	private void drawline(List<PointF> ps, Canvas canvas, Paint paint) {
		PointF startp = new PointF();
		PointF endp = new PointF();
		for (int i = 0; i < ps.size() - 1; i++) {
			startp = ps.get(i);
			endp = ps.get(i + 1);
			canvas.drawLine(startp.x, startp.y, endp.x, endp.y, paint);
		}
	}

	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		float ph = 19.5f;
		mHeight = h;
		mWidth = w;
		marginb = h / ph * 3.6f;
		margint = h / ph * 2f;
		piontRadiu = h / ph * 0.32f;
		lineWidth = h / ph * 0.07f;
		chartHeight = mHeight - marginb - margint;
		linePaint.setStrokeWidth(lineWidth);

		rectSize = h / ph * 2f;

		wavePaint.setColor(Color.RED);
		wavePaint.setAntiAlias(true);
		wavePaint.setStrokeWidth(500);
		wavePaint.setStyle(Style.FILL);

		tTextH = h / ph * 0.80f;
		tTextP.setTextSize(tTextH);

		bTextH = h / ph * 0.74f;
		bTextP.setTextSize(bTextH);
		linePaint.setTextSize(bTextH);

		dotLineP.setPathEffect(new DashPathEffect(new float[] { h / ph * 0.25f,
				h / ph * 0.14f }, 0));
		dotLineP.setStrokeWidth(h / ph * 0.04f);
		RectF rect = new RectF();

		mMatTip.mapRect(rect);

		bTipHeight = h / ph * 1.76f;

	}

	//
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		float childWidthSize = getMeasuredWidth();
		float childHeightSize = childWidthSize / 38f * 19f;
		widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) childHeightSize,
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setMstyle(Mstyle mstyle) {
		this.mstyle = mstyle;
	}

	public onTouchLinstener getmListener() {
		return mListener;
	}

	public void setmListener(onTouchLinstener mListener) {
		this.mListener = mListener;
	}

}
