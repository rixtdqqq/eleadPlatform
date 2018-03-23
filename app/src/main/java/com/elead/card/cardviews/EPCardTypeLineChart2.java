package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * @desc 质量监督压力测试
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class EPCardTypeLineChart2 extends BaseCard {

    public EPCardTypeLineChart2(Context context) {
        this(context, null);
    }

    public EPCardTypeLineChart2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EPCardTypeLineChart2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.ep_card_type_line_chart2;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
