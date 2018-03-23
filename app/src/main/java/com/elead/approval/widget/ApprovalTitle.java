package com.elead.approval.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class ApprovalTitle extends LinearLayout {

    public TextView tv_tltle;
    public ImageButton imb_back;
    public ImageButton imb_x;

    public ApprovalTitle(Context context) {
        this(context, null);

    }

    public ApprovalTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApprovalTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.title_approval, this);
        tv_tltle = (TextView) inflate.findViewById(R.id.tv_tltle);
        imb_back = (ImageButton) inflate.findViewById(R.id.imb_back);
        imb_x = (ImageButton) inflate.findViewById(R.id.imb_x);
        imb_x.setOnClickListener(onClickListener);
        imb_back.setOnClickListener(onClickListener);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Activity a = (Activity) getContext();
            switch (v.getId()) {
                case R.id.imb_back:
                    a.onBackPressed();
                    break;
                case R.id.imb_x:
                    a.finish();
                    break;

                default:
                    break;
            }
        }
    };

    public void setText(String text) {
        tv_tltle.setText(text);
    }

}
