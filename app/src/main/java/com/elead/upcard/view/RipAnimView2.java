package com.elead.upcard.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class RipAnimView2 extends View implements ISignBase {

    private static final float ADD_TIME_DELAY = 2100;// 影响波纹圈数 值越大越少
    private static final float MOVE_SPEED = 0.002f;// 波纹移动速度 值越大越慢
    private Paint mButtonPaint, mRipPaint;
    private Matrix btnMatrix, animViewMatrix;
    private float mHeight, mWidth;
    private Bitmap bitmapBtn, bitmapAnim;
    private List<Float> waves = new ArrayList<Float>();
    private OnSignClickLinstener onSignClickLinstener;
    private float maxR;
    private float minR;
    private boolean isMoveAnim;
    private RectF btnRect = new RectF();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    isMoveAnim = false;
                    break;

                default:
                    break;
            }
        }

        ;
    };
    public static boolean isConnectFinish;
    private Context mContext;

    public RipAnimView2(Context context) {
        this(context, null);
    }

    public RipAnimView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RipAnimView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inIt(context);
    }

    private float density;
    private boolean isTouch;
    private float animViewTranX;
    private float topTranslateY;
    private float botTranslateY;
    private float currTranslateY;
    private float animScale;
    private float btnBitmapWidth;
    private int animCount;
    private float btnScale;

    private void inIt(Context context) {
        this.mContext = context;
        density = getResources().getDisplayMetrics().density;

        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRipPaint.setStyle(Style.FILL);

        mRipPaint.setColor(Color.parseColor("#FFFFFF"));

        btnMatrix = new Matrix();
        animViewMatrix = new Matrix();
        bitmapBtn = BitmapFactory.decodeResource(getResources(),
                R.drawable.sign_btn2);
        bitmapAnim = BitmapFactory.decodeResource(getResources(),
                R.drawable.sign_saomiao1);
        setBackgroundColor(Color.parseColor("#2ec7c9"));
    }

    private float yy = 0;
    float time = -1;
    float time2 = -1;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (time > ADD_TIME_DELAY) {
                time = 0;
                waves.add(minR);
            }
            time += 20;
            time2 += 1;
            for (int i = 0; i < waves.size(); i++) {
                float f = waves.get(i);
                waves.set(i, f + (mHeight - f) * MOVE_SPEED);
                if (f > maxR) {
                    waves.remove(i);
                }
            }

            if (isMoveAnim) {
                if (currTranslateY > botTranslateY) {
                    animCount++;
                    if (isConnectFinish || animCount > 10) {
                        isMoveAnim = false;
                    }
                    currTranslateY = topTranslateY;
                } else if (currTranslateY < topTranslateY) {
                    animCount++;
                }
                currTranslateY += yy;
                animViewMatrix.setTranslate(animViewTranX, currTranslateY);
                animViewMatrix.postScale(animScale, animScale);

            }

            if (time2 > ADD_TIME_DELAY) {
                handler.postDelayed(runnable, 20);
                postInvalidate();
            } else {
                handler.post(runnable);
            }
        }
    };

    public void startAnimation() {
        handler.postDelayed(runnable, 20);
    }

    public void stopAnimation() {
        handler.removeCallbacks(runnable);
    }

    private ValueAnimator downAnimator;
    float currScalePecent = 1;

    public void downAnim() {
        if (null != downAnimator) {
            downAnimator.cancel();
        }
        upAnimator = ValueAnimator.ofFloat(1, 0.9f);

        upAnimator.setInterpolator(new AccelerateInterpolator(2));
        upAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                currScalePecent = (Float) animator.getAnimatedValue();
                float animatedValue = currScalePecent * btnScale;
                btnMatrix.reset();
                btnMatrix.postScale(animatedValue, animatedValue);
                btnMatrix.postTranslate((mWidth - bitmapBtn.getWidth()
                        * animatedValue) / 2f, (mHeight - bitmapBtn.getHeight()
                        * animatedValue) / 2f);

            }
        });
        upAnimator.setDuration(300);
        upAnimator.start();
    }

    private ValueAnimator upAnimator;
    private Path btnPath;

    public void upAnim() {
        if (null != upAnimator) {
            upAnimator.cancel();
        }
        downAnimator = ValueAnimator.ofFloat(0.9f, 1.1f, 0.95f, 1f);
        downAnimator.setInterpolator(new AccelerateInterpolator());

        downAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                currScalePecent = (Float) animator.getAnimatedValue();
                float animatedValue = currScalePecent * btnScale;
                btnMatrix.reset();
                btnMatrix.postScale(animatedValue, animatedValue);
                btnMatrix.postTranslate((mWidth - bitmapBtn.getWidth()
                        * animatedValue) / 2f, (mHeight - bitmapBtn.getHeight()
                        * animatedValue) / 2f);

            }
        });
        downAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        downAnimator.setDuration(300);
        downAnimator.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = btnRect.contains(event.getX(), event.getY());
                if (isTouch) {
                    if (currScalePecent >= 0.9f) {
                        downAnim();
                    } else {
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (currScalePecent < 1) {
                    upAnim();
                }
                Activity aac = (Activity) mContext;
                if (isTouch && event.getAction() != MotionEvent.ACTION_CANCEL
                        && PermissionUtil.needSignPermission(aac, 0)
                        ) {
                    if (null != onSignClickLinstener) {
                        onSignClickLinstener.onClick();
                    }
                    isMoveAnim = true;
                    isTouch = false;
                    animCount = 0;
                    postInvalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        btnBitmapWidth = mWidth / 2f;
        maxR = (mHeight / 2);
        minR = btnBitmapWidth / 2.8f;

        btnScale = btnBitmapWidth / bitmapBtn.getWidth();
        btnMatrix.reset();
        btnMatrix.setScale(btnScale, btnScale);
        btnMatrix.postTranslate(
                (mWidth - bitmapBtn.getWidth() * btnScale) / 2f,
                (mHeight - bitmapBtn.getHeight() * btnScale) / 2f);
        btnRect = new RectF(bitmapBtn.getWidth()*0.12f,bitmapBtn.getWidth()*0.12f,
                bitmapBtn.getWidth()*0.87f, bitmapBtn.getHeight()*0.87f);
        btnMatrix.mapRect(btnRect);
        btnPath = new Path();
        btnPath.arcTo(btnRect, 0, 359);
        btnPath.close();

        animScale = btnBitmapWidth / bitmapAnim.getWidth();
        animViewMatrix.postScale(animScale, animScale);

        animViewTranX = (mWidth - bitmapAnim.getWidth() * animScale) / 2f
                / animScale;

        topTranslateY = (mHeight - btnBitmapWidth) / 2f
                - bitmapAnim.getHeight() * animScale;
        botTranslateY = topTranslateY + btnBitmapWidth + bitmapAnim.getHeight() * animScale;
        currTranslateY = topTranslateY;
        animViewMatrix.postTranslate(animViewTranX, currTranslateY);

        yy = 3 * density;
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int size = waves.size();
        for (int i = 0; i < size; i++) {
            float r = waves.get(i);
            int alpha = (int) (255 - 255 / maxR * r);
            if (alpha < 0) {
                alpha = 0;
            }
            mRipPaint.setAlpha(alpha);
            canvas.drawCircle(mWidth / 2, mHeight / 2, r, mRipPaint);
        }

        canvas.drawBitmap(bitmapBtn, btnMatrix, null);
        if (isMoveAnim) {
            canvas.save();
            canvas.clipPath(btnPath);
            canvas.drawBitmap(bitmapAnim, animViewMatrix, null);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = 0;
        if (childWidthSize < 1000) {
            childHeightSize = childWidthSize;
        } else {
            childHeightSize = (int) (MyApplication.size[1] / 2.69f);
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSignClickLinstener(
            OnSignClickLinstener onSignClickLinstener) {
        this.onSignClickLinstener = onSignClickLinstener;
    }

    public interface OnSignClickLinstener {
        void onClick();
    }

    @Override
    public void setConnectFinish(boolean isConnectFinish) {
        this.isConnectFinish = isConnectFinish;
    }

    @Override
    public boolean isMove() {
        return isMoveAnim;
    }

}
