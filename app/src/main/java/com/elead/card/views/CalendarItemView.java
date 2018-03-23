package com.elead.card.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.gly.calendar.view.CalendarCard;
import com.gly.calendar.view.PopCalendar;

/**
 * @desc 日历item
 * @auth Created by Administrator on 2016/11/2 0002.
 */

public class CalendarItemView extends RelativeLayout {
    private ImageButton card_calendar_right_img, card_calendar_left_img,
            img_arrow_down;
    private LinearLayout lin_data_continor;
    private Context mContext;
    private int padLeft;
    private TextView title, tv_date;
    private PopCalendar instance;


    public CalendarItemView(Context context) {
        this(context, null);
    }

    public CalendarItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public CalendarItemView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        padLeft = (int) (MyApplication.size[0] / 38f * 1.62f);
        setPadding(padLeft, 0, padLeft, 0);
        initView();
    }

    public void initView() {

        lin_data_continor = (LinearLayout) LinearLayout.inflate(getContext(),
                R.layout.card_title_calendar_item, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                -2, -2);
        params.addRule(CENTER_VERTICAL);
        params.addRule(ALIGN_PARENT_RIGHT);
        addView(lin_data_continor, params);
        card_calendar_right_img = (ImageButton) lin_data_continor
                .findViewById(R.id.card_calendar_right_img);
        card_calendar_left_img = (ImageButton) lin_data_continor
                .findViewById(R.id.card_calendar_left_img);
        img_arrow_down = (ImageButton) lin_data_continor
                .findViewById(R.id.img_arrow_down);

        tv_date = (TextView) lin_data_continor
                .findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instance == null) {
                    instance = new PopCalendar(mContext, v,
                            new PopCalendar.onPopCancleLinstener() {

                                @Override
                                public void onCalendarCheck(
                                        String currShooseTime) {
                                    if (null != currShooseTime) {
                                        tv_date.setText(CalendarCard.checkedStartDay);
                                    }
                                }

                                @Override
                                public void onPopuCancle() {
                                    instance.dismiss();
                                    instance = null;
                                }
                            });
                    instance.showAsDropDown(v, 0, -v.getHeight());
                } else {
                    instance.dismiss();
                    instance = null;
                }
            }
        });

    }

    public void setTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            title = new TextView(mContext);
            title.setText(str);
            title.setTextColor(getResources().getColor(R.color.fontcolors1));
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.card_title_size));
            int v = (int) title.getPaint().measureText(str, 0, str.length());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    v, -2);
            params.addRule(CENTER_VERTICAL);
            addView(title, params);
        }
    }

    public void isShowdate(boolean isShowdate) {
        if (isShowdate) {
            lin_data_continor.setVisibility(View.VISIBLE);
        } else {
            lin_data_continor.setVisibility(View.GONE);
        }
    }

    public View getImg_arrow_left() {
        return card_calendar_left_img;
    }

    public View getImg_arrow_right() {
        return card_calendar_right_img;
    }

    public View getImg_arrow_down() {
        return img_arrow_down;
    }


    @Override
    public int getPaddingLeft() {
        // TODO Auto-generated method stub
        return padLeft;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize / 38f * 5f);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
