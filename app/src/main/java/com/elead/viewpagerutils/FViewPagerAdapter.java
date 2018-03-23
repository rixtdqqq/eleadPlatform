package com.elead.viewpagerutils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class FViewPagerAdapter extends SuperFragmentPagerAdapter {
	private List<Fragment> fragments;

	public FViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	// 得到每个item
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	// 初始化每个页卡选项
	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		return super.instantiateItem(arg0, arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return PagerAdapter.POSITION_NONE;
	}

	@Override
	public Object getInitArgs(int position) {
		// TODO Auto-generated method stub
		return null;
	}

}
