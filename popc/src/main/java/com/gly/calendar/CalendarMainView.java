package com.gly.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gly.calendar.view.CalendarCard;
import com.gly.calendar.view.PopCalendar;
import com.gly.calendar.view.PopCalendar.onPopCancleLinstener;

/**
 * @author tzb
 */
public class CalendarMainView extends LinearLayout {

    public Context mContext;
    public View mView;
    public float density;
    private PopCalendar instance;
    private TextView slide_time;

    public CalendarMainView(Context context) {
        this(context, null);
    }

    public CalendarMainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public CalendarMainView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        density = mContext.getResources().getDisplayMetrics().density;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.calendar_main, this);
        slide_time = (TextView) findViewById(R.id.slide_time);
        slide_time.setText("今日(" + CalendarCard.checkedStartDay + ")");
        findViewById(R.id.imb_predaybtn).setOnClickListener(clickListener);
        findViewById(R.id.imb_nextdaybtn).setOnClickListener(clickListener);
        findViewById(R.id.top_centor_layout).setOnClickListener(clickListener);
    }

    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.imb_predaybtn) {
                if (null != instance && null != instance.linCalendar
                        && null != instance.linCalendar.mCalendarCard) {
                    instance.linCalendar.mCalendarCard.preXXXAndUpdate();
                } else {
                    CalendarCard.preXXX();
                }
                slide_time.setText(getTopText());
            } else if (v.getId() == R.id.imb_nextdaybtn) {
                if (null != instance && null != instance.linCalendar
                        && null != instance.linCalendar.mCalendarCard) {
                    instance.linCalendar.mCalendarCard.nextXXXAndUpdate();
                } else {
                    CalendarCard.nextXXX();
                }
                slide_time.setText(getTopText());
            } else if (v.getId() == R.id.top_centor_layout) {
                if (instance == null) {
                    instance = new PopCalendar(mContext, v,
                            new onPopCancleLinstener() {

                                @Override
                                public void onCalendarCheck(
                                        String currShooseTime) {
                                    if (null == currShooseTime) {
                                        slide_time.setText(getTopText());
                                    } else {
                                        slide_time.setText(currShooseTime);
                                    }
                                }

                                @Override
                                public void onPopuCancle() {
                                    instance.dismiss();
                                    instance = null;
                                }
                            });
                    instance.show();
                } else {
                    instance.dismiss();
                    instance = null;
                }
            }
        }
    };

    public static String getTopText() {
        String start = CalendarCard.checkedStartDay;
        String end = CalendarCard.checkedEndDay;
        String todayDate = PopCalendar.getTodayDate();
        String yestodayDate = PopCalendar.getYestodayDate();
        String[] lastWeekDates = PopCalendar.getLastWeekDate();
        String lastWeekStr = lastWeekDates[0] + lastWeekDates[1];
        String[] thisWeekDates = PopCalendar.getThisWeekDate();
        String thisWeekStr = thisWeekDates[0] + thisWeekDates[1];
        String[] lastMonthDates = PopCalendar.getLastMonthDate();
        String lastMonthStr = lastMonthDates[0] + lastMonthDates[1];
        String[] thisMonthDates = PopCalendar.getThisMonthDate();
        String thisMonthStr = thisMonthDates[0] + thisMonthDates[1];
        String a = "";
        String b = ")";
        if (todayDate.equals(start) && todayDate.endsWith(end)) {
            a = "今日(";
        } else if (yestodayDate.equals(start) && yestodayDate.endsWith(end)) {
            a = "昨日(";
        } else if (lastWeekStr.contains(start) && lastWeekStr.contains(end)) {
            a = "上周(";
        } else if (thisWeekStr.contains(start) && thisWeekStr.contains(end)) {
            a = "本周(";
        } else if (lastMonthStr.contains(start) && lastMonthStr.contains(end)) {
            a = "上月(";
        } else if (thisMonthStr.contains(start) && thisMonthStr.contains(end)) {
            a = "本月(";
        } else {
            a = b = "";
        }

        boolean equals = CalendarCard.checkedStartDay
                .equals(CalendarCard.checkedEndDay);
        if (equals) {
            return a + CalendarCard.checkedStartDay.replace("-", ".") + b;
        } else if (!equals) {
            return a
                    + (CalendarCard.checkedStartDay + "~" + CalendarCard.checkedEndDay)
                    .replace("-", ".") + b;
        } else {
            return "";
        }
    }
}
