package com.elead.upcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.upcard.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class HolidayChooseView extends RelativeLayout {


    private Context context;
    private RelativeLayout choose_holiday_rel;
    private View inflate;
    private ImageButton rightButton;
    private TextView letfText;
    private TextView tiaoXiuText;
    private TextView yearsHolidayText;
    private TextView illHolidayText;
    private String tiaoXiuIv;
    private String yearsHolidayIv;
    private String illHolidayIv;
    private ArrayList<String> mList = new ArrayList<String>();

    private TextView tvTime, tvOptions;

    public HolidayChooseView(Context context) {
        this(context, null);
    }

    public HolidayChooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HolidayChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // init();
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.approve_holiday_item, this);
        rightButton = (ImageButton) inflate.findViewById(R.id.bt_ib);
        letfText = (TextView) inflate.findViewById(R.id.left_text_iv);
        tiaoXiuText = (TextView) inflate.findViewById(R.id.tiao_xiu_iv);
        yearsHolidayText = (TextView) inflate.findViewById(R.id.nian_jia_iv);
        illHolidayText = (TextView) inflate.findViewById(R.id.with_ill_holidays_iv);
        choose_holiday_rel = (RelativeLayout) inflate.findViewById(R.id.btn_approval_choose_holiday);
    }


    public void init(String LeftText, int textColor, boolean isClick) {
        letfText.setText(LeftText);
        // letfText.setTextColor(textColor);
        yearsHolidayText.setTextColor(textColor);
        tiaoXiuText.setTextColor(textColor);
        illHolidayText.setTextColor(textColor);
        if (isClick) {
            letfText.setTextColor(textColor);
            rightButton.setVisibility(View.VISIBLE);
            choose_holiday_rel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDailog();
                }
            });

        } else {
            rightButton.setVisibility(View.GONE);
            tiaoXiuText.setText(getResources().getString(R.string.paid_leave) + "(" + tiaoXiuIv + ")");
            yearsHolidayText.setText(getResources().getString(R.string.annual_leave) + "(" + yearsHolidayIv + ")");
            illHolidayText.setText(getResources().getString(R.string.paid_sick_leave) + "(" + illHolidayIv + ")");
        }

    }


    public void setShowText(String tiaoXiuIv, String yearsHolidayIv, String illHolidayIv) {
        this.tiaoXiuIv = tiaoXiuIv;
        this.yearsHolidayIv = yearsHolidayIv;
        this.illHolidayIv = illHolidayIv;
    }


    private void ShowDailog() {
        for (int i = 0; i < 50; i++) {
            mList.add(i * 0.5+"");
        }

        Utils.alertBottomWheelOption(context, mList, new Utils.OnWheelViewClick() {
            @Override
            public void onClickL(View view, int postion) {
                //rightButton.setVisibility(View.GONE);
                tiaoXiuText.setText(getResources().getString(R.string.paid_leave) + "(" + mList.get(postion).substring(0,mList.get(postion).length()) + ")");
                // yearsHolidayText.setText(getResources().getString(R.string.annual_leave) + "(" +mList.get(postion) + ")");
                // illHolidayText.setText(getResources().getString(R.string.paid_sick_leave) + "(" +mList.get(postion) + ")");
            }

            @Override
            public void onClickMiddle(View view, int positonM) {
                yearsHolidayText.setText(getResources().getString(R.string.annual_leave) + "("+mList.get(positonM).substring(0,mList.get(positonM).length()) + ")");
            }

            @Override
            public void onClickR(View view, int positionR) {
                illHolidayText.setText(getResources().getString(R.string.paid_sick_leave) + "("+mList.get(positionR).substring(0,mList.get(positionR).length()) + ")");
            }
        });

    }

}
