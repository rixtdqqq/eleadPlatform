package com.gly.zpower.baseadapter;

import java.util.Calendar;
import java.util.Date;

public class RangeDateUtils {

	/**
	 * 根据当前日期获得所在周的日期区间（周一和周日日期）
	 * 
	 * @return
	 * @author zhaoxuepu
	 * @throws ParseException
	 */
	public static DateRange getThisWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		Date start = cal.getTime();
		cal.add(Calendar.DATE, 6);
		Date end = cal.getTime();
		return new DateRange(start, end);
	}

	/**
	 * 根据当前日期获得上周的日期区间（上周周一和周日日期）
	 * 
	 * @return
	 * @author zhaoxuepu
	 */
	public static DateRange getLastWeek() {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		return new DateRange(calendar1.getTime(), calendar2.getTime());
	}

	/**
	 * 获取date的月份的时间范围
	 * 
	 * @param date
	 * @return
	 */
	public static DateRange getMonthRange(Date date) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(date);
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		setMaxTime(startCalendar);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(date);
		endCalendar.set(Calendar.DAY_OF_MONTH,
				endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setMaxTime(endCalendar);

		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}

	/**
	 * 获取当前季度的时间范围
	 * 
	 * @return current quarter
	 */
	public static DateRange getThisQuarter() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.MONTH,
				((int) startCalendar.get(Calendar.MONTH) / 3) * 3);
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		setMinTime(startCalendar);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.MONTH,
				((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);
		endCalendar.set(Calendar.DAY_OF_MONTH,
				endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setMaxTime(endCalendar);

		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}

	/**
	 * 获取昨天的时间范围
	 * 
	 * @return
	 */
	public static DateRange getYesterdayRange() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DAY_OF_MONTH, -1);
		setMinTime(startCalendar);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);
		setMaxTime(endCalendar);

		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}

	/**
	 * 获取当前月份的时间范围
	 * 
	 * @return
	 */
	public static DateRange getThisMonth() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		setMinTime(startCalendar);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.DAY_OF_MONTH,
				endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setMaxTime(endCalendar);

		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}

	/**
	 * 获取上个月的时间范围
	 * 
	 * @return
	 */
	public static DateRange getLastMonth() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.MONTH, -1);
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		setMinTime(startCalendar);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.MONTH, -1);
		endCalendar.set(Calendar.DAY_OF_MONTH,
				endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setMaxTime(endCalendar);

		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}

	/**
	 * 获取上个季度的时间范围
	 * 
	 * @return
	 */
	public static DateRange getLastQuarter() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.MONTH,
				((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		setMinTime(startCalendar);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.MONTH,
				((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
		endCalendar.set(Calendar.DAY_OF_MONTH,
				endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setMaxTime(endCalendar);

		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}

	private static void setMinTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setMaxTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));
	}

}