package com.elead.pickerview.view;

import android.content.Context;
import android.view.View;

import com.elead.eplatform.R;
import com.elead.pickerview.TimePickerView.Type;
import com.elead.pickerview.adapter.ArrayWheelAdapter;
import com.elead.pickerview.adapter.NumericWheelAdapter;
import com.elead.pickerview.lib.WheelView;
import com.elead.pickerview.listener.OnItemSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.elead.pickerview.TimePickerView.Type.DINFINE;


public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;

    private Type type;
    public static final int DEFULT_START_YEAR = 1990;
    public static final int DEFULT_END_YEAR = 2100;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;
    private ArrayList<String> mListHour = new ArrayList<>();
    private ArrayList<ArrayList<String>> mListMinuns = new ArrayList<>();
    private OnItemSelectedListener wheelListener_option1;

    public WheelTime(View view) {
        super();
        this.view = view;
        type = Type.ALL;
        setView(view);

    }

    public WheelTime(View view, Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker(int year, int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setLabel(context.getString(R.string.pickerview_month));
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel(context.getString(R.string.pickerview_day));
        wv_day.setCurrentItem(day - 1);

        if (type.equals(DINFINE)) {
            setDinfineMode();
        } else {
            wv_hours = (WheelView) view.findViewById(R.id.hour);
            wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
            wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
            wv_hours.setCurrentItem(h);

            wv_mins = (WheelView) view.findViewById(R.id.min);
            wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
            wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
            wv_mins.setCurrentItem(m);
        }
        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem = 30;
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year
                            .getCurrentItem() + startYear) % 100 != 0)
                            || (wv_year.getCurrentItem() + startYear) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 5;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case YEAR_MONTH_DAY_HOUR:
                textSize = textSize * 3;
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
        }
        setTextSize(textSize);

    }

    private void setDinfineMode() {
        Context context = view.getContext();

        wv_hours = (WheelView) view.findViewById(R.id.hour);
        //wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setAdapter(new ArrayWheelAdapter(mListHour));
        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(wv_hours.getCurrentItem());

        wv_mins = (WheelView) view.findViewById(R.id.min);
        //wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        if (mListMinuns != null) {
            wv_mins.setAdapter(new ArrayWheelAdapter(mListMinuns.get(0)));
            wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字

            wv_mins.setCurrentItem(wv_mins.getCurrentItem());
        }


        // 联动监听器
        wheelListener_option1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt2Select = 0;
                if (mListMinuns != null) {
                    opt2Select = wv_mins.getCurrentItem();//上一个opt2的选中位置
                    //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt2Select = opt2Select >= mListMinuns.get(index).size() - 1 ? mListMinuns.get(index).size() - 1 : opt2Select;

                    wv_mins.setAdapter(new ArrayWheelAdapter(mListMinuns
                            .get(index)));
                    wv_mins.setCurrentItem(opt2Select);
                }
//                if (mOptions3Items != null) {
//                    wheelListener_option2.onItemSelected(opt2Select);
//                }
            }
        };

        //		// 添加联动监听
        if (mListMinuns != null && true)
            wv_hours.setOnItemSelectedListener(wheelListener_option1);
    }

    /**
     * 设置时间字体大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        if (type.equals(DINFINE)) {
            sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                    .append((wv_month.getCurrentItem() + 1)).append("-")
                    .append((wv_day.getCurrentItem() + 1)).append(" ")
                    .append(mListHour.get(wv_hours.getCurrentItem())).append(":")
                    .append(mListMinuns.get(wv_hours.getCurrentItem()).get(wv_mins.getCurrentItem()));
        } else {
            sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                    .append((wv_month.getCurrentItem() + 1)).append("-")
                    .append((wv_day.getCurrentItem() + 1)).append(" ")
                    .append(wv_hours.getCurrentItem()).append(":")
                    .append(wv_mins.getCurrentItem());
        }

        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;

        mListHour.add("8");
        mListHour.add("12");
        mListHour.add("18");
        //mListHour.add("9");

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<String>();
        options2Items_01.add("30");
//        options2Items_01.add("佛山");
//        options2Items_01.add("东莞");
//        options2Items_01.add("阳江");
//        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<String>();
        options2Items_02.add("30");
        // options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03 = new ArrayList<String>();
        options2Items_03.add("0");
//
//        ArrayList<String> options2Items_04 = new ArrayList<String>();
//        options2Items_04.add("5");
        mListMinuns.add(options2Items_01);
        mListMinuns.add(options2Items_02);
        mListMinuns.add(options2Items_03);
        // mListMinuns.add(options2Items_04);

    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
