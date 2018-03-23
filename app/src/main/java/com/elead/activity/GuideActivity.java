package com.elead.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.elead.adapter.PagerViewAdapter;
import com.elead.eplatform.R;
import com.elead.utils.AppManager;
import com.elead.utils.SharedPreferencesUtil;
import com.elead.utils.StatusBarUtils;
import com.elead.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21 0021.
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private Button mEntryTiYan;
    private List<View> mViews = new ArrayList<View>();
    private ImageView[] points;
    private int currentIndex;
    private static final float ZOOM_MAX = 1.3f;
    private static final float ZOOM_MIN = 1.0f;
    private ImageView img_one, img_two, img_three;
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private boolean mFirst = false;
    private RelativeLayout mMainView;
    private LinearLayout linerCricel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        mFirst = SharedPreferencesUtil.getBoolean(this, KEY_GUIDE_ACTIVITY);
        if (!mFirst) {
            initView();
//            createShortCut();
            initPoint();
        } else {
            Intent mIntent1 = new Intent();
            mIntent1.setClass(this, SplashActivity.class);
            startActivity(mIntent1);
            finish();
        }

    }

    public void createShortCut() {
        //创建快捷方式的Intent
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), GuideActivity.class));
        //发送广播。OK
        sendBroadcast(shortcutintent);
    }

    private void initView() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        mMainView = (RelativeLayout) mInflater.inflate(R.layout.guide_activity, null);
        viewPager = (ViewPager) mMainView.findViewById(R.id.view_pager);

        View tabNumberOne = mInflater.inflate(R.layout.guide_backgroud_one, null);
        View tabNumberTwo = mInflater.inflate(R.layout.guide_backgroud_two, null);
        View tabNumberThree = mInflater.inflate(R.layout.guide_backgroud_three, null);

        img_one = (ImageView) tabNumberOne.findViewById(R.id.img_one);
        Util.setBackGround(this, img_one, R.drawable.guide_backgroud_1);

        img_two = (ImageView) tabNumberTwo.findViewById(R.id.img_two);
        Util.setBackGround(this, img_two, R.drawable.guide_backgroud_2);

        img_three = (ImageView) tabNumberThree.findViewById(R.id.img_three);
        Util.setBackGround(this, img_three, R.drawable.guide_backgroud_3);

        mViews.add(tabNumberOne);
        mViews.add(tabNumberTwo);
        mViews.add(tabNumberThree);

        mEntryTiYan = (Button) tabNumberThree.findViewById(R.id.tiyan_bt);
        mEntryTiYan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirst = true;
                SharedPreferencesUtil.putBoolean(GuideActivity.this, KEY_GUIDE_ACTIVITY, mFirst);
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        });

        PagerViewAdapter adapter = new PagerViewAdapter(this, mViews);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(this);
        setContentView(mMainView);
    }

    private void setAnimats() {
        /**
         * 放大动画
         */
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(ZOOM_MIN, ZOOM_MAX, ZOOM_MIN, ZOOM_MAX, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.8f));

        animationSet.setDuration(500);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);
        // 实现心跳的View

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /**
                 * 缩小动画
                 */
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(ZOOM_MAX, ZOOM_MIN, ZOOM_MAX, ZOOM_MIN, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.8f, 1.0f));
                animationSet.setDuration(600);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);
                // 实现心跳的View
                mEntryTiYan.startAnimation(animationSet);
            }
        });
        mEntryTiYan.startAnimation(animationSet);
    }


    private void initPoint() {
        linerCricel = (LinearLayout) findViewById(R.id.ll);
        points = new ImageView[3];

        for (int i = 0; i < points.length; i++) {
            points[i] = (ImageView) linerCricel.getChildAt(i);
            points[i].setEnabled(true);
            points[i].setOnClickListener(this);

            points[i].setTag(i);
        }
        currentIndex = 0;
        points[currentIndex].setEnabled(false);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        points[position].setEnabled(false);
        points[currentIndex].setEnabled(true);
        if (position == 2) {
            setAnimats();
            points[2].setEnabled(true);
            linerCricel.setVisibility(View.GONE);

        } else {
            linerCricel.setVisibility(View.VISIBLE);
        }
        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }


    @Override
    public void onClick(View view) {
        viewPager.setCurrentItem((Integer) view.getTag());
    }


}
