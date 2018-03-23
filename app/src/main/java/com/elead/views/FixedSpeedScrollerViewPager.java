/**
 * 
 */
package com.elead.views;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author
 *	设置ViewPager左右滑动的速度
 */
public class FixedSpeedScrollerViewPager extends Scroller {

	/**
	 * @param context
	 */
	
	private int mDuration = 600;
	
	public FixedSpeedScrollerViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public int getmDuration() {
		return mDuration;
	}

	public void setmDuration(int mDuration) {
		this.mDuration = mDuration;
	}

	/**
	 * @param context
	 * @param interpolator
	 * @param flywheel
	 */
	public FixedSpeedScrollerViewPager(Context context,
									   Interpolator interpolator, boolean flywheel) {
		super(context, interpolator, flywheel);
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param context
	 * @param interpolator
	 */
	public FixedSpeedScrollerViewPager(Context context,
			Interpolator interpolator) {
		super(context, interpolator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy) {
		// TODO Auto-generated method stub
		super.startScroll(startX, startY, dx, dy,mDuration);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		// TODO Auto-generated method stub
		super.startScroll(startX, startY, dx, dy, mDuration);
	}

	
}
