package com.elead.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.views.pulltorefresh.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RefLoadLayout extends FrameLayout {

    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;


    private final ImageView headerImage;
    private final ProgressBar headerProgress;
    private final TextView headerText, pull_to_refresh_time;

    private String pullLabel;
    private String refreshingLabel;
    private String releaseLabel;
    private boolean isrefreshing;
    private final Animation rotateAnimation, resetRotateAnimation;
    public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SharedPreferences sp;
    private String spKey = "spKey";

    public RefLoadLayout(Context context, int mode, String releaseLabel, String pullLabel, String refreshingLabel) {
        super(context);

        ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.loading_layout, this);
        headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
        headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
        headerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
        pull_to_refresh_time = (TextView) header.findViewById(R.id.pull_to_refresh_time);
        final Interpolator interpolator = new LinearInterpolator();
        rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setInterpolator(interpolator);
        rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        rotateAnimation.setFillAfter(true);

        resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        resetRotateAnimation.setInterpolator(interpolator);
        resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        resetRotateAnimation.setFillAfter(true);

        this.releaseLabel = releaseLabel;
        this.pullLabel = pullLabel;
        this.refreshingLabel = refreshingLabel;
        switch (mode) {
            case PullToRefreshView.MODE_PULL_UP_TO_REFRESH:
                headerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
                break;
            case PullToRefreshView.MODE_PULL_DOWN_TO_REFRESH:
            default:
                headerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
                break;
        }
    }

    public void reset() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                headerText.setText(pullLabel);
                headerImage.setVisibility(View.VISIBLE);
                headerProgress.setVisibility(View.GONE);
                isrefreshing = false;

                String time = sf.format(new Date());
                if (null != sp) {
                    sp.edit().putString(spKey, time).commit();
                    if (pull_to_refresh_time.getVisibility() == GONE) {
                        pull_to_refresh_time.setVisibility(VISIBLE);
                    }
                    pull_to_refresh_time.setText("上次加载时间: " + sp.getString(spKey, sf.format(new Date())));
                }
            }
        }, 500);

    }

    public void releaseToRefresh() {
        headerText.setText(releaseLabel);
        headerImage.clearAnimation();
        headerImage.startAnimation(rotateAnimation);
    }

    public void setPullLabel(String pullLabel) {
        this.pullLabel = pullLabel;
    }

    public void refreshing() {
        headerText.setText(refreshingLabel);
        headerImage.clearAnimation();
        headerImage.setVisibility(View.INVISIBLE);
        headerProgress.setVisibility(View.VISIBLE);
        isrefreshing = true;
    }

    public void setRefreshingLabel(String refreshingLabel) {
        this.refreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(String releaseLabel) {
        this.releaseLabel = releaseLabel;
    }

    public void pullToRefresh() {
        headerText.setText(pullLabel);
        headerImage.clearAnimation();
        headerImage.startAnimation(resetRotateAnimation);
    }

    public void setTextColor(int color) {
        headerText.setTextColor(color);
    }

    public boolean isRefreshing() {
        return isrefreshing;
    }

    public void setSpKey(String spKey) {
        this.spKey = spKey;
        this.sp = getContext().getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
        String lastTime = sp.getString(spKey, "");
        if (TextUtils.isEmpty(lastTime)) {
            pull_to_refresh_time.setVisibility(GONE);
        } else {
            pull_to_refresh_time.setVisibility(VISIBLE);
            pull_to_refresh_time.setText("上次加载时间: " + lastTime);
        }
    }
}
