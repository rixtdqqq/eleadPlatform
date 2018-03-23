package com.elead.upcard.view;

import android.content.Context;
import android.graphics.Color;
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

public class SignTbSelector extends RelativeLayout implements ICard {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int pressTvColor = Color.parseColor("#7bd7d8");
    private final int defTvColor = Color.parseColor("#999999");
    private final int lineColor = Color.parseColor("#2ec7c9");
    public static final String action = SignTbSelector.class.getSimpleName();
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
    private LayoutParams btLineParam;
    private onTbSelectLinstener onTbSelectLinstener;

    public SignTbSelector(Context context) {
        this(context, null);
    }

    public SignTbSelector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignTbSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        density = mContext.getResources().getDisplayMetrics().density;
        tvContinor = new LinearLayout(mContext);
        tvContinor.setLayoutParams(new LayoutParams(-1, -1));
        tvContinor.setOrientation(LinearLayout.HORIZONTAL);
        addView(tvContinor);

        lineView = new View(mContext);
        lineView.setBackgroundColor(lineColor);
        btLineParam = new LayoutParams(0, (int) (3 * density));
        btLineParam.addRule(ALIGN_PARENT_BOTTOM);
        addView(lineView, btLineParam);
    }


    public void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        params.weight = 1;

        tvContinor.removeAllViews();
        for (int i = 0; i < items.size(); i++) {
            final TextView textView = new TextView(mContext);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
                    .getDimensionPixelSize(R.dimen.px42));
            if (i == selectIndex) {
                textView.setTextColor(pressTvColor);
            } else {
                textView.setTextColor(defTvColor);
            }

            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setText(items.get(i));
            textView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    selectIndex = tvContinor.indexOfChild(textView);
                    if (null != onTbSelectLinstener) {
                        onTbSelectLinstener.onSelector(selectIndex);
                    }
                    for (int j = 0; j < tvContinor.getChildCount(); j++) {
                        TextView textView = (TextView) tvContinor.getChildAt(j);
                        textView.setTextColor(defTvColor);
                    }
                    textView.setTextColor(pressTvColor);
                }
            });
            tvContinor.addView(textView);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth == 0) {
            mWidth = w;
            mHeight = h;
            if (items.size() > 2) {
                lineWidth = (int) (mWidth / items.size() * 0.6f);
            } else {
                lineWidth = (int) (80 * density);
            }
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
        float childHeightSize = childWidthSize / 9f;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setDate(String... strings) {
        for (String str : strings) {
            items.add(str);
        }
        initView();
    }


    public interface onTbSelectLinstener {
        void onSelector(int position);
    }

    public void setOnTbSelectLinstener(SignTbSelector.onTbSelectLinstener onTbSelectLinstener) {
        this.onTbSelectLinstener = onTbSelectLinstener;
    }

    public void setOffest(int position, float positionOffset) {
        selectIndex = position;
        if (positionOffset > 0 && positionOffset < 1) {
            float itemWidth = mWidth / items.size();
            float offset = (itemWidth - lineWidth) / 2 + position * itemWidth + itemWidth * positionOffset;
            lineView.setTranslationX(offset);
        }
    }

    public void setCurrItem(int position) {
        selectIndex = position;
        for (int j = 0; j < tvContinor.getChildCount(); j++) {
            TextView textView = (TextView) tvContinor.getChildAt(j);
            textView.setTextColor(defTvColor);
        }
        TextView textView = (TextView) tvContinor.getChildAt(position);
        if (null != textView)
            textView.setTextColor(pressTvColor);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
