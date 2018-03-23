package com.elead.fragment;

import android.os.Bundle;

public abstract class BaseBackPressFragment extends AbstractBaseFragment {
	protected BackHandledInterface mBackHandledInterface;

	/**
	 * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
	 * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
	 * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
	 */
	public abstract boolean onBackPressed();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!(getActivity() instanceof BackHandledInterface)) {
			throw new ClassCastException(
					"Hosting Activity must implement BackHandledInterface");
		} else {
			this.mBackHandledInterface = (BackHandledInterface) getActivity();
		}
	}

	@Override
	protected void onVisible() {
		mBackHandledInterface.setSelectedFragment(this);
		super.onVisible();
	}

	@Override
	protected void onInvisible() {
		// TODO Auto-generated method stub
		mBackHandledInterface.setSelectedFragment(null);
		super.onInvisible();
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}

}
