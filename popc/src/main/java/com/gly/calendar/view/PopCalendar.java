package com.gly.calendar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.gly.calendar.R;
import com.gly.calendar.view.CalendarCard.Canendars;
import com.gly.calendar.view.LinCalendar.onSure2CancleLinstener;
import com.gly.calendar.view.SimpleChooseLin.onChooseLinstener;

import java.text.ParseException;

/**
 * 自定义的日历PopWindow
 * 
 * @author tzb
 * 
 */
public class PopCalendar extends PopupWindow {

	private Context context;

	private View parent;
	private onPopCancleLinstener onPopCancleLinstener;
	private FrameLayout mContainer;
	public LinCalendar linCalendar;

	public PopCalendar(Context context) {
		super(context);
		this.context = context;
	}

	public PopCalendar(Context context, View parent,
			onPopCancleLinstener onPopCancleLinstener) {
		this(context, parent);
		this.onPopCancleLinstener = onPopCancleLinstener;
	}

	@SuppressLint("InflateParams")
	public PopCalendar(Context context, View parent) {
		super(WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		this.parent = parent;
		this.context = context;
		View view = LinearLayout.inflate(context, R.layout.popu_layout, null);
		setContentView(view);

		mContainer = (FrameLayout) view.findViewById(R.id.mContainer);

		showSimChooseLayout();
		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		// setFocusable(true);

		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xffffffff);
		setFocusable(false);
//		setOutsideTouchable(true);
		setBackgroundDrawable(dw);
		
		// 设置popWindow的显示和消失动画
		setAnimationStyle(R.style.mypopwindow_anim_style);
		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (null != onPopCancleLinstener) {
					// String str = "";
					// if (null == linCalendar) {
					// str = DateUtil.sf.format(new Date()).replace("-", ".");
					// } else {
					// str = (linCalendar.mCalendarCard.checkedDay + "~" +
					// linCalendar.mCalendarCard.checkedEndDay)
					// .replace("-", ".");
					// }
					onPopCancleLinstener.onPopuCancle();
				}

			}
		});
		// 在底部显示
		// View parent = activity.findViewById(R.id.contact_main);
		// window.showAtLocation(parent, GRAVITY_FOR_POP, X_LOCATION,
		// Y_LOCATION);
		// popWindow消失监听方法
	}

	private void showSimChooseLayout() {
		mContainer.removeAllViews();
		mContainer.requestLayout();
		SimpleChooseLin simpleChooseLin = new SimpleChooseLin(context,
				new onChooseLinstener() {

					@Override
					public void onCusScopeClick() {
						showCalendatView();
					}

					@Override
					public void onCheckChange(int position) {
						CustomDate date = null;
						switch (position) {
						case 0:
							CalendarCard.currCanendar = Canendars.DAY;
							CalendarCard.checkedStartDay = CalendarCard.checkedEndDay = getTodayDate();
							onPopCancleLinstener.onCalendarCheck("今日("
									+ CalendarCard.checkedStartDay + ")");

							break;
						case 1:
							CalendarCard.currCanendar = Canendars.DAY;
							date = new CustomDate();
							date.preDay();
							CalendarCard.checkedStartDay = CalendarCard.checkedEndDay = getYestodayDate();
							onPopCancleLinstener.onCalendarCheck("昨日("
									+ CalendarCard.checkedStartDay + ")");
							break;
						case 2:
							CalendarCard.currCanendar = Canendars.WEEK;
							String[] weekByDate = getThisWeekDate();
							CalendarCard.checkedStartDay = weekByDate[0];
							CalendarCard.checkedEndDay = weekByDate[1];
							onPopCancleLinstener.onCalendarCheck("本周("
									+ getTopText() + ")");
							break;
						case 3:
							CalendarCard.currCanendar = Canendars.WEEK;
							String[] preweekByDate = getLastWeekDate();
							CalendarCard.checkedStartDay = preweekByDate[0];
							CalendarCard.checkedEndDay = preweekByDate[1];
							onPopCancleLinstener.onCalendarCheck("上周("
									+ getTopText() + ")");
							break;
						case 4:
							CalendarCard.currCanendar = Canendars.MONTH;
							String[] thisMonthByDate = getThisMonthDate();
							CalendarCard.checkedStartDay = thisMonthByDate[0];
							CalendarCard.checkedEndDay = thisMonthByDate[1];
							onPopCancleLinstener.onCalendarCheck("本月("
									+ getTopText() + ")");
							break;
						case 5:
							CalendarCard.currCanendar = Canendars.MONTH;
							date = new CustomDate();
							date.preMonth();
							try {
								CalendarCard.checkedStartDay = DateUtil.sf.format(DateUtil
										.getFirstDayOfMonth(DateUtil.sf
												.parse(date.toString())));
								CalendarCard.checkedEndDay = DateUtil.sf.format(DateUtil
										.getLastDayOfMonth(DateUtil.sf
												.parse(date.toString())));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							onPopCancleLinstener.onCalendarCheck("上月("
									+ getTopText() + ")");
							break;
						}

					}

					@Override
					public void onCloseClick() {
						dismiss();
					}
				});
		mContainer.addView(simpleChooseLin);
	}

	protected String getTopText() {
		return (CalendarCard.checkedStartDay + "~" + CalendarCard.checkedEndDay)
				.replace("-", ".");
	}

	private void showCalendatView() {
		mContainer.removeAllViews();
		mContainer.requestLayout();
		linCalendar = new LinCalendar(context);
		mContainer.addView(linCalendar);

		linCalendar.setOnSureLinstener(new onSure2CancleLinstener() {

			@Override
			public void sure(String startTime, String endTime) {
				if (null != onPopCancleLinstener) {
					onPopCancleLinstener.onCalendarCheck(null);
				}
				dismiss();

			}

			@Override
			public void cancle() {
				showSimChooseLayout();
			}
		});
	}

	public interface onPopCancleLinstener {
		void onCalendarCheck(String currShooseTime);
		void onPopuCancle();
	}

	/**
	 * 显示popWindow
	 */
	public void show() {
		showAsDropDown(parent);
	}

	/**
	 * 
	 */
	public static String[] getLastMonthDate() {
		CustomDate date = new CustomDate();
		date.preMonth();
		String dates[] = new String[2];
		try {
			dates[0] = DateUtil.sf.format(DateUtil
					.getFirstDayOfMonth(DateUtil.sf.parse(date.toString())));
			dates[1] = DateUtil.sf.format(DateUtil
					.getLastDayOfMonth(DateUtil.sf.parse(date.toString())));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dates;
	}

	/**
	 * 
	 */
	public static String[] getThisMonthDate() {
		CustomDate date = new CustomDate();
		String dates[] = new String[2];
		try {
			dates[0] = DateUtil.sf.format(DateUtil
					.getFirstDayOfMonth(DateUtil.sf.parse(date.toString())));
			dates[1] = DateUtil.sf.format(DateUtil
					.getLastDayOfMonth(DateUtil.sf.parse(date.toString())));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dates;
	}

	/**
	 * 
	 */
	public static String[] getLastWeekDate() {
		CustomDate date = new CustomDate();
		date.preWeek();
		String[] preweekByDate = DateUtil.getWeekByDate(date.toString());
		return preweekByDate;
	}

	/**
	 * 
	 */
	public static String[] getThisWeekDate() {
		CustomDate date = new CustomDate();
		String[] weekByDate = DateUtil.getWeekByDate(date.toString());
		return weekByDate;
	}

	/**
	 * 
	 */
	public static String getYestodayDate() {
		CustomDate date = new CustomDate();
		date.preDay();
		return date.toString();
	}

	public static String getTodayDate() {
		CustomDate date = new CustomDate();
		return date.toString();
	}
}
