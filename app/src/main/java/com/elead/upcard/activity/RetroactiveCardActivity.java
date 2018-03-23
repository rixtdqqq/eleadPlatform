package com.elead.upcard.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.elead.activity.BaseActivity;
import com.elead.activity.BaseFragmentActivity;
import com.elead.eplatform.R;
import com.elead.upcard.fragment.RetroactiveCardFragment;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class RetroactiveCardActivity extends BaseFragmentActivity {

    private RetroactiveCardFragment retroactiveCardFragment;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_common);
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getResources().getString(R.string.buqian), BaseActivity.TitleType.NORMAL);
    }

    private void setupViews() {
        if (null ==retroactiveCardFragment) {
            retroactiveCardFragment=new RetroactiveCardFragment();
        }
        changeFragment(retroactiveCardFragment);
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
