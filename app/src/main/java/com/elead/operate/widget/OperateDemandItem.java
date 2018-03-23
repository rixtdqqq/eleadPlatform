package com.elead.operate.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * @desc 需求
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class OperateDemandItem extends LinearLayout {

    private Context mContext;
    private View mView;

    private TextView tv_left, tv_middle, tv_right;

    public TextView getTv_left() {
        return tv_left;
    }

    public TextView getTv_middle() {
        return tv_middle;
    }

    public TextView getTv_right() {
        return tv_right;
    }

    public OperateDemandItem(Context context) {
        this(context, null);
        mContext = context;
    }

    public OperateDemandItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public OperateDemandItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.operate_demand_item, this);
        init();
    }

    private void init(){
        tv_left = (TextView) mView.findViewById(R.id.tv_left);
        tv_middle = (TextView) mView.findViewById(R.id.tv_middle);
        tv_right = (TextView) mView.findViewById(R.id.tv_right);
    }
}
