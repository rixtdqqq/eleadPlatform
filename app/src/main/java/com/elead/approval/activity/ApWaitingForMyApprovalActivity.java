package com.elead.approval.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.elead.activity.BaseActivity;
import com.elead.activity.BaseFragmentActivity;
import com.elead.approval.fragment.ApprovalAlreadyApproveFragment;
import com.elead.approval.fragment.ApprovalWaitApprovalFragment;
import com.elead.approval.widget.MyApprovalPageViewPager;
import com.elead.eplatform.R;

/**
 * @desc
 * @auth Created by mujun.xu on 2016/9/18 0018.
 */
public class ApWaitingForMyApprovalActivity extends BaseFragmentActivity /*implements TopBarView.OnClickListener*/ {


    private MyApprovalPageViewPager myApprovalPageViewPage;
    private ApprovalWaitApprovalFragment approvalWaitApprovalFragment;
    private ApprovalAlreadyApproveFragment approvalAlreadyApproveFragment;
    private ApprovalWaitApprovalFragment.RecycleView recycleviewWait;

    private ApprovalAlreadyApproveFragment.RecycleView recycleViewAlready;
//    private TopBarView titleView;
    private LinearLayout ll_approval_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        setContentView(R.layout.approval_a_main);
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
        /*titleView = (TopBarView) findViewById(R.id.approval_titleView);
        titleView.dismissSeacher();
        titleView.dismissSetting();
        titleView.dismissRightView();
        titleView.toggleCenterView(getResources().getString(R.string.already_approve));
        titleView.toggleLeftView(R.drawable.title_back);
        titleView.setClickListener(this);*/
        setTitle(getResources().getString(R.string.my_approve), BaseActivity.TitleType.NORMAL);
        ll_approval_main = (LinearLayout) findViewById(R.id.ll_approval_main);
        myApprovalPageViewPage = (MyApprovalPageViewPager) findViewById(R.id.approval_viewPagetTab);
        approvalWaitApprovalFragment = myApprovalPageViewPage.getApprovalWaitApprovalFragment();
        approvalAlreadyApproveFragment = myApprovalPageViewPage.getApprovalAlreadyApproveFragment();
        if (null != approvalWaitApprovalFragment) {
            recycleviewWait = approvalWaitApprovalFragment.setRecyView();
        }
        if (null != approvalAlreadyApproveFragment) {
            recycleViewAlready = approvalAlreadyApproveFragment.setRecyView();
        }
    }

    @Override
    protected void onDestroy() {
        // 分级别回收，先把最消耗内存的view回收掉，再回收父view
        try {
            if (null != recycleviewWait) {
                recycleviewWait.recycleThisView();
            }
            if (null != recycleViewAlready) {
                recycleViewAlready.recycleThisView();
            }
            if (null != myApprovalPageViewPage) {
                myApprovalPageViewPage.removeAllViews();
                myApprovalPageViewPage = null;
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

   /* @Override
    public void onLeftBtnClick(View v) {
        finish();
    }

    @Override
    public void onRightBtnClick(View v) {

    }*/
}
