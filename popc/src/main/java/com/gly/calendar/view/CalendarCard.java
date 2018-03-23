package com.gly.calendar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义日历卡
 * 
 */
public class CalendarCard extends View {

	private static final float RECT_XYP = 6.62f;
	private int TOTAL_COL = 7; // 7列
	private int TOTAL_ROW = 6;

	private Paint mCirclePaint; // 绘制圆形的画笔
	private Paint mTextPaint; // 绘制文本的画笔
	private int mViewWidth; // 视图的宽度
	private float mCellXSpace, mCellYSpace; // 单元格间距
	private Row rows[] = null; // 行数组，每个元素代表一行
	private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
	private OnCellClickListener mCellClickListener; // 单元格点击回调事件
	private int touchSlop; //
	private boolean callBackCellSpace;

	private float mDownX;
	private float mDownY;
	private int col, row;
	public static String checkedStartDay = new CustomDate().toString();
	public static String checkedEndDay = "";

	public List<Date> list = new ArrayList<Date>();

	Calendar now = Calendar.getInstance();

	enum Canendars {
		DAY, WEEK, MONTH, YEAR;
	}

	public static Canendars currCanendar = Canendars.DAY;

	/**
	 * 单元格点击的回调接口
	 * 
	 * @author huangyi
	 * 
	 */
	public interface OnCellClickListener {

		void changeDate(CustomDate date);
	}

	public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CalendarCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalendarCard(Context context) {
		super(context);
		init(context);
	}

	public CalendarCard(Context context, OnCellClickListener listener) {
		super(context);
		this.mCellClickListener = listener;
		init(context);
	}

	public CalendarCard(Context context, List<Date> list) {
		super(context);
		this.list = list;
		init(context);
	}

	private void init(Context context) {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		initDate();
	}

	private void initDate() {
		mShowDate = new CustomDate();
		if ("".equals(checkedStartDay)) {
			checkedStartDay = mShowDate.toString();
		}
		update();
	}

	private void fillDate() {
		rows = new Row[TOTAL_ROW];
		int monthDay = DateUtil.getCurrentMonthDay(); // 今天
		int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
				mShowDate.month - 1); // 上个月的天数
		int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
				mShowDate.month); // 当前月的天数
		int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
				mShowDate.month) - 1;// 选中的天在第几列

		if (firstDayWeek < 0) {
			firstDayWeek = 7 + firstDayWeek;
		}
		boolean isCurrentMonth = false;
		if (DateUtil.isCurrentMonth(mShowDate)) {
			isCurrentMonth = true;
		}
		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL; // 单元格位置
				// 这个月的
				CustomDate modifiDay = null;
				if (position >= firstDayWeek
						&& position < firstDayWeek + currentMonthDays) {
					day++;

					modifiDay = CustomDate.modifiDayForObject(mShowDate, day);
					rows[j].cells[i] = new Cell(modifiDay,
							State.CURRENT_MONTH_DAY, i, j);
					if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
						rows[j].cells[i] = new Cell(modifiDay,
								State.UNREACH_DAY, i, j);
					}
					if (DateUtil.isWeekEndDay(modifiDay.toString())) {
						rows[j].cells[i].state = State.WEEK_END;
					}

					// 过去一个月
				} else if (position < firstDayWeek) {
					modifiDay = new CustomDate(mShowDate.year,
							mShowDate.month - 1, lastMonthDays
									- (firstDayWeek - position - 1));
					rows[j].cells[i] = new Cell(modifiDay,
							State.PAST_MONTH_DAY, i, j);
					// 下个月
				} else if (position >= firstDayWeek + currentMonthDays) {
					modifiDay = new CustomDate(mShowDate.year,
							mShowDate.month + 1, position - firstDayWeek
									- currentMonthDays + 1);
					rows[j].cells[i] = new Cell(modifiDay,
							State.NEXT_MONTH_DAY, i, j);
				}

				if (DateUtil.compareStringData(modifiDay.toString(),
						checkedEndDay) <= 0
						&& DateUtil.compareStringData(modifiDay.toString(),
								checkedStartDay) >= 0
						|| DateUtil.compareStringData(modifiDay.toString(),
								checkedStartDay) == 0) {
					rows[j].cells[i].state = State.CHECKED_DAY;
				}
			}
		}
		if (null != mCellClickListener) {
			mCellClickListener.changeDate(mShowDate);
		}
	}

	private void fillMonth() {
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL;
				CustomDate modifiMonthForObject = CustomDate
						.modifiMonthForObject(mShowDate, position + 1);
				rows[j].cells[i] = new Cell(modifiMonthForObject,
						State.CURRENT_MONTH_DAY, i, j);
				if (DateUtil.compareStringData(modifiMonthForObject.toString(),
						checkedEndDay) <= 0
						&& DateUtil.compareStringData(
								modifiMonthForObject.toString(),
								checkedStartDay) >= 0
						|| DateUtil.compareStringData(
								modifiMonthForObject.toString(),
								checkedStartDay) == 0) {
					System.out.println("modifiMonthForObject: "
							+ modifiMonthForObject);
					rows[j].cells[i].state = State.CHECKED_DAY;

				}
			}
		}
		if (null != mCellClickListener) {
			mCellClickListener.changeDate(mShowDate);
		}
	}

	private void fillQuarter() {
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				CustomDate modifiMonthForObject = CustomDate
						.modifiQuarterForObject(mShowDate, i + 1);
				rows[j].cells[i] = new Cell(modifiMonthForObject,
						State.CURRENT_MONTH_DAY, i, j);
				if (DateUtil.compareStringData(modifiMonthForObject.toString(),
						checkedEndDay) <= 0
						&& DateUtil.compareStringData(
								modifiMonthForObject.toString(),
								checkedStartDay) >= 0
						|| DateUtil.compareStringData(
								modifiMonthForObject.toString(),
								checkedStartDay) == 0) {
					rows[j].cells[i].state = State.CHECKED_DAY;

				}
			}
		}
		if (null != mCellClickListener) {
			mCellClickListener.changeDate(mShowDate);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] != null) {
				rows[i].drawCells(canvas);
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 记录触摸点坐标
			mDownX = event.getX();
			mDownY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			// 偏移量
			float disX = event.getX() - mDownX;
			float disY = event.getY() - mDownY;
			if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
				col = (int) (mDownX / mCellXSpace);
				row = (int) (mDownY / mCellYSpace);
				measureClickCell(col, row);
			}
			break;
		default:
			break;
		}

		return true;
	}

	/**
	 * 计算点击的单元格
	 * 
	 * @param col
	 * @param row
	 */
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= TOTAL_ROW)
			return;
		if (rows[row] != null) {
			CustomDate date = rows[row].cells[col].date;
			Calendar c = Calendar.getInstance();
			date.week = col;
			System.out.println("当前天：" + DateUtil.sf.format(c.getTime()));

			String strClick = date.toString();
			System.out.println("strClick: " + strClick);
			switch (currCanendar) {
			case DAY:
				if (!strClick.equals(checkedStartDay)) {
					checkedStartDay = strClick;
				} else if (!strClick.equals(checkedEndDay)) {
					checkedEndDay = strClick;
				}
				break;
			case WEEK:
				String[] weekByDate = DateUtil.getWeekByDate(strClick);
				checkedStartDay = weekByDate[0];
				checkedEndDay = weekByDate[1];
				break;
			case MONTH:
				try {
					checkedStartDay = DateUtil.sf.format(DateUtil
							.getFirstDayOfMonth(DateUtil.sf.parse(strClick)));
					checkedEndDay = DateUtil.sf.format(DateUtil
							.getLastDayOfMonth(DateUtil.sf.parse(strClick)));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				break;
			case YEAR:
				try {
					checkedStartDay = DateUtil.sf.format(DateUtil
							.getFirstDayOfQuarter(DateUtil.sf.parse(strClick)));
					checkedEndDay = DateUtil.sf.format(DateUtil
							.getLastDayOfQuarter(DateUtil.sf.parse(strClick)));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			System.out.println("checkedDay: " + checkedStartDay
					+ "---checkedEndDay: " + checkedEndDay);
			if (DateUtil.compareStringData(checkedEndDay, checkedStartDay) == -1) {
				String a = checkedStartDay;
				checkedStartDay = checkedEndDay;
				checkedEndDay = a;
			}
			if (null != mCellClickListener) {
				mCellClickListener.changeDate(mShowDate);
			}
			update();
		}
	}

	private static CustomDate str2CusDate(String date) {
		CustomDate startDate = null;
		try {
			String[] split = date.split("-");
			startDate = new CustomDate(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]), Integer.parseInt(split[2]));

		} catch (Exception e) {
		}
		return startDate;
	}

	/**
	 * 组元素
	 * 
	 * @author huangyi
	 * 
	 */
	class Row {
		public int j;

		Row(int j) {
			this.j = j;
		}

		public Cell[] cells = new Cell[TOTAL_COL];// 单元格元素

		// 绘制单元格
		public void drawCells(Canvas canvas) {
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cells[i].drawSelf(canvas);
				}
			}
		}

	}

	/**
	 * 单元格元素
	 * 
	 * @author huangyi
	 * 
	 */
	class Cell {
		public CustomDate date;
		public State state;
		public int i;
		public int j;

		public Cell(CustomDate date, State state, int i, int j) {
			super();
			this.date = date;
			this.state = state;
			this.i = i;
			this.j = j;
		}

		public void drawSelf(Canvas canvas) {

			switch (state) {
			case CHECKED_DAY: // 被选中的天
				customPaint(Style.FILL, Color.parseColor("#3BB1EF"),
						Color.parseColor("#fffffe"), canvas);
				if (currCanendar == Canendars.DAY
						|| currCanendar == Canendars.WEEK) {
					String bottomContent = "";
					if (date.toString().equals(checkedStartDay)) {
						bottomContent = "开始";
					} else if (date.toString().equals(checkedEndDay)) {
						bottomContent = "结束";
					}
					float textHeight = mTextPaint.measureText(bottomContent);
					canvas.drawText(bottomContent, (float) ((i + 0.5)
							* mCellXSpace - textHeight / 2), (float) ((j + 0.6)
							* mCellYSpace + textHeight / 2), mTextPaint);
				}
				break;
			case CURRENT_MONTH_DAY: // 当前月日期
				mTextPaint.setColor(Color.BLACK);
				break;
			case PAST_MONTH_DAY: // 过去一个月
			case NEXT_MONTH_DAY: // 下一个月
				mTextPaint.setColor(Color.parseColor("#C6C6C6"));
				break;
			case UNREACH_DAY: // 还未到的天
				mTextPaint.setColor(Color.BLACK);
				break;
			case TODAY:// 今天
				customPaint(Style.FILL, Color.parseColor("#d0d0d0"),
						Color.parseColor("#fffffe"), canvas);
				break;
			case WEEK_END:// 周末
				mTextPaint.setColor(Color.parseColor("#F5A964"));
				break;
			default:
				break;
			}
			// 绘制文字
			String content = "";

			switch (currCanendar) {
			case DAY:
				content = date.day + "";
				break;
			case WEEK:
				content = date.day + "";
				break;
			case MONTH:
				content = date.month + "月";
				break;
			case YEAR:
				content = date.quarter + "季度";
				break;
			}
			canvas.drawText(content,
					(float) ((i + 0.5) * mCellXSpace - mTextPaint
							.measureText(content) / 2), (float) ((j + 0.6)
							* mCellYSpace - mTextPaint.measureText(content, 0,
							1) / 2), mTextPaint);

		}

		/**
		 * 绘制圆圈样式颜色、文字样式颜色
		 * 
		 * @param cirleStyle
		 * @param cirlePaintColor
		 * @param textPaintColor
		 * @param canvas
		 */
		public void customPaint(Style cirleStyle, int cirlePaintColor,
				int textPaintColor, Canvas canvas) {
			mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mCirclePaint.setAntiAlias(true);
			mCirclePaint.setStyle(cirleStyle);// 圆圈的样式
			mCirclePaint.setColor(cirlePaintColor); // 圆圈的颜色
			mTextPaint.setColor(textPaintColor);// 文字的颜色
			float centerX = (float) (mCellXSpace * (i + 0.5));
			float centerY = (float) ((j + 0.5) * mCellYSpace);
			float xr = mCellXSpace / 2 * 0.95f;
			float yr = mCellYSpace / 2 * 0.95f;

			canvas.drawRoundRect(new RectF(centerX - xr, centerY - yr, centerX
					+ xr, centerY + yr), yr / 5, yr / 5, mCirclePaint);
		}
	}

	/**
	 * 
	 * @author huangyi 单元格的状态 当前月日期，过去的月的日期，下个月的日期
	 */
	enum State {
		TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY, CHECKED_DAY, WEEK_END;
	}

	public static void preXXX() {
		CustomDate startDay = str2CusDate(checkedStartDay);
		CustomDate endDay = str2CusDate(checkedEndDay);
		if (null == endDay || "".equals(endDay)) {
			endDay = new CustomDate(startDay.year, startDay.month, startDay.day);
		}
		switch (currCanendar) {
		case DAY:
			startDay.preDay();
			endDay.preDay();
			break;
		case WEEK:
			startDay.preWeek();
			endDay.preWeek();
			break;
		case MONTH:
			startDay.preMonth();
			endDay.preMonth();

			break;
		case YEAR:
			startDay.preQuarter();
			endDay.preQuarter();
			break;
		}
		checkedStartDay = startDay.toString();
		checkedEndDay = endDay.toString();
	}

	public void preXXXAndUpdate() {
		preXXX();
		update();
	}

	public static void nextXXX() {
		CustomDate startDay = str2CusDate(checkedStartDay);
		CustomDate endDay = str2CusDate(checkedEndDay);
		if (null == endDay || "".equals(endDay)) {
			endDay = startDay;
		}
		switch (currCanendar) {
		case DAY:
			startDay.nextDay();
			endDay.nextDay();
			break;
		case WEEK:
			startDay.nextWeek();
			endDay.nextWeek();
			break;
		case MONTH:
			startDay.nextMonth();
			endDay.nextMonth();

			break;
		case YEAR:
			startDay.nextQuarter();
			endDay.nextQuarter();
			break;
		}
		checkedStartDay = startDay.toString();
		checkedEndDay = endDay.toString();
	}

	public void nextXXXAndUpdate() {
		nextXXX();
		update();
	}

	// 后一天
	public void nextDay() {
		mShowDate.nextDay();
		update();
	}

	// 上周
	public void preWeek() {
		mShowDate.preWeek();
		update();
	}

	// 下周
	public void nextWeek() {
		mShowDate.nextWeek();
		update();
	}

	// 上一个月
	public void preMonth() {
		mShowDate.preMonth();
		update();
	}

	// 下一个月
	public void nextMonth() {
		mShowDate.nextMonth();
		update();
	}

	// 上一个季度
	public void preQuarter() {
		mShowDate.preQuarter();
		update();
	}

	// 下一个季度
	public void nextQuarter() {
		mShowDate.nextQuarter();
		update();
	}

	// 上一年
	public void preYear() {
		mShowDate.preYear();
		update();
	}

	// 下一年
	public void nextYear() {
		mShowDate.nextYear();
		update();
	}

	// 清空日期
	public void clearDate() {
		checkedStartDay = new CustomDate().toString();
		checkedEndDay = checkedStartDay;
		update();
	}

	private void recetRow() {
		rows = new Row[TOTAL_ROW];
	}

	public void update() {
		switch (currCanendar) {
		case DAY:
		case WEEK:
			TOTAL_COL = 7;
			TOTAL_ROW = 6;
			recetRow();
			fillDate();
			break;
		case MONTH:
			TOTAL_COL = 3;
			TOTAL_ROW = 4;
			recetRow();
			fillMonth();
			break;
		case YEAR:
			TOTAL_COL = 4;
			TOTAL_ROW = 1;
			recetRow();
			fillQuarter();
			break;
		}
		invalidate();
		requestLayout();
	}

	public void setCellClickListener(OnCellClickListener mCellClickListener) {
		this.mCellClickListener = mCellClickListener;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		int childWidthSize = getMeasuredWidth();
		mViewWidth = childWidthSize;
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
		switch (currCanendar) {
		case DAY:
		case WEEK:
			mCellYSpace = mCellXSpace = mViewWidth / TOTAL_COL;
			break;
		case MONTH:
		case YEAR:
			mCellXSpace = mViewWidth / TOTAL_COL;
			mCellYSpace = mViewWidth / RECT_XYP;
			break;

		default:
			mCellYSpace = mCellXSpace = mViewWidth / TOTAL_COL;
			break;
		}
		mTextPaint.setTextSize(mCellYSpace / 5.16f);
		int childHeightSize = (int) (mCellYSpace * TOTAL_ROW);
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
