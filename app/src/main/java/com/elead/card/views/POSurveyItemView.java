package com.elead.card.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.eplatform.R;

/**
 * @desc
 * @auth Created by Administrator on 2016/11/2 0002.
 */

public class POSurveyItemView extends LinearLayout {

    private Context mContext;
    private View mView;

    public POSurveyItemView(Context context) {
        this(context, null);
        mContext = context;
    }

    public POSurveyItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public POSurveyItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.card_survey_item, this);
    }
}
