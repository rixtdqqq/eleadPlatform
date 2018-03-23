package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.CalendarItemView;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/4 0004.
 */

public class HorizonProgressCard extends BaseCard {
    private CalendarItemView calendarItemView;

    public HorizonProgressCard(Context context) {
        this(context, null);
    }

    public HorizonProgressCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizonProgressCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.list_item_po_f_survey;
    }

    @Override
    public void initView() {

    }


    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        calendarItemView = (CalendarItemView) mView.findViewById(R.id.calendar);
        calendarItemView.setTitle(baseCardInfo.cardTitle);
        calendarItemView.getImg_arrow_left().setVisibility(GONE);
        calendarItemView.getImg_arrow_right().setVisibility(GONE);
    }
}
