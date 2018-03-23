package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.BaseLayoutView;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class InfoChartCard extends BaseLayoutView implements ICard {
    public InfoChartCard(Context context) {
        this(context, null);
    }

    public InfoChartCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoChartCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }

    @Override
    public int initLayout() {
        return R.layout.card_info_chart;
    }

    @Override
    public void initView() {

    }
}
