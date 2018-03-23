package com.elead.upcard.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * @desc 上下班打卡记录详情
 * @auth Created by Administrator on 2016/11/2 0002.
 */

public class UpCardItemView extends LinearLayout {

    private Context mContext;
    private View mView;
    private View line_top;
    private View line_bottom;
    private TextView tv_up_card_type;
    private TextView tv_up_card_label;
    private TextView tv_up_card_time;
    private TextView tv_up_card_status;
    private TextView tv_up_card_location;
    private ImageView img_location;

    public ImageView getImg_location() {
        return img_location;
    }

    public View getLine_top() {
        return line_top;
    }

    public View getLine_bottom() {
        return line_bottom;
    }

    public TextView getTv_up_card_status() {
        return tv_up_card_status;
    }

    public TextView getTv_up_card_label() {
        return tv_up_card_label;
    }

    public TextView getTv_up_card_time() {
        return tv_up_card_time;
    }

    public UpCardItemView(Context context) {
        this(context, null);
    }

    public UpCardItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpCardItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.up_card_i_details, this);
        initView();
    }

    private void initView() {
        line_top = mView.findViewById(R.id.line_top);
        line_bottom = mView.findViewById(R.id.line_bottom);
        tv_up_card_type = (TextView) mView.findViewById(R.id.tv_up_card_type);
        tv_up_card_label = (TextView) mView.findViewById(R.id.tv_up_card_label);
        tv_up_card_time = (TextView) mView.findViewById(R.id.tv_up_card_time);
        tv_up_card_status = (TextView) mView.findViewById(R.id.tv_up_card_status);
        tv_up_card_location = (TextView) mView.findViewById(R.id.tv_up_card_location);
        img_location = (ImageView) mView.findViewById(R.id.img_location);
    }

    public void setUpCardType(String type) {
        if (!TextUtils.isEmpty(type)) {
            tv_up_card_type.setText(type);
        }
    }

    public void setUpCardLabel(String label) {
        if (!TextUtils.isEmpty(label)) {
            tv_up_card_label.setText(label);
            tv_up_card_status.setTextColor(Color.WHITE);
        } else {
            tv_up_card_status.setTextColor(getResources().getColor(R.color.fontcolors4));
        }
    }

    public void setUpCardTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            tv_up_card_time.setText(time);
        }
    }

    public void setUpCardStatus(String status) {
        if (!TextUtils.isEmpty(status)) {
            tv_up_card_status.setText(status);
        }
    }

    public void setUpCardLocation(String location) {
        if (!TextUtils.isEmpty(location)) {
            tv_up_card_location.setText(location);
        }
    }

    public void isShowLineTop(boolean isShow) {
        if (isShow) {
            line_top.setVisibility(VISIBLE);
        } else {
            line_top.setVisibility(INVISIBLE);
        }
    }

    public void isShowLineBottom(boolean isShow) {
        if (isShow) {
            line_bottom.setVisibility(VISIBLE);
        } else {
            line_bottom.setVisibility(INVISIBLE);
        }
    }
}
