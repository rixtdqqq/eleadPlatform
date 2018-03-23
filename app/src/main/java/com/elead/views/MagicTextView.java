package com.elead.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

public class MagicTextView extends TextView {

	private LinearGradient mLinearGradient;
	private Matrix mGradientMatrix;
	private Paint mPaint;
	private int mViewWidth = 0;
	private int mTranslate = 0;
	private boolean isFromLeftToRight = true;
	private boolean mAnimating = true;
	private float paddingLeft;
	private int postInvalidateDelayed = 100;
	private String TAG="MagicTextView";

	public MagicTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MagicTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mViewWidth == 0) {
			mViewWidth = getMeasuredWidth();
			Log.i(TAG, "mViewWidth="+mViewWidth);
			if (mViewWidth > 0) {
				mPaint = getPaint();
				mPaint.setColor(Color.WHITE);
				mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
						new int[] { Color.BLACK, 0x00ffffffff, Color.BLACK },
						new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
				mPaint.setShader(mLinearGradient);
				mGradientMatrix = new Matrix();
			}
		}
		Rect rect = new Rect();
		String text = "正在加载";
//		mPaint.getTextBounds(text, 0, text.length(), rect);
		setGravity(Gravity.CENTER);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mAnimating && mGradientMatrix != null) {
			if (isFromLeftToRight) {
				mTranslate += mViewWidth / 10;
				if (mTranslate > 2 * mViewWidth) {
					mTranslate = -mViewWidth;
				}
			} else {
				mTranslate -= mViewWidth / 10;
				if (mTranslate < -mViewWidth) {
					mTranslate = 2 * mViewWidth;
				}
			}
			mGradientMatrix.setTranslate(mTranslate, 0);
			mLinearGradient.setLocalMatrix(mGradientMatrix);
			postInvalidateDelayed(postInvalidateDelayed);
		}
	}

	public int getPostInvalidateDelayed() {
		return postInvalidateDelayed;
	}

	public void setPostInvalidateDelayed(int postInvalidateDelayed) {
		this.postInvalidateDelayed = postInvalidateDelayed;
	}

	public void setFromLeftToRight(boolean isFromLeftToRight) {
		this.isFromLeftToRight = isFromLeftToRight;
	}

	public void setmAnimating(boolean mAnimating) {
		this.mAnimating = mAnimating;
	}
}
