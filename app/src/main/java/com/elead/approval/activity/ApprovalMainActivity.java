package com.elead.approval.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.elead.activity.BaseActivity;
import com.elead.activity.BaseFragmentActivity;
import com.elead.approval.fragment.ApprovalMainFragment;
import com.elead.eplatform.R;

/**
 * @desc 审批首页
 * @auth Created by mujun.xu on 2016/9/14 0014.
 */
public class ApprovalMainActivity extends BaseFragmentActivity {

    private ApprovalMainFragment approvalMainFragment;
    /**
     * 界面是否重构
     */
    private final String IS_DESTROY = "isActivityDestroy";
    /**
     * 界面重构标示符
     */
    private boolean isActivityDestroy = false;
    public boolean getIsActivityDestroy() {
        return isActivityDestroy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isActivityDestroy = savedInstanceState.getBoolean(IS_DESTROY, false);
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        setContentView(R.layout.f_common);
        setupViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setTitle(getResources().getString(R.string.approved), BaseActivity.TitleType.NORMAL);
    }

    private void setupViews() {
        if (null == approvalMainFragment) {
            approvalMainFragment = new ApprovalMainFragment();
        }
        changeFragment(approvalMainFragment);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isActivityDestroy", true);
        super.onSaveInstanceState(outState);
    }
}
