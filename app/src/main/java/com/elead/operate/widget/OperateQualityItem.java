package com.elead.operate.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * @desc 质量
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class OperateQualityItem extends LinearLayout {

    private Context mContext;
    private View mView;

    private TextView tv_1, tv_2, tv_3,tv_4, tv_5, tv_6;

    public TextView getTv_1() {
        return tv_1;
    }

    public TextView getTv_2() {
        return tv_2;
    }

    public TextView getTv_3() {
        return tv_3;
    }

    public TextView getTv_4() {
        return tv_4;
    }

    public TextView getTv_5() {
        return tv_5;
    }

    public TextView getTv_6() {
        return tv_6;
    }

    public OperateQualityItem(Context context) {
        this(context, null);
        mContext = context;
    }

    public OperateQualityItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public OperateQualityItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.operate_quality_item, this);
        init();
    }

    private void init(){
        tv_1 = (TextView) mView.findViewById(R.id.tv_1);
        tv_2 = (TextView) mView.findViewById(R.id.tv_2);
        tv_3 = (TextView) mView.findViewById(R.id.tv_3);
        tv_4 = (TextView) mView.findViewById(R.id.tv_4);
        tv_5 = (TextView) mView.findViewById(R.id.tv_5);
        tv_6 = (TextView) mView.findViewById(R.id.tv_6);
    }
}
