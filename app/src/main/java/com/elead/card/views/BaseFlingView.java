package com.elead.card.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

public abstract class BaseFlingView extends BaseView {
	public enum FlingType {
		X, Y// X还是Y轴漂移
	}

	private FlingType flingType = FlingType.X;
	public float mMaxValue;// 画出全部所需要宽度
	public float flingOffset = -1;// 当前偏移量

	public Context mContext;
	private VelocityTracker mVelocityTracker;
	private Scroller mScroller;
	private float mMinVelocitx = 0;
	private float currPosition;
	private float downPosition;
	public float density;
	private float downY;

	public BaseFlingView(Context context) {
		this(context, null);
	}

	public BaseFlingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseFlingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		mScroller = new Scroller(getContext());
		mMinVelocitx = ViewConfiguration.get(getContext())
				.getScaledMinimumFlingVelocity();

		density = mContext.getResources().getDisplayMetrics().density;
	}

	public void init(FlingType flingType, float mMaxValue) {
		this.flingType = flingType;
		this.mMaxValue = mMaxValue;
	};

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (flingType == FlingType.X) {
			canvas.translate(-flingOffset, 0);
		} else {
			canvas.translate(0, -flingOffset);
		}
	}

	@SuppressLint("Recycle")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventPosition = 0;
		if (flingType == FlingType.X) {
			eventPosition = (int) event.getX();
		} else {
			eventPosition = (int) event.getY();
		}
		if (null == mVelocityTracker) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currPosition = flingOffset;
			downPosition = eventPosition;
			mScroller.forceFinished(true);
			break;
		case MotionEvent.ACTION_MOVE:
			flingOffset = currPosition + (int) (downPosition - eventPosition);
			changeMoveAndValue();
			break;
		case MotionEvent.ACTION_UP:
			currPosition = flingOffset;
			countVelocityTracker();
			return false;
		}

		return true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = event.getY();
			break;
		}
		float aaa = 50 * density;
		if (Math.abs(downY - event.getY()) > aaa) {
			getParent().requestDisallowInterceptTouchEvent(false);
		} else {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(event);

	}

	private void changeMoveAndValue() {
		flingOffset = flingOffset <= 0 ? 0 : flingOffset;
		flingOffset = flingOffset > mMaxValue ? mMaxValue : flingOffset;
		invalidate();
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			if (flingType == FlingType.X) {
				int xPosition = mScroller.getCurrX();
				flingOffset = currPosition - xPosition;

			} else {
				int yPosition = mScroller.getCurrY();
				flingOffset = currPosition - yPosition;
			}
			if (flingOffset <= 0 || flingOffset >= mMaxValue) {
				mScroller.forceFinished(true);
			}
			changeMoveAndValue();

		}
	}

	private void countVelocityTracker() {
		mVelocityTracker.computeCurrentVelocity(1000);
		if (flingType == FlingType.X) {
			float xVelocity = mVelocityTracker.getXVelocity();
			if (Math.abs(xVelocity) > mMinVelocitx) {
				mScroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE,
						Integer.MAX_VALUE, 0, 0);
			}
		} else {
			float yVelocity = mVelocityTracker.getYVelocity();
			if (Math.abs(yVelocity) > mMinVelocitx) {
				mScroller.fling(0, 0, 0, (int) yVelocity, 0, 0,
						Integer.MIN_VALUE, Integer.MAX_VALUE);
			}
		}
	}

}
