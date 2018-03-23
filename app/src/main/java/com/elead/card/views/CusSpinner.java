package com.elead.card.views;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("DrawAllocation")
public class CusSpinner extends BaseView {
    private Context mContext;
    private String[] content = new String[]{};
    private float currHeight;
    private float mWidth;
    private float textHeight;
    private float dropDownImgHeight;
    private Bitmap dpdBit = null;
    private Matrix matrix;
    private int textColor;
    private Paint paint;
    private float s;
    private float defaultWidth;
    private float pecH;
    private float childWidthSize;
    private int defaultHeight;
    private boolean isShowText = false;
    private List<RectF> rectText = null;
    private float currTW;
    private boolean animIsEnd;
    private float density;
    public static final int DEF_HEIGHT = 25;

    public CusSpinner(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public CusSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        // TODO Auto-generated method stub
        super.onWindowVisibilityChanged(visibility);
        System.out.println("onWindowVisibilityChanged: " + visibility);
    }

    private void init() {
        density = mContext.getResources().getDisplayMetrics().density;
        defaultHeight = (int) (DEF_HEIGHT * density + 0.5f);
        currHeight = defaultHeight;
        dpdBit = BitmapFactory.decodeResource(getResources(),
                R.drawable.ding_item_menu);
        textColor = Color.parseColor("#515974");

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        paint.setColor(textColor);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        matrix = new Matrix();
        matrix.mapRect(new RectF(0, 0, mWidth, dropDownImgHeight));

        setBackgroundResource(R.drawable.card_spinner_bg);
        setBackgroundColor(Color.WHITE);
        setFocusableInTouchMode(true);
        setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (animIsEnd) {
                    Message obtainMessage = handler.obtainMessage();
                    obtainMessage.obj = arg1;
                    obtainMessage.what = 1000;
                    handler.removeMessages(1000);
                    handler.sendMessageDelayed(obtainMessage, 300);
                }
            }
        });
    }

    public float getDefaultHeight() {
        return defaultHeight;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    if (msg.obj instanceof Boolean) {
                        boolean isShow = (Boolean) msg.obj;
                        if (!isShow && isShowRealy) {
                            open2CloseAnim();
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        ;

    };

    private float getTextLength(int getType) {
        String str = "";
        switch (getType) {
            case Integer.MAX_VALUE:
                int maxLength = 0;
                for (int i = 0; i < content.length; i++) {
                    String string = content[i];
                    if (string.length() > maxLength) {
                        maxLength = string.length();
                        str = string;
                    }
                }
                break;
            case Integer.MIN_VALUE:
                int minLength = Integer.MAX_VALUE;
                for (int i = 0; i < content.length; i++) {
                    String string = content[i];
                    if (string.length() < minLength) {
                        minLength = string.length();
                        str = string;
                    }
                }
                break;
            case 0:
                str = content[0];
                break;

            default:
                break;
        }

        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        float width = bounds.left + bounds.width();
        return width;
    }

    private float startH, endH, startW, endW = 0;
    private boolean isShowRealy;

    private void open2CloseAnim() {
        if (content.length <= 1)
            return;

        if (getHeight() == defaultHeight) {
            startH = defaultHeight;
            endH = defaultHeight * content.length;
            startW = endW = getTextLength(0);
            isShowText = true;
            isShowRealy = true;
        } else {
            isShowRealy = false;
            startH = defaultHeight * content.length;
            endH = defaultHeight;

            endW = getTextLength(0);
            // setFocusable(false);
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                float animatedValue = (Float) arg0.getAnimatedValue();
                currHeight = startH - (startH - endH) * animatedValue;
                currTW = startW - (startW - endW) * animatedValue;
                requestLayout();
            }
        });
        animator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                animIsEnd = false;

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                if (!isShowText) {
                    isShowText = false;
                    invalidate();
                }
                animIsEnd = true;
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animator.setDuration(150);
        animator.start();

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (content.length > 0) {
            canvas.drawBitmap(dpdBit, matrix, null);
            int length = 1;
            if (isShowText) {
                length = content.length;
            }
            rectText = new ArrayList<RectF>();
            for (int i = 0; i < length; i++) {
                float defHeight = defaultHeight / 2 + textHeight / 3;
                float x = dropDownImgHeight;
                float y = defHeight + i * defaultHeight;
                canvas.drawText(content[i], x, y, paint);
                if (isShowText) {
                    rectText.add(new RectF(0, y - textHeight * 1.5f, mWidth, y
                            + textHeight / 2));
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:
                if (isShowText && animIsEnd) {
                    for (int i = 0; i < rectText.size(); i++) {
                        if (rectText.get(i).contains(event.getX(), event.getY())) {
                            String a = content[i];
                            String b = content[0];
                            content[i] = b;
                            content[0] = a;
                            open2CloseAnim();
                            break;
                        }
                    }

                } else {
                    open2CloseAnim();
                }

                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void setBackgroundColor(int color) {
        GradientDrawable myGrad = (GradientDrawable) getBackground();
        myGrad.setColor(color);
        myGrad.setCornerRadius(defaultHeight / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        dropDownImgHeight = defaultHeight / pecH * 1.5f;
        matrix.reset();
        if (null != dpdBit) {
            s = dropDownImgHeight / dpdBit.getWidth();
            matrix.postScale(s, s);
            matrix.postTranslate((mWidth - (defaultWidth + dropDownImgHeight
                    * s) / 2f), ((defaultHeight - dpdBit.getHeight() * s) / 2f));
        }
        // float s = dpdBit.getWidth() > dpdBit.getHeight() ? dropDownImgHeight
        // / dpdBit.getWidth() : dropDownImgHeight / dpdBit.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        pecH = 2.19f;
        defaultWidth = defaultHeight / pecH * 3.2f;
        textHeight = defaultHeight / pecH * 0.88f;
        paint.setTextSize(textHeight);

        if (0 == currTW && content.length > 0) {
            Rect bounds = new Rect();
            String str = content[0];
            paint.getTextBounds(str, 0, str.length(), bounds);
            currTW = bounds.left + bounds.width();
        }

        float padding = defaultHeight / 2;
        childWidthSize = defaultWidth + currTW + padding;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) currHeight,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setText(String[] str) {
        this.content = str;
        invalidate();
    }

}
