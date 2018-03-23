package com.elead.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.elead.activity.BaseFragmentActivity;
import com.elead.eplatform.R;


/**
 * 自定义ViewPager父控件，增加Tab标签
 * 
 * @author mujun.xu
 * 
 *         用法：不能直接引用，需继承该控件，实现initViewPagerFragments()方法
 * 
 */
public class CustomViewPager extends FrameLayout {

	private ViewPager mPager;// 页卡内容
	private BaseFragmentActivity parentActivity;

	/**
	 * PagerSlidingTabStrip的实例
	 */
	private CustomViewPagerTab tabs;

	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;

	public void setTabsSmoothScroll(boolean flag){
		tabs.setIsSmoothScroll(flag);
	}

	/**
	 * 初始化左侧栏，设置默认样式，设置选中的监听动作
	 */
	public CustomViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.e_lead_widget_toolbar, this);

		dm = getResources().getDisplayMetrics();
		tabs = (CustomViewPagerTab) findViewById(R.id.tabs);

		parentActivity = (BaseFragmentActivity) context;
		// 初始化子页面等相关内容
		InitViewPager();
	}

	public BaseFragmentActivity getParentActivity() {
		return parentActivity;
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomViewPager(Context context) {
		this(context, null, 0);
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		// 初始化子页面布局
		FragmentManager fragmentManger = parentActivity
				.getSupportFragmentManager();
		CustomViewPagerAdapter pageAdapter = new CustomViewPagerAdapter(
				fragmentManger, initViewPagerFragments());
		mPager = (ViewPager) findViewById(R.id.vPager);
		mPager.setAdapter(pageAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

		// 设置表头
		tabs.setViewPager(mPager);
		setTabsValue();
	}

	/**
	 * page 当前页码
	 */
	public void setCurrentPage(int page) {
		try {
			mPager.setCurrentItem(page);
		} catch (Exception E) {
			mPager.setCurrentItem(0);
		}
	}
	
	/**
	 * 获取 当前页码
	 */
	public int getCurrentPage() {
			return mPager.getCurrentItem();
	}

	/*
	 * 需要初始化的Fragments,在继承的子控件中实现该方法：
	 */
	public BaseFragment[] initViewPagerFragments() {
		return null;
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColorResource(R.color.qianlv);
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColorResource(R.color.qianlv);
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
		// 设置 Tab Icon图片大小
		tabs.setIcocLayoutHeight(25);
		tabs.setIcocLayoutWidth(25);
		// 设置下划线的颜色
		tabs.setUnderlineColorResource(R.color.small_line_bg);

	}

	/**
	 * ViewPager适配器
	 */
	public class CustomViewPagerAdapter extends FragmentPagerAdapter {
		private BaseFragment[] fragments = null;

		public CustomViewPagerAdapter(FragmentManager fm,
				BaseFragment[] fragmentList) {
			super(fm);
			fragments = fragmentList;
		}

		@Override
		public BaseFragment getItem(int position) {
			return fragments[position];
		}

		@Override
		public int getCount() {
			if (fragments == null) {
				return 0;
			}
			return fragments.length;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragments[position].getMyTitle();
		}
	}

	/**
	 * 页卡切换监听
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageSelected(int arg0) {

		}
	}

	/**设置Tab背景色*/
	public void setTabsBackground(int color){
		tabs.setBackgroundColor(color);
	}




}
