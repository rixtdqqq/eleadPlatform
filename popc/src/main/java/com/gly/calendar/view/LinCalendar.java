package com.gly.calendar.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gly.calendar.R;
import com.gly.calendar.view.CalendarCard.Canendars;
import com.gly.calendar.view.CalendarCard.OnCellClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义的日历PopWindow
 *
 * @author tzb
 */
public class LinCalendar extends LinearLayout {

    /**
     * 用来装日历的ViewPager
     */
    // private static ViewPager mViewPager;

    private static final long ANIM_DURING = 0;

    /**
     * 日历上标注的日期(格式"2015-12-25")
     */
    public static List<Date> liang = new ArrayList<Date>();
    /**
     * 上下文对象
     */
    private Context context;

    public CalendarCard mCalendarCard;
    private RadioGroup group_calandar;
    private TextView tv_month_center, tv_year_center;
    private RelativeLayout layout_month;
    private LinearLayout xingqis;

    private Button btn_cancle;

    private Button btn_sure;

    public LinCalendar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private onSure2CancleLinstener oSureLinstener;

    public void initView() {

        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_calendar, this);

        view.findViewById(R.id.imb_premonth_btn).setOnClickListener(
                clickListener);
        view.findViewById(R.id.imb_nextmonth_btn).setOnClickListener(
                clickListener);

        view.findViewById(R.id.imb_preyear_btn).setOnClickListener(
                clickListener);
        view.findViewById(R.id.imb_nextyear_btn).setOnClickListener(
                clickListener);
        view.findViewById(R.id.clear_date).setOnClickListener(clickListener);

        group_calandar = (RadioGroup) view.findViewById(R.id.group_calandar);
        layout_month = (RelativeLayout) view.findViewById(R.id.layout_month);
        xingqis = (LinearLayout) view.findViewById(R.id.xingqis);

        btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(clickListener);
        btn_sure = (Button) view.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(clickListener);

        tv_month_center = (TextView) view.findViewById(R.id.tv_month_center);
        tv_year_center = (TextView) view.findViewById(R.id.tv_year_center);
        mCalendarCard = (CalendarCard) view.findViewById(R.id.mcalendarcard);
        mCalendarCard.setCellClickListener(new OnCellClickListener() {

            @Override
            public void changeDate(CustomDate date) {
                if (null != onCellClickListener) {
                    onCellClickListener.changeDate(date);
                }
                tv_month_center.setText(date.month + "月");
                tv_year_center.setText(date.year + "年");
            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        switch (CalendarCard.currCanendar) {
            case DAY:
                group_calandar.check(R.id.rb_0);
                break;
            case WEEK:
                group_calandar.check(R.id.rb_1);
                break;
            case MONTH:
                group_calandar.check(R.id.rb_2);
                break;
            case YEAR:
                group_calandar.check(R.id.rb_3);
                break;
        }

        group_calandar
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup arg0, int arg1) {
                        if (arg1 == R.id.rb_0) {
                            CalendarCard.currCanendar = Canendars.DAY;
                            showAnim(layout_month);
                        } else if (arg1 == R.id.rb_1) {
                            CalendarCard.currCanendar = Canendars.WEEK;
                            showAnim(layout_month);
                        } else if (arg1 == R.id.rb_2) {
                            CalendarCard.currCanendar = Canendars.MONTH;
                            goneAnim(layout_month);
                        } else if (arg1== R.id.rb_3) {
                            CalendarCard.currCanendar = Canendars.YEAR;
                            goneAnim(layout_month);
                        }
                        mCalendarCard.update();
                    }
                });

    }

    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imb_premonth_btn) {
                mCalendarCard.preMonth();
            } else if (v.getId() == R.id.imb_nextmonth_btn) {
                mCalendarCard.nextMonth();
            } else if (v.getId() == R.id.imb_preyear_btn) {
                mCalendarCard.preYear();
            } else if (v.getId() == R.id.imb_nextyear_btn) {
                mCalendarCard.nextYear();
            } else if (v.getId() == R.id.clear_date) {
                mCalendarCard.clearDate();
            } else if (v.getId() == R.id.btn_sure) {
                if (null != oSureLinstener) {
                    oSureLinstener.sure(CalendarCard.checkedStartDay,
                            CalendarCard.checkedEndDay);
                }
            } else if (v.getId() == R.id.btn_cancle) {
                if (null != oSureLinstener) {
                    oSureLinstener.cancle();
                }
            }

        }
    };
    private OnCellClickListener onCellClickListener;

    private void showAnim(final View v) {
        if (!v.isShown()) {
            xingqis.setVisibility(View.VISIBLE);
            v.setVisibility(View.VISIBLE);
            xingqis.animate().setDuration(ANIM_DURING).alpha(1);
            v.animate().setDuration(ANIM_DURING).alpha(1)
                    .setListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animator arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator arg0) {
                            v.setAlpha(1);
                            xingqis.setAlpha(1);
                        }

                        @Override
                        public void onAnimationCancel(Animator arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
    }

    private void goneAnim(final View v) {
        if (v.isShown()) {
            xingqis.setAlpha(1);
            xingqis.animate().setDuration(ANIM_DURING).alpha(0);
            v.setAlpha(1);
            v.animate().setDuration(ANIM_DURING).alpha(0)
                    .setListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animator arg0) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator arg0) {
                            v.setVisibility(View.GONE);
                            xingqis.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationCancel(Animator arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
    }

    public void setCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    public interface onSure2CancleLinstener {
        void sure(String startTime, String endTime);

        void cancle();
    }

    public void setOnSureLinstener(onSure2CancleLinstener oSureLinstener) {
        this.oSureLinstener = oSureLinstener;
    }

}
