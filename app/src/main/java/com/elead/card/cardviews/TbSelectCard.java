package com.elead.card.cardviews;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;

public class TbSelectCard extends RelativeLayout implements ICard {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final String action = TbSelectCard.class.getSimpleName();
    public static final String INDEX = "INDEX";
    public static final String content = "content";
    private List<String> items = new ArrayList<String>();
    private Context mContext;
    private float mWidth;
    private float mHeight;
    private int selectIndex = 0;
    private float density;
    private LinearLayout tvContinor;
    private View lineView;
    private int lineWidth;
    private int lineHeight;
    private LayoutParams btLineParam;
    private String dates;

    public TbSelectCard(Context context) {
        this(context, null);
    }

    public TbSelectCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TbSelectCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        density = mContext.getResources().getDisplayMetrics().density;
        setBackgroundColor(getResources().getColor(
                R.color.opera_title_bg));
        tvContinor = new LinearLayout(mContext);
        tvContinor.setLayoutParams(new LayoutParams(-1, -1));
        tvContinor.setOrientation(LinearLayout.HORIZONTAL);
        addView(tvContinor);

        lineView = new View(mContext);
        lineView.setBackgroundColor(getResources().getColor(
                R.color.lanlv));
        btLineParam = new LayoutParams(0, (int) (3 * density));
        btLineParam.addRule(ALIGN_PARENT_BOTTOM);
        addView(lineView, btLineParam);
        setDate();
    }


    public void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        params.weight = 1;
        final int pressColor = mContext.getResources().getColor(R.color.white);
        final int defColor = mContext.getResources().getColor(
                R.color.fontcolors4);

        tvContinor.removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            final TextView textView = new TextView(mContext);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
                    .getDimensionPixelSize(R.dimen.title_textsize));
            if (i == selectIndex) {
                textView.setTextColor(pressColor);
            } else {
                textView.setTextColor(defColor);
            }

            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(items.get(i));
            textView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    selectIndex = tvContinor.indexOfChild(textView);
                    moveAnim();

                    Intent intent = new Intent();
                    intent.setAction(action);
                    intent.putExtra(INDEX, selectIndex);
                    intent.putExtra(content, items.get(selectIndex));
                    mContext.sendBroadcast(intent);
                    for (int j = 0; j < tvContinor.getChildCount(); j++) {
                        TextView textView = (TextView) tvContinor.getChildAt(j);
                        textView.setTextColor(defColor);
                    }
                    textView.setTextColor(pressColor);
                }
            });
            tvContinor.addView(textView);
        }
    }

    private void moveAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(
                lineView.getTranslationX(), getLineOffset());
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                float animatedValue = (Float) arg0.getAnimatedValue();
                lineView.setTranslationX(animatedValue);
            }
        });

        animator.setDuration(300);
        animator.start();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth == 0) {
            mWidth = w;
            mHeight = h;
            lineWidth = (int) (mWidth / 38f * 8f);
            btLineParam.width = lineWidth;
            lineView.setTranslationX(getLineOffset());
        }
    }

    private float getLineOffset() {
        return (mWidth / items.size() - lineWidth) / 2 + selectIndex * mWidth
                / items.size();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        float childWidthSize = getMeasuredWidth();
        float childHeightSize = childWidthSize / 7.16f;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setDate() {
        items.add("产品人力");
        items.add("考勤状况");
        initView();
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
