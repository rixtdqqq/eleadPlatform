package com.elead.card.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/10/24 0024.
 */

public class DigitalDealCardView extends LinearLayout {

    private TextView total_tv;
    private TextView bottom_tv;
    private TextView tv_question;
    private float padLeft;
    private View inflate;
    private float botTextSize, topTextSize;
    private float density;

    public DigitalDealCardView(Context context) {
        this(context, null);

    }

    public DigitalDealCardView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public DigitalDealCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }


    private void initView(Context context) {
        density = context.getResources().getDisplayMetrics().density;
        setGravity(Gravity.CENTER);
        setVerticalGravity(Gravity.CENTER_VERTICAL);
        inflate = LinearLayout.inflate(context, R.layout.card_three_item_text, null);
        total_tv = (TextView) inflate.findViewById(R.id.total_tv);
        bottom_tv = (TextView) inflate.findViewById(R.id.bottom_tv);

        addView(inflate);
    }

    public void setDatas(String totalStr, String bottomStr, int bg_id,int color) {
        total_tv.setText(totalStr);
        total_tv.setTextColor(color);
        bottom_tv.setText(addComma(bottomStr));
        setBackgroundResource(bg_id);
    }


    /**
     * 02
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     * 03
     * <p>
     * 04
     *
     * @param str 无逗号的数字
     *            05
     *            <a href="http://home.51cto.com/index.php?s=/space/34010" target="_blank">@return</a> 加上逗号的数字
     *            06
     */

    private static String addComma(String str) {

// 将传进数字反转

        String reverseStr = new StringBuilder(str).reverse().toString();

        String strTemp = "";

        for (int i = 0; i < reverseStr.length(); i++) {

            if (i * 3 + 3 > reverseStr.length()) {

                strTemp += reverseStr.substring(i * 3, reverseStr.length());

                break;
            }

            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";

        }

        // 将[789,456,] 中最后一个[,]去除

        if (strTemp.endsWith(",")) {

            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }

        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();

        return resultStr;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        botTextSize = h/4.8f * 1.13f;
        topTextSize = h/4.8f * 0.78f;
        total_tv.setTextSize(topTextSize / density);
        bottom_tv.setTextSize(botTextSize / density);
       setPadding((int) padLeft, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        float childWidthSize = getMeasuredWidth();
        float childHeightSize = childWidthSize /11f * 4.8f;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) childHeightSize,
                MeasureSpec.EXACTLY);


        padLeft = childWidthSize /8f;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
