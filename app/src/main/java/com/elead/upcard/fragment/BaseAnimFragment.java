package com.elead.upcard.fragment;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public abstract class BaseAnimFragment extends Fragment {
	public View myView;
	public Activity mContext;

	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
			int layout) {
		mContext = getActivity();
		if (null == myView) {
			myView = inflater.inflate(layout, container, false);
		}
		ViewGroup parent = (ViewGroup) myView.getParent();
		if (parent != null) {
			parent.removeView(myView);
		}
		return myView;
	}

	public View getMyView() {
		return myView;
	}

	public void setView(View view) {
		this.myView = view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/** Fragment当前状态是否可见 */
	protected boolean isVisible;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (getUserVisibleHint()) {
			isVisible = true;
			handler.sendEmptyMessage(100);
		} else {
			isVisible = false;
			handler.sendEmptyMessage(200);

		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				onVisible();
				break;
			case 200:
				onInvisible();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();
	}

	/**
	 * 不可见
	 */
	protected void onInvisible() {

	}

	/**
	 * 延迟加载 子类必须重写此方法
	 */
	protected abstract void lazyLoad();
}
