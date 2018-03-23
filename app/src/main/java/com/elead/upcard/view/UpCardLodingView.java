package com.elead.upcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;

/**
 * @desc 加载
 * @auth Created by Administrator on 2016/11/2 0002.
 */

public class UpCardLodingView extends LinearLayout {

    private Context mContext;
    private View mView;
    private ProgressBar progress_bar;
    private TextView tv_no_data;

    public ProgressBar getProgress_bar() {
        return progress_bar;
    }

    public TextView getTv_no_data() {
        return tv_no_data;
    }

    public UpCardLodingView(Context context) {
        this(context, null);
    }

    public UpCardLodingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpCardLodingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.up_card_loding_or_empty, this);
        initView();
      setLayoutParams(new LinearLayout.LayoutParams(-1,(int) (MyApplication.size[1]/3.678f)));
    }

    private void initView() {
        progress_bar = (ProgressBar) mView.findViewById(R.id.progress_bar);
        tv_no_data = (TextView) mView.findViewById(R.id.tv_no_data);
    }
}
