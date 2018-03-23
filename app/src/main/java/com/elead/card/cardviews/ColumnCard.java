package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.CalendarItemView;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/4 0004.
 */

public class ColumnCard extends BaseCard {

    private CalendarItemView calendaritemview;

    public ColumnCard(Context context) {
        super(context);
    }

    public ColumnCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.card_delivery_quality_item;
    }

    @Override
    public void initView() {
        calendaritemview = getView(R.id.calendaritemview);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        calendaritemview.setTitle(baseCardInfo.cardTitle);
        calendaritemview.isShowdate(false);
    }
}
