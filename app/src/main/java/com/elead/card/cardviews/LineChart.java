package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.card.views.CusSpinner;
import com.elead.eplatform.R;

/**
 * @desc
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class LineChart extends LinearLayout {

    private Context mContext;
    public View mView;
    private CusSpinner spinner1, spinner2;

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.reporter_v_line, this);
        initView();
    }

    private void initView(){
        spinner1 = (CusSpinner) mView.findViewById(R.id.card_visits_spinner1);
        spinner1.setText(new String[] { "Month", "嚓嚓嚓", "Wednesday", "Thursday" });

        spinner2 = (CusSpinner) mView.findViewById(R.id.card_visits_spinner2);
        spinner2.setText(new String[] { "Range", "ggg", "Thursday", "Thursday" });
    }
}
