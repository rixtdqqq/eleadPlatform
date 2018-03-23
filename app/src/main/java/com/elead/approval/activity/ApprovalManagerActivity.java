package com.elead.approval.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.elead.activity.BaseFragmentActivity;
import com.elead.approval.fragment.ApprovalClassicModelFragment;
import com.elead.approval.fragment.ApprovalCustomFragment;
import com.elead.approval.widget.MyApprovalManagerViewPager;
import com.elead.eplatform.R;

/**
 * @desc 审批管理
 * @auth Created by mujun.xu on 2016/9/18 0018.
 */
public class ApprovalManagerActivity extends BaseFragmentActivity {


    private MyApprovalManagerViewPager myApprovalManagerViewPager;
    private ApprovalClassicModelFragment approvalClassicModelFragment;
    private ApprovalCustomFragment approvalCustomFragment;
    private ApprovalClassicModelFragment.RecycleView recycleviewClassicModel;

    private ApprovalCustomFragment.RecycleView recycleViewCustom;
    private LinearLayout ll_approval_main;

    public ApprovalClassicModelFragment getApprovalClassicModelFragment() {
        return approvalClassicModelFragment;
    }

    public ApprovalCustomFragment getApprovalCustomFragment() {
        return approvalCustomFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approval_a_manager);
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化控件
     * create by mujun.xu
     */
    private void setupViews() {
        setTitle(getResources().getString(R.string.approval_manager));
        ll_approval_main = (LinearLayout) findViewById(R.id.ll_approval_manager);
        myApprovalManagerViewPager = (MyApprovalManagerViewPager) findViewById(R.id.approval_manager_viewPagetTab);
        approvalClassicModelFragment = myApprovalManagerViewPager.getApprovalClassicModelFragment();
        approvalCustomFragment = myApprovalManagerViewPager.getApprovalCustomFragment();
        if (null != approvalClassicModelFragment) {
            recycleviewClassicModel = approvalClassicModelFragment.setRecyView();
        }
        if (null != approvalCustomFragment) {
            recycleViewCustom = approvalCustomFragment.setRecyView();
        }
    }

    @Override
    protected void onDestroy() {
        // 分级别回收，先把最消耗内存的view回收掉，再回收父view
        try {
            if (null != recycleviewClassicModel) {
                recycleviewClassicModel.recycleThisView();
            }
            if (null != recycleViewCustom) {
                recycleViewCustom.recycleThisView();
            }
            if (null != myApprovalManagerViewPager) {
                myApprovalManagerViewPager.removeAllViews();
                myApprovalManagerViewPager = null;
            }
            if (null != ll_approval_main) {
                ll_approval_main.removeAllViews();
                ll_approval_main = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 调用gc，增加回收效率
            super.onDestroy();
            System.gc();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
