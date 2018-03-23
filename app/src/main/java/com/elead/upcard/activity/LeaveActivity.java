package com.elead.upcard.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.elead.activity.BaseActivity;
import com.elead.activity.BaseFragmentActivity;
import com.elead.eplatform.R;
import com.elead.upcard.fragment.UpCardLeaveFragment;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class LeaveActivity extends BaseFragmentActivity {
    private UpCardLeaveFragment upCardFragment;
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
        this.setTitle(getResources().getString(R.string.approval_leave), BaseActivity.TitleType.NORMAL);
    }

    private void setupViews() {
        if (null ==upCardFragment) {
            upCardFragment = new UpCardLeaveFragment();
        }
        changeFragment(upCardFragment);
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
