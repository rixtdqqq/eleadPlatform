package com.elead.approval.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.approval.fragment.ApprovalAlreadyApproveFragment;
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
public class MyApprovalPageViewPager extends CustomViewPager {

	private ApprovalWaitApprovalFragment approvalWaitApprovalFragment;
	private ApprovalAlreadyApproveFragment approvalAlreadyApproveFragment;

	public MyApprovalPageViewPager(Context context, AttributeSet attrs,
							 int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyApprovalPageViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyApprovalPageViewPager(Context context) {
		this(context, null, 0);
	}

	public ApprovalWaitApprovalFragment getApprovalWaitApprovalFragment() {
		return approvalWaitApprovalFragment;
	}

	public ApprovalAlreadyApproveFragment getApprovalAlreadyApproveFragment() {
		return approvalAlreadyApproveFragment;
	}

	/*
         * 需要初始化的Fragments,并设置相关的属性
         */
	@Override
	public BaseFragment[] initViewPagerFragments() {

		BaseFragment[] tabFragments = new BaseFragment[2];
		tabFragments[0] = ApprovalWaitApprovalFragment.newInstance();
		tabFragments[1] = ApprovalAlreadyApproveFragment.newInstance();
		try {
			approvalWaitApprovalFragment = (ApprovalWaitApprovalFragment) tabFragments[0];
			approvalAlreadyApproveFragment = (ApprovalAlreadyApproveFragment) tabFragments[1];
		} catch (Exception e) {

		} finally {

		}

		// 设置标题
		String waitInspect = getResources().getString(R.string.waiting_for_my_approval);
		String alreadyInspect = getResources().getString(R.string.already_approve);
		tabFragments[0].setMyTitle(waitInspect);
		tabFragments[1].setMyTitle(alreadyInspect);

		return tabFragments;
	}
}
