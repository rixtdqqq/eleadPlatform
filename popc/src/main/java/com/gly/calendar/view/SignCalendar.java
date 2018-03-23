package com.gly.calendar.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gly.calendar.R;
import com.gly.calendar.view.SignCalendarCard.OnCellClickListener;

import java.util.ArrayList;
import java.util.Date;


/**
 * 自定义的日历PopWindow
 *
 * @author tzb
 */
public class SignCalendar extends LinearLayout {


    private SignCalendarCard[] mShowViews;
    /**
     * 用来装日历的ViewPager
     */
    private CustomViewPager mViewPager;

    /**
     * 日历组件适配器
     */
    private CalendarViewAdapter<SignCalendarCard> adapter;
    public int mCurrentIndex = 498;

    private SildeDirection mDirection = SildeDirection.NO_SILDE;

    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;
    }


    /**
     * 上一个月选择按钮
     */
    private TextView preImgBtn;
    /**
     * 下一个月选择按钮
     */
    private TextView nextImgBtn;
    /**
     * 头部显示的年月
     */
    private TextView slide_time;

    /**
     * 日历卡数据
     */
    public SignCalendarCard mShowView;


    private OnCellClickListener listener;

    private Context context;

    public SignCalendar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public void update() {
        mShowViews = adapter.getAllItems();
        mShowViews[mViewPager.getCurrentItem() % mShowViews.length].update();
    }

    public void initView() {
        final CustomDate today = new CustomDate();
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_sign_calendar, this);
        preImgBtn = (TextView) view
                .findViewById(R.id.activity_user_calendar_PreMonthBtn);
        nextImgBtn = (TextView) view
                .findViewById(R.id.activity_user_calendar_NextMonthBtn);
        mViewPager = (CustomViewPager) view
                .findViewById(R.id.activity_user_calendar_Viewpager);
        slide_time = (TextView) view.findViewById(R.id.slide_time);
        LinearLayout continor = (LinearLayout) view.findViewById(R.id.continor);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        preImgBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });
        nextImgBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });


        mShowViews = new SignCalendarCard[3];
        for (int i = 0; i < 3; i++) {
            mShowViews[i] = new SignCalendarCard(context, new OnCellClickListener() {

                @Override
                public void clickDate(CustomDate date) {
                    if (listener != null) {
                        listener.clickDate(date);
                    }
                }

                @Override
                public void changeDate(final CustomDate date) {
                    if (listener != null) {
                        new Handler(context.getMainLooper()).post(new Runnable() {

                            @Override
                            public void run() {
                                // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                                listener.changeDate(date);
                            }
                        });
                    }

                    if (date.month >= today.month && date.year >= today.year) {
                        nextImgBtn.setVisibility(View.GONE);
                    } else {
                        nextImgBtn.setVisibility(View.VISIBLE);
                    }
                    slide_time.setText(date.year + "/" + date.month);
                }
            }, new ArrayList<Date>());
        }
        adapter = new CalendarViewAdapter<>(mShowViews);
        setViewPager();
        slide_time.setText(today.year + "/" + today.month);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = getResources().getDisplayMetrics().density;
        int screenWidth = dm.widthPixels;
        mViewPager.getLayoutParams().height = (int) (screenWidth / 7f * 6f + 10 * density);
        mViewPager.requestLayout();
    }


    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mCurrentIndex);
        mViewPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 计算方向
     *
     * @param position viewpager的位置
     */
    private void measureDirection(int position) {

        if (position > mCurrentIndex) {
            mDirection = SildeDirection.RIGHT;

        } else if (position < mCurrentIndex) {
            mDirection = SildeDirection.LEFT;
        }
        mCurrentIndex = position;
    }

    /**
     * 更新日历视图
     *
     * @param position viewpager的位置
     */
    private void updateCalendarView(int position) {
        mShowViews = adapter.getAllItems();
        if (mDirection == SildeDirection.RIGHT) {
            mShowViews[position % mShowViews.length].rightSlide();
        } else if (mDirection == SildeDirection.LEFT) {
            mShowViews[position % mShowViews.length].leftSlide();
        }
        mDirection = SildeDirection.NO_SILDE;
    }

    public OnCellClickListener getListener() {
        return listener;
    }

    public void setListener(OnCellClickListener listener) {
        this.listener = listener;
    }
}




