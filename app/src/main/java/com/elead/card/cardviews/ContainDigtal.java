package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.DigitalDealCardView;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class ContainDigtal extends BaseCard {

    private DigitalDealCardView digital1;
    private DigitalDealCardView digital2;
    private DigitalDealCardView digital3;

    public ContainDigtal(Context context) {
        this(context, null);
    }

    public ContainDigtal(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int initLayout() {
        return R.layout.card_contain_three_digtal;
    }

    @Override
    public void initView() {
        digital1 = (DigitalDealCardView) getView(R.id.digital_1);
        digital1.setDatas("Total Sales", 3636 + "", R.drawable.card_digitaldel_1, Color.parseColor("#FA8463"));
        digital2 = (DigitalDealCardView) getView(R.id.digital_2);
        digital2.setDatas("New Orders", 3634 + "", R.drawable.card_digitaldel_2, Color.parseColor("#58D6CD"));
        digital3 = (DigitalDealCardView) getView(R.id.digital_3);
        digital3.setDatas("Qusetion", 3621365 + "", R.drawable.card_digitaldel_3, Color.parseColor("#B098DA"));
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
