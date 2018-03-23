package com.elead.viewpagerutils;

import java.lang.reflect.Field;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;


public abstract class SuperFragmentPagerAdapter extends FragmentPagerAdapter {

	public SuperFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public abstract int getCount();
	
	public abstract Object getInitArgs(int position);
	
	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
		
		try {
			Field field = FragmentPagerAdapter.class.getDeclaredField("mCurrentPrimaryItem");
			field.setAccessible(true);
			Object object = field.get(this);
			
			if (object != null && object instanceof PagerAssist) {
				PagerAssist assist = (PagerAssist) object;
				if (assist != null && !assist.hasInit()) {
					assist.setInit(true);
					assist.initPager();
				}
			}
		} catch (NoSuchFieldException e) {
			Log.d("a","$$$ No Such Field.");
		} catch (Exception e) {
			Log.e("a",e.toString());
		}
	}

	@Override
	public abstract Fragment getItem(int position);

}
