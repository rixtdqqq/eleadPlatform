package com.elead.views;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.elead.utils.SizeUtil;


public class JumpLayout extends FrameLayout {

    boolean isStart = true;
    private float mDensity;
    private float textSize = 20;
    private float textHeight;
    private float viewHeight;
    private int mWidth;
    private int mHeight;
    private float padding;
    private onAnimFinishLinstener animFinishLinstener;
    private int haveRunCount = 0;

    public JumpLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inIt();
    }

    private void inIt() {
        mDensity = getContext().getResources().getDisplayMetrics().density;
        padding = 3 * mDensity;
    }

    private PointF endF;
    private int xDuration, yDuration;
    private String[] text;

    public void startAnim(final PointF startF, final PointF endF,
                          final int xDuration, final int yDuration, final String[] text,
                          int textSize) {
        if (getChildCount() == 0) {
            this.textSize = textSize;
            this.endF = endF;
            this.xDuration = xDuration;
            this.yDuration = yDuration;
            this.text = text;
            for (int i = 0; i < text.length; i++) {
                MagicTextView textView = new MagicTextView(getContext());
                textView.setLayoutParams(new LayoutParams(-2, -2));
                textView.setTextSize(textSize);
                textView.setBackgroundColor(Color.TRANSPARENT);
                textView.setVisibility(View.GONE);
                textView.setText(text[i]);
                textView.setShadowLayer(10, 6, 6, Color.GRAY);
                Paint textPaint = textView.getPaint();
                Rect bounds = new Rect();
                textPaint.getTextBounds(text[i], 0, 1, bounds);
                textHeight = textPaint.measureText(text[0]);
                viewHeight = SizeUtil.measureView(textView);
                addView(textView);
            }
        }
        jumpAnim();
    }

    private void jumpAnim() {
        for (int i = 0; i < text.length; i++) {
            final float index = i;
            getChildAt(i).animate()
                    .translationX(endF.x + index * (textHeight + padding))
                    .setInterpolator(new AccelerateInterpolator())
                    .setStartDelay(i * (yDuration - (yDuration / 2)))
                    .setDuration(yDuration).start();
            getChildAt(i).animate()
                    .setStartDelay(i * (yDuration - (yDuration / 2)))
                    .translationY(endF.y + textHeight - viewHeight)
                    .setInterpolator(new BounceInterpolator())
                    .setDuration(yDuration).setListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator arg0) {
                    getChildAt((int) index).setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animator arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator arg0) {
                    if (index == 2) {
                        moveAnim();
                    }
                }

                @Override
                public void onAnimationCancel(Animator arg0) {
                    // TODO Auto-generated method stub

                }
            }).start();
        }
    }

    private void moveAnim() {
        for (int i = 0; i < text.length; i++) {
            final int index = i;
            getChildAt(i).animate().translationX(mWidth + textSize)
                    .setInterpolator(new AccelerateInterpolator())
                    .setStartDelay((7 - index) * xDuration)
                    .setDuration((4 - index) * xDuration)
                    .setListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator arg0) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator arg0) {
                        }

                        @Override
                        public void onAnimationEnd(Animator arg0) {
                            haveRunCount++;
                            if (null != animFinishLinstener && index == text.length - 1) {
                                animFinishLinstener
                                        .onAnimFinish(haveRunCount);
                            }
                            if (index == 0) {
                                for (int j = 0; j < getChildCount(); j++) {
                                    getChildAt(j).setTranslationX(0);
                                    getChildAt(j).setTranslationY(0);
                                    getChildAt(j).setVisibility(View.GONE);
                                }
                                destroyDrawingCache();
                                jumpAnim();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator arg0) {

                        }
                    }).start();
        }
        return;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:

                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        mHeight = getHeight();
        mWidth = getWidth();
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setAnimFinishLinstener(onAnimFinishLinstener animFinishLinstener) {
        this.animFinishLinstener = animFinishLinstener;
    }

    public interface onAnimFinishLinstener {
        void onAnimFinish(int count);
    }

}
