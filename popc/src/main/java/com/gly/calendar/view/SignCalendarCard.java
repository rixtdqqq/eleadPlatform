package com.gly.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义日历卡
 */
public class SignCalendarCard extends View {

    private static final int TOTAL_COL = 7; // 7列
    private static final int TOTAL_ROW = 6; // 6行

    private Paint mCirclePaint; // 绘制圆形的画笔
    private Paint mPointPaint; // 绘制圆形的画笔
    private Paint mTextPaint; // 绘制文本的画笔
    private int mViewWidth; // 视图的宽度
    private int mViewHeight; // 视图的高度
    private int mCellSpace; // 单元格间距
    private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行
    public static CustomDate mShowDate; // 自定义的日期，包括year,month,day
    private OnCellClickListener mCellClickListener; // 单元格点击回调事件
    private int touchSlop; //
    private boolean callBackCellSpace;

    private Cell mClickCell;
    private float mDownX;
    private float mDownY;
    private int col, row;
    public static String checkedDay = "";
    private Float density;
    public List<Date> list;

    public static List<String> abnormal = new ArrayList<String>();
    public static List<String> normal = new ArrayList<String>();

    Calendar now = Calendar.getInstance();


    /**
     * 单元格点击的回调接口
     *
     * @author huangyi
     */
    public interface OnCellClickListener {
        void clickDate(CustomDate date); // 回调点击的日期

        void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
    }

    public SignCalendarCard(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SignCalendarCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignCalendarCard(Context context) {
        this(context, null);
    }

    public SignCalendarCard(Context context, OnCellClickListener listener,
                            List<Date> list) {
        super(context);
        density = context.getResources().getDisplayMetrics().density;
        this.mCellClickListener = listener;
        this.list = list;
        init(context);
    }

    private void init(Context context) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Style.FILL);
        initDate();
    }

    private void initDate() {
        mShowDate = new CustomDate();
        fillDate();//
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
                CustomDate modifiDay = new CustomDate();
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
                    if (rows[j].cells[i].date.toString().equals(checkedDay.toString())) {
                        rows[j].cells[i].state = State.CHECKED_DAY;
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

            }
        }
        if (null != mCellClickListener) {
            mCellClickListener.changeDate(mShowDate);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null) {
                rows[i].drawCells(canvas);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        // 单元间隔距离
        // mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth /
        // TOTAL_COL);

        if (!callBackCellSpace) {
            callBackCellSpace = true;
        }
        mTextPaint.setTextSize(mCellSpace / 4);
    }

    /**
     * 手指移动的X坐标
     */
    private float movePositionX;
    /**
     * 手指移动的Y坐标
     */
    private float movePositionY;

    /**
     * 当前是否在执行滑动操作
     */
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
                    col = (int) (mDownX / mCellSpace);
                    row = (int) (mDownY / mCellSpace);
                    measureClickCell(col, row);
                }
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
        if (mClickCell != null) {
            rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
        }
        if (rows[row] != null) {
            mClickCell = new Cell(rows[row].cells[col].date,
                    rows[row].cells[col].state, rows[row].cells[col].i,
                    rows[row].cells[col].j);
            CustomDate date = rows[row].cells[col].date;
            if (State.PAST_MONTH_DAY != rows[row].cells[col].state && State.NEXT_MONTH_DAY != rows[row].cells[col].state) {
                // --------------
//                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                Calendar c = Calendar.getInstance();
                checkedDay = date.toString();
                date.week = col;
                mCellClickListener.clickDate(date);
                update();
            }
        }
    }

    /**
     * 组元素
     *
     * @author huangyi
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
                    customPaint(Style.FILL, Color.parseColor("#2ec7c9"),
                            Color.parseColor("#fffffe"), canvas);
                    break;
                case TODAY:// 今天
                    // customPaint(Paint.Style.FILL, Color.parseColor("#d0d0d0"),
                    // Color.parseColor("#fffffe"), canvas);
                case CURRENT_MONTH_DAY:
                    // 当前月日期
                    mTextPaint.setColor(Color.parseColor("#333333"));
                    float centerX = (float) (mCellSpace * (i + 0.5));
                    float centerY = (float) ((j + 0.5) * mCellSpace);
                    if (abnormal.size() > 0) {
                        for (int i = 0; i < abnormal.size(); i++) {
                            if (abnormal.get(i).equals(date.toString())) {
                                mPointPaint.setColor(Color.parseColor("#ec8181"));
                                canvas.drawCircle(centerX, centerY + mCellSpace / 3f,
                                        3 * density, mPointPaint);
                                break;
                            }
                        }
                    }
                    if (normal.size() > 0) {
                        for (int i = 0; i < normal.size(); i++) {
                            if (normal.get(i).equals(date.toString())) {
                                mPointPaint.setColor(Color.parseColor("#5dd0aa"));
                                canvas.drawCircle(centerX, centerY + mCellSpace / 3f,
                                        3 * density, mPointPaint);
                                break;
                            }
                        }
                    }
                    break;
                case PAST_MONTH_DAY: // 过去一个月
                case NEXT_MONTH_DAY: // 下一个月
                    mTextPaint.setColor(Color.parseColor("#C6C6C6"));
                    break;
                case UNREACH_DAY: // 还未到的天
                    mTextPaint.setColor(Color.BLACK);
                    break;
                case MARK_DAY:// 标记的天
//                    customPaint(Style.STROKE, Color.parseColor("#F39700"),
//                            Color.parseColor("#F39700"), canvas);
                    break;
            }

            // 绘制文字
            String content = date.day + "";

            canvas.drawText(content,
                    (float) ((i + 0.5) * mCellSpace - mTextPaint
                            .measureText(content) / 2), (float) ((j + 0.7)
                            * mCellSpace - mTextPaint
                            .measureText(content, 0, 1) / 2), mTextPaint);

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
            float centerX = (float) (mCellSpace * (i + 0.5));
            float centerY = (float) ((j + 0.5) * mCellSpace);
            float r = mCellSpace / 2 * 0.95f;
            canvas.drawCircle(centerX, centerY + mTextPaint.getTextSize() / 3,
                    r, mCirclePaint);
            if (abnormal.size() > 0) {
                for (int i = 0; i < abnormal.size(); i++) {
                    if (abnormal.get(i).equals(date.toString())) {
                        mPointPaint.setColor(Color.WHITE);
                        canvas.drawCircle(centerX, centerY + mCellSpace / 3f,
                                3 * density, mPointPaint);
                        break;
                    }
                }
            }
            if (normal.size() > 0) {
                for (int i = 0; i < normal.size(); i++) {
                    if (normal.get(i).equals(date.toString())) {
                        mPointPaint.setColor(Color.WHITE);
                        canvas.drawCircle(centerX, centerY + mCellSpace / 3f,
                                3 * density, mPointPaint);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @author huangyi 单元格的状态 当前月日期，过去的月的日期，下个月的日期
     */
    enum State {
        TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY, CHECKED_DAY, MARK_DAY;
    }

    // 从左往右划，上一个月
    public void leftSlide() {

        if (mShowDate.month == 1) {
            mShowDate.month = 12;
            mShowDate.year -= 1;
        } else {
            mShowDate.month -= 1;
        }
        mShowDate.day = 1;
        checkedDay = mShowDate.toString();
        update();
        mCellClickListener.changeDate(mShowDate);
    }

    // 从右往左划，下一个月
    public void rightSlide() {
        if (mShowDate.month == 12) {
            mShowDate.month = 1;
            mShowDate.year += 1;
        } else {
            mShowDate.month += 1;
        }

        CustomDate today = new CustomDate();
        if (today.month == mShowDate.month) {
            mShowDate.day = today.day;
        } else {
            mShowDate.day = 1;
        }
        checkedDay = mShowDate.toString();
        update();
        mCellClickListener.changeDate(mShowDate);
    }

    public void update() {
        fillDate();
        postInvalidate();
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
        mCellSpace = mViewWidth / TOTAL_COL;
        int childHeightSize = mCellSpace * 6 + ((int) (10 * density));
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
