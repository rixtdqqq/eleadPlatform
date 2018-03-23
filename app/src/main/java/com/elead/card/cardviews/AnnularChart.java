package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.card.views.CusSpinner;
import com.elead.eplatform.R;

/**
 * @desc 环形图卡
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class AnnularChart extends LinearLayout {

    private Context mContext;
    private View mView;
    private CusSpinner spinner1, spinner2;

    public AnnularChart(Context context) {
        this(context, null);
    }

    public AnnularChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnnularChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.reporter_v_annular, this);
        initView();
    }

    private void initView(){

    }
}
