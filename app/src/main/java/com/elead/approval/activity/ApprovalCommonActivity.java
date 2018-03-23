package com.elead.approval.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.elead.activity.BaseFragmentActivity;
import com.elead.approval.fragment.ApprovalAddWorkFragment;
import com.elead.approval.fragment.ApprovalCCMyFragment;
import com.elead.approval.fragment.ApprovalContractFragment;
import com.elead.approval.fragment.ApprovalGoAway;
import com.elead.approval.fragment.ApprovalIStartedFragment;
import com.elead.approval.fragment.ApprovalPay;
import com.elead.approval.fragment.ApprovalPositive;
import com.elead.approval.fragment.ApprovalRecruitment;
import com.elead.approval.fragment.ApprovalShoping;
import com.elead.approval.fragment.ApprovalUserCarApply;
import com.elead.approval.fragment.ApprovalYongYinApply;
import com.elead.approval.fragment.BeiYongMoneyApplyFragment;
import com.elead.approval.fragment.ClassBaoBeiFragment;
import com.elead.approval.fragment.CollectionMoneyFragment;
import com.elead.approval.fragment.DepartmentXieZuoFragment;
import com.elead.approval.fragment.JiXiaoSelfFragment;
import com.elead.approval.fragment.KaiDiSendFragment;
import com.elead.approval.fragment.MeetingShenpiFragment;
import com.elead.approval.fragment.NewFactFragment;
import com.elead.approval.fragment.OrderApplyFragment;
import com.elead.approval.fragment.ProjectApplyFragment;
import com.elead.approval.fragment.ThingsPurchaseFragment;
import com.elead.approval.fragment.WrokAskFragment;
import com.elead.approval.fragment.ZizhiUseFragment;
import com.elead.eplatform.R;

/**
 * @desc
 * @auth Created by mujun.xu on 2016/9/18 0018.
 */
public class ApprovalCommonActivity extends BaseFragmentActivity {

    Fragment changeFragment;
    private String frgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_common);
        frgName = getIntent().getStringExtra("fragment");
        loadFragment();
    }

    /**
     * 切换到指定fragment
     *
     * @param fragment
     */
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 加载fragment
     *
     * @param
     */
    private void loadFragment() {
        if (null == changeFragment) {
            if (TextUtils.equals(frgName, "ApprovalIStartedFragment")) {
                changeFragment = new ApprovalIStartedFragment();
            } else if (TextUtils.equals(frgName,"ApprovalCCMyFragment")) {
                changeFragment = new ApprovalCCMyFragment();
            } else if (TextUtils.equals(frgName,"DepartmentXieZuoFragment")) {
                changeFragment = new DepartmentXieZuoFragment();
            } else if (TextUtils.equals(frgName,"ProjectApplyFragment")) {
                changeFragment = new ProjectApplyFragment();
            } else if (TextUtils.equals(frgName,"BeiYongMoneyApplyFragment")) {
                changeFragment = new BeiYongMoneyApplyFragment();
            } else if (TextUtils.equals(frgName,"OrderApplyFragment")) {
                changeFragment = new OrderApplyFragment();
            } else if (TextUtils.equals(frgName,"ThingsPurchaseFragment")) {
                changeFragment = new ThingsPurchaseFragment();
            } else if (TextUtils.equals(frgName,"CollectionMoneyFragment")) {
                changeFragment = new CollectionMoneyFragment();
            } else if (TextUtils.equals(frgName,"ApprovalShoping")) {
                changeFragment = new ApprovalShoping();
            } else if (TextUtils.equals(frgName,"NewFactFragment")) {
                changeFragment = new NewFactFragment();
            } else if (TextUtils.equals(frgName,"MeetingShenpiFragment")) {
                changeFragment = new MeetingShenpiFragment();
            } else if (TextUtils.equals(frgName,"ZizhiUseFragment")) {
                changeFragment = new ZizhiUseFragment();
            } else if (TextUtils.equals(frgName,"ApprovalContractFragment")) {
                changeFragment = new ApprovalContractFragment();
            } else if (TextUtils.equals(frgName,"WrokAskFragment")) {
                changeFragment = new WrokAskFragment();
            } else if (TextUtils.equals(frgName,"ClassBaoBeiFragment")) {
                changeFragment = new ClassBaoBeiFragment();
            } else if (TextUtils.equals(frgName,"KaiDiSendFragment")) {
                changeFragment = new KaiDiSendFragment();
            } else if (TextUtils.equals(frgName,"JiXiaoSelfFragment")) {
                changeFragment = new JiXiaoSelfFragment();
            } else if (TextUtils.equals(frgName,"ApprovalUserCarApply")) {
                changeFragment = new ApprovalUserCarApply();
            } else if (TextUtils.equals(frgName,"ApprovalYongYinApply")) {
                changeFragment = new ApprovalYongYinApply();
            } else if (TextUtils.equals(frgName,"ApprovalGoAway")) {
                changeFragment = new ApprovalGoAway();
            } else if (TextUtils.equals(frgName,"ApprovalPositive")) {
                changeFragment = new ApprovalPositive();
            } else if (TextUtils.equals(frgName,"ApprovalAddWorkFragment")) {
                changeFragment = new ApprovalAddWorkFragment();
            } else if (TextUtils.equals(frgName,"ApprovalPay")) {
                changeFragment = new ApprovalPay();
            } else if (TextUtils.equals(frgName,"ApprovalRecruitment")) {
                changeFragment = new ApprovalRecruitment();
            }
        }
        changeFragment(changeFragment);
    }

}
