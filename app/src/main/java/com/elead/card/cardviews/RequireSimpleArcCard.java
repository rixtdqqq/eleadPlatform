package com.elead.card.cardviews;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.elead.application.MyApplication;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.CalendarItemView;
import com.elead.card.views.ShapArcView;
import com.elead.eplatform.R;


public class RequireSimpleArcCard extends BaseCard {

    private ShapArcView stRequireArcView;
    private CalendarItemView stCalendarView;

    public RequireSimpleArcCard(Context context) {
        this(context, null);
    }

    public RequireSimpleArcCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public RequireSimpleArcCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public int initLayout() {
        return R.layout.card_require_simplearc_tab;
    }

    @Override
    public void initView() {
        stRequireArcView = getView(R.id.st_require_arc_view);
        stCalendarView = getView(R.id.st_require_ca_item);
        // measure(0,0);
        stCalendarView.setTitle("需求规范");
        stRequireArcView.getLayoutParams().width = (int) (MyApplication.size[0] / 38f * 17.1f);
        stRequireArcView.init(50, "IT需求实现周期", "35天", "#987ae6", "#987ae6");
//    @Override
//    public void setDate(String dates) {
//
//    }
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}