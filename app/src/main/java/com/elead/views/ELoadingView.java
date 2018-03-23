package com.elead.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class ELoadingView extends RelativeLayout {
    public ELoadingView(Context context) {
        this(context,null);
    }

    public ELoadingView(Context context, AttributeSet attrs) {
        this(context, null,0);
    }

    public ELoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.e_loading_dialog,this);
        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(900);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.imv_loading).startAnimation(animation);
    }
}
