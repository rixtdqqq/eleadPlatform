package com.elead.viewpagerutils;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SuperViewPager extends ViewPager {

	public SuperViewPager(Context context) {
		super(context);
	}

	public SuperViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 可向左滑动
	 */
	public boolean canTurnLeft() {
		return getAdapter() != null && getAdapter().getCount() > 0
				&& getCurrentItem() > 0;
	}

	/**
	 * 可向右滑动
	 */
	public boolean canTurnRight() {
		return getAdapter() != null && getAdapter().getCount() > 0
				&& getCurrentItem() < getAdapter().getCount() - 1;
	}

	/**
	 * 获取当前 View
	 */
	public final View findCurView() {
		return findViewByPosition(getCurrentItem());
	}

	/**
	 * position 非为当前或者缓存，则会返回 null
	 */
	public final View findViewByPosition(int position) {
		View view = null;
		if (position >= 0 && getAdapter() != null
				&& getAdapter().getCount() > 0
				&& getAdapter().getCount() > position) {
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				View child = getChildAt(i);
				if (child != null) {
					ViewGroup.LayoutParams vp = child.getLayoutParams();
					if (vp != null && vp instanceof LayoutParams) {
						LayoutParams params = (LayoutParams) vp;
						int paramsPos = getPositionParam(params);
						if (paramsPos == position) {
							view = child;
							break;
						}
					}
				}
			}
		}
		return view;
	}

	private int getPositionParam(LayoutParams params) {
		int position = -1;
		try {
			if (params != null) {
				Field field = LayoutParams.class.getDeclaredField("position");
				field.setAccessible(true);
				position = field.getInt(params);
			}
		} catch (NoSuchFieldException e) {
			Log.d("a", "$$$ No Such Field.");
		} catch (Exception e) {
			Log.e("a", e.toString());
		}
		return position;
	}

}
