package com.elead.approval.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.approval.fragment.ApprovalAlreadyApproveFragment;
import com.elead.approval.fragment.ApprovalClassicModelFragment;
import com.elead.approval.fragment.ApprovalCustomFragment;
import com.elead.approval.fragment.ApprovalWaitApprovalFragment;
import com.elead.eplatform.R;
import com.elead.views.BaseFragment;
import com.elead.views.CustomViewPager;


/**
 * 继承 CustomViewPager ，实现首页两栏滑动功能
 * 
 * @author xwx298252
 * 
 */
public class MyApprovalManagerViewPager extends CustomViewPager {

	private ApprovalClassicModelFragment approvalClassicModelFragment;
	private ApprovalCustomFragment approvalCustomFragment;

	public MyApprovalManagerViewPager(Context context, AttributeSet attrs,
									  int defStyle) {
		super(context, attrs, defStyle);
	}

	public ApprovalClassicModelFragment getApprovalClassicModelFragment() {
		return approvalClassicModelFragment;
	}

	public ApprovalCustomFragment getApprovalCustomFragment() {
		return approvalCustomFragment;
	}

	public MyApprovalManagerViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyApprovalManagerViewPager(Context context) {
		this(context, null, 0);
	}


	/*
         * 需要初始化的Fragments,并设置相关的属性
         */
	@Override
	public BaseFragment[] initViewPagerFragments() {

		BaseFragment[] tabFragments = new BaseFragment[2];
		tabFragments[0] = ApprovalClassicModelFragment.newInstance();
		tabFragments[1] = ApprovalCustomFragment.newInstance();
		try {
			approvalClassicModelFragment = (ApprovalClassicModelFragment) tabFragments[0];
			approvalCustomFragment = (ApprovalCustomFragment) tabFragments[1];
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		// 设置标题
		String waitInspect = getResources().getString(R.string.classic_model);
		String alreadyInspect = getResources().getString(R.string.custom);
		tabFragments[0].setMyTitle(waitInspect);
		tabFragments[1].setMyTitle(alreadyInspect);

		return tabFragments;
	}
}
