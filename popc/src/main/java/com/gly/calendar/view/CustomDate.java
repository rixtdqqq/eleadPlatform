package com.gly.calendar.view;

import java.io.Serializable;

public class CustomDate implements Serializable {

    private static final long serialVersionUID = 1L;
    public int year;
    public int month;
    public int day;
    public int week;
    public int quarter;

    public CustomDate(int year, int month, int day) {
        if (month > 12) {
            month = 1;
            year++;
        } else if (month < 1) {
            month = 12;
            year--;
        }
        this.year = year;
        this.month = month;
        this.day = day;
        checkQuarter();
    }

    public CustomDate() {
        this.year = DateUtil.getYear();
        this.month = DateUtil.getMonth();
        this.day = DateUtil.getCurrentMonthDay();
    }

    public CustomDate(String str) {
        try {
            String[] a = str.split("-");
            this.year = Integer.parseInt(a[0]);
            this.month = Integer.parseInt(a[1]);
            this.day = Integer.parseInt(a[2]);
        } catch (Exception e) {
        }
    }

    public static CustomDate dateStr2Object(String str) {
        try {
            String[] a = str.split("-");
            return new CustomDate(Integer.parseInt(a[0]),
                    Integer.parseInt(a[1]), Integer.parseInt(a[2]));
        } catch (Exception e) {
            return null;
        }
    }

    public static CustomDate modifiDayForObject(CustomDate date, int day) {
        CustomDate modifiDate = new CustomDate(date.year, date.month, day);
        return modifiDate;
    }

    public static CustomDate modifiMonthForObject(CustomDate date, int month) {
        CustomDate modifiDate = new CustomDate(date.year, month, date.day);
        int monthDays = DateUtil.getMonthDays(date.year, month);
        if (modifiDate.day > monthDays) {
            modifiDate.day = monthDays;
        }
        return modifiDate;
    }

    public static CustomDate modifiQuarterForObject(CustomDate date, int quarter) {
        CustomDate modifiDate = new CustomDate(date.year, date.month, date.day);
        modifiDate.quarter = quarter;
        switch (quarter) {
            case 1:
                modifiDate.month = 1;
                break;
            case 2:
                modifiDate.month = 4;
                break;
            case 3:
                modifiDate.month = 9;
                break;
            case 4:
                modifiDate.month = 12;
                break;
        }
        return modifiDate;
    }

    // 前一天
    public void preDay() {
        if (day == 1) {
            preMonth();
            day = DateUtil.getMonthDays(year, month);
        } else {
            day -= 1;
        }

    }

    // 后一天
    public void nextDay() {
        int monthDays = DateUtil.getMonthDays(year, month);
        if (day >= monthDays) {
            nextMonth();
            day = 1;
        } else {
            day += 1;
        }
    }

    public void preWeek() {
        day -= 7;
        if (day < 1) {
            int monthDays = DateUtil.getMonthDays(year, month - 1);
            preMonth();
            day = monthDays + day;
        }
    }

    public void nextWeek() {
        day += 7;
        int monthDays = DateUtil.getMonthDays(year, month);
        if (day > monthDays) {
            nextMonth();
            day = day - monthDays - 1;
        }
    }

    // 上一个月
    public void preMonth() {
        int thismonthDays = DateUtil.getMonthDays(year, month);
        if (month == 1) {
            month = 12;
            year -= 1;
        } else {
            month -= 1;
        }
        if (day > 27) {
            int premonthDays = DateUtil.getMonthDays(year, month);
            day += premonthDays - thismonthDays;
        }

    }

    // 下一个月
    public void nextMonth() {
        int thismonthDays = DateUtil.getMonthDays(year, month);
        if (month == 12) {
            month = 1;
            year += 1;
        } else {
            month += 1;
        }
        if (day > 27) {
            int nextMonthDays = DateUtil.getMonthDays(year, month);
            day += nextMonthDays - thismonthDays;
        }
    }

    // 上一个季度
    public void preQuarter() {
        preMonth();
        preMonth();
        preMonth();
        checkQuarter();
    }

    // 下一个季度
    public void nextQuarter() {
        nextMonth();
        nextMonth();
        nextMonth();
        checkQuarter();
    }

    private void checkQuarter() {
        if (month <= 3 && month >= 1) {
            quarter = 1;
        } else if (month <= 6 && month >= 4) {
            quarter = 2;
        } else if (month <= 9 && month >= 7) {
            quarter = 3;
        } else if (month <= 12 && month >= 10) {
            quarter = 4;
        }

    }

    // 上一年
    public void preYear() {
        year -= 1;

    }

    // 下一年
    public void nextYear() {
        year += 1;

    }

    @Override
    public String toString() {
        String strDay = "";
        if (day <= 9) {
            strDay = "0" + day;
        } else {
            strDay = "" + day;
        }
        String strMonth = "";
        if (month <= 9) {
            strMonth = "0" + month;
        } else {
            strMonth = "" + month;
        }
        return year + "-" + strMonth + "-" + strDay;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return toString().equals(obj.toString());
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

}
