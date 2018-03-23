package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * @desc 质量监督温度湿度图
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class EPCardTypeBarChart extends BaseCard {

    public EPCardTypeBarChart(Context context) {
        this(context, null);
    }

    public EPCardTypeBarChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EPCardTypeBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.ep_card_type_bar_chart;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
