package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.CalendarItemView;
import com.elead.eplatform.R;

/**
 * @desc 特殊刷卡统计View
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class OperateSpecialPunchCard extends BaseCard {

    private CalendarItemView calendarItemView;

    public OperateSpecialPunchCard(Context context) {
        this(context, null);
    }

    public OperateSpecialPunchCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OperateSpecialPunchCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.operate_special_punch_the_card;
    }

    @Override
    public void initView() {
        calendarItemView = (CalendarItemView) mView.findViewById(R.id.calendar);
    }


    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        calendarItemView.setTitle(baseCardInfo.cardTitle);
        calendarItemView.getImg_arrow_left().setVisibility(VISIBLE);
        calendarItemView.getImg_arrow_down().setVisibility(VISIBLE);
        calendarItemView.getImg_arrow_right().setVisibility(VISIBLE);
        calendarItemView.getImg_arrow_down().setVisibility(GONE);
    }
}
