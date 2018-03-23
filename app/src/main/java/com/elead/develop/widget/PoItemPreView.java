package com.elead.develop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class PoItemPreView extends LinearLayout {


    private Context mContext;
    private TextView titleToptv;
    private TextView titleBottomTimetv;
    private ImageView imgres;
    private int drawable;
    private String titleTop;
    private String time;
    private String unit;
    private boolean isState;
    private ImageView StateIm;
    private ImageView arrow_right_img;

    public PoItemPreView(Context context) {
        this(context, null);
    }

    public PoItemPreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PoItemPreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
    }


    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.card_po_preview_item, this, true);
        titleToptv = (TextView) findViewById(R.id.title_top_iv);
        titleBottomTimetv = (TextView) findViewById(R.id.title_bottom_iv);
        imgres = (ImageView) findViewById(R.id.imgres_ig);
        StateIm = (ImageView) findViewById(R.id.states_id);
        arrow_right_img = (ImageView) findViewById(R.id.arrow_right_id);

//        titleToptv.setText(titleTop);
//        titleBottomTimetv.setText("提交日期:" + time + "");
//        imgres.setImageResource(drawable);
//        unitTv.setText(unit);
    }


    public void InitData(int drawable, String titleTop, String time, String unit, boolean isState) {
        this.drawable = drawable;
        this.titleTop = titleTop;
        this.time = time;
        this.unit = unit;
        this.isState = isState;

        titleToptv.setText(titleTop);
        titleBottomTimetv.setText("提交日期:" + time + "");
        imgres.setImageResource(drawable);

        if (isState) {
            StateIm.setVisibility(View.GONE);
        } else {
            StateIm.setVisibility(View.VISIBLE);
        }

    }


    public void setColorFont(int TopColor, int bottomColor,int lineColor,int drawSrc) {
        titleToptv.setTextColor(TopColor);
        titleBottomTimetv.setTextColor(bottomColor);
        arrow_right_img.setImageResource(drawSrc);
    }

}
