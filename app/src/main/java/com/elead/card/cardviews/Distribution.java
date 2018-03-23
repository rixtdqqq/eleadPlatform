package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.mode.JerryChartInfo;
import com.elead.card.views.JerryChartView;
import com.elead.card.views.JerryChartView.ChartStyle;
import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;

public class Distribution extends BaseCard {
    public Distribution(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public Distribution(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private JerryChartView jerryChartView;

    @Override
    public int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.card_distribution;
    }

    @Override
    public void initView() {
        jerryChartView = (JerryChartView) findViewById(R.id.jerryChartView);
        List<JerryChartInfo> datas = new ArrayList<JerryChartInfo>();

        datas.add(new JerryChartInfo(Color.parseColor("#b39ddb"), (float) (Math
                .random() * 3755f) + 200, Color.RED, false, "Visitors"));
        datas.add(new JerryChartInfo(Color.parseColor("#48d5cd"), (float) (Math
                .random() * 9012f) + 200, Color.RED, false, "Registered"));
        datas.add(new JerryChartInfo(Color.parseColor("#fff100"), (float) (Math
                .random() * 1600f) + 200, Color.RED, true, "Bounce"));

        jerryChartView.setData(datas, ChartStyle.ANNULAR);

    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
