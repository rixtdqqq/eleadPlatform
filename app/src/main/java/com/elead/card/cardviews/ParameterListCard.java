package com.elead.card.cardviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class ParameterListCard extends LinearLayout implements ICard {
    private final float density;
    private Context mContext;

    public ParameterListCard(Context context) {
        this(context, null);
    }

    public ParameterListCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public ParameterListCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        density = getResources().getDisplayMetrics().density;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        initView();
    }


    public void initView() {
        removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (1 * density), (int) (30 * density));
        int size = 4;
        int lineColor = getResources().getColor(R.color.fontcolors2);
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                View line = new View(mContext);
                line.setLayoutParams(params);
                line.setBackgroundColor(lineColor);
                addView(line);
            }
            View item = LinearLayout.inflate(mContext, R.layout.card_parameterlist_item, null);
            addView(item);
        }
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize / 38f * 10.76f);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
