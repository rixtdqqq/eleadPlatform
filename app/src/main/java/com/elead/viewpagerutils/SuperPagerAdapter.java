package com.elead.viewpagerutils;

import java.lang.reflect.Field;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public abstract class SuperPagerAdapter extends PagerAdapter {
	
	/**
	 * 返回页卡的数量
	 */
	@Override
	public abstract int getCount();

	/**
	 * 防止动态增减 View 时，缓存 View 不能及时销毁
	 */
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	/**
	 * 官方提示这样写
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	/**
	 * 删除页卡
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (object != null && object instanceof View) {
			View view = (View) object;
			container.removeView(view);
		}
	}
	
	/**
	 * 这个方法用来实例化页卡
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}
	
	/**
	 * 跳转到每个页面都要执行的方法，但会执行多次
	 */
	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}
	
	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
		if (container != null && container instanceof ViewPager) {
			ViewPager pager = (ViewPager) container;
			View view = findCurView(pager);
			if (view != null && view instanceof PagerAssist) {
				PagerAssist assist = (PagerAssist) view;
				if (assist != null && !assist.hasInit()) {
					assist.setInit(true);
					assist.initPager();
				}
			}
		}
	}
	
	/**
	 * 获取当前 View
	 */
	public final View findCurView(ViewPager pager) {
		View view = null;
		if (pager != null) {
			view = findViewByPosition(pager, pager.getCurrentItem());
		}
		return view;
	}
	
	/**
	 * position 非为当前或者缓存，则会返回 null
	 */
	public final View findViewByPosition(ViewPager pager, int position) {
		View view = null;
		if (position >= 0 && pager != null && pager.getAdapter() != null 
				&& pager.getAdapter().getCount() > 0 && pager.getAdapter().getCount() > position) {
			int count = pager.getChildCount();
			for (int i = 0; i < count; i++) {
				View child = pager.getChildAt(i);
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
			Log.d("a","$$$ No Such Field.");
		} catch (Exception e) {
			Log.e("a",e.toString());
		}
		return position;
	}

}