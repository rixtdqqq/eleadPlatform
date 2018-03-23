package com.elead.upcard.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * @desc 打卡记录
 * @auth Created by Administrator on 2016/11/2 0002.
 */

public class UpCardItemChildView extends LinearLayout {

    private Context mContext;
    private View mView;
    private TextView tv_up_card_time;
    private TextView tv_up_card_location;

    public UpCardItemChildView(Context context) {
        this(context, null);
    }

    public UpCardItemChildView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpCardItemChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.up_card_i_details_child, this);
        initView();
    }

    private void initView(){
        tv_up_card_time = (TextView) mView.findViewById(R.id.tv_up_card_time);
        tv_up_card_location = (TextView) mView.findViewById(R.id.tv_up_card_location);
    }

    public void setUpCardTime(String time){
        if (!TextUtils.isEmpty(time)){
            tv_up_card_time.setText(time);
        }
    }

    public void setUpCardLocation(String location){
        if (!TextUtils.isEmpty(location)){
            tv_up_card_location.setText(location);
        }
    }
}
