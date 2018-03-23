package com.elead.views;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import static android.animation.LayoutTransition.APPEARING;

/**
 * Created by Administrator on 2017/1/18 0018.
 */

public class AnimChangeLayout extends LinearLayout {
    private float currHeight, secondHeight;
    public static boolean isAnim;

    public AnimChangeLayout(Context context) {
        this(context, null);
    }

    public AnimChangeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimChangeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutTransition transitioner = new LayoutTransition();
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translateY", 1f, 0f);
        transitioner.setAnimator(APPEARING, animIn);

//        APPEARING —— 元素在容器中出现时所定义的动画。
//        DISAPPEARING —— 元素在容器中消失时所定义的动画。
//        CHANGE_APPEARING —— 由于容器中要显现一个新的元素，其它需要变化的元素所应用的动画
//        CHANGE_DISAPPEARING —— 当容器中某个元素消失，其它需要变化的元素所应用的动画

//        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translateY", 1f, 0f);
//        transitioner.setAnimator(DISAPPEARING, animOut);
        setLayoutTransition(transitioner);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isAnim) {
            if (0 == currHeight) {
                currHeight = getMeasuredHeight();
            } else if (secondHeight == 0) {
                secondHeight = getMeasuredHeight();
            }
            float childHeight = getChildHeight();
            if (secondHeight != childHeight) {
                isAnim = true;
                anim(currHeight, childHeight);
                secondHeight = 0;
                currHeight = 0;
            }
        }
    }

    private int childHeight = 0;

    private int getChildHeight() {
        if (0 == childHeight) {
            int count = getChildCount();
            int height = 0;
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                if (view.getVisibility() == VISIBLE) {
                    height += view.getMeasuredHeight();
                }
            }
            return height + getPaddingBottom() + getPaddingTop();
        } else return childHeight;
    }

    private void anim(float startHeight, float endHeight) {

        ValueAnimator animator = ValueAnimator.ofFloat(startHeight, endHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currHeight = (float) animation.getAnimatedValue();
                getLayoutParams().height = (int) currHeight;
                requestLayout();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(300);
        animator.start();

    }
}
