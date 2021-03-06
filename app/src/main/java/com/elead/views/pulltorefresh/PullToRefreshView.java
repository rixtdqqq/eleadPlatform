package com.elead.views.pulltorefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.elead.eplatform.R;
import com.elead.views.RefLoadLayout;

public class PullToRefreshView extends RelativeLayout {

    /**
     * 手指滑动距离与控件移动距离的比例为2:1
     */
    static final float FRICTION = 2.0f;

    /**
     * 显示“下拉刷新”的状态
     */
    static final int PULL_TO_REFRESH = 0x0;
    /**
     * 显示“释放刷新”的状态
     */
    static final int RELEASE_TO_REFRESH = 0x1;
    /**
     * 用户通过下拉进入的刷新状态
     */
    static final int REFRESHING = 0x2;
    /**
     * 用户通过代码强制进入的刷新状态
     */
    static final int MANUAL_REFRESHING = 0x3;

    /**
     * 私有模式，不提供对外调用，
     * 仅用来标示“用户下拉刷新成功后，headerView显示在头部，当用户手指向上滑动时，将headerView跟随用户滑动向上滑动”
     * 及“用户上拉更多成功后，footerView显示在底部，当用户手指向下滑动时，将footerView跟随用户滑动向下滑动”这两个过程的模式
     */
    private static final int MODE_PULL_TO_SCROLL_HEADER_OR_FOOTER = 0x0;
    /**
     * 标示当前支持下拉刷新模式
     */
    public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;
    /**
     * 标示当前支持上拉更多模式
     */
    public static final int MODE_PULL_UP_TO_REFRESH = 0x2;
    /**
     * 标示当前支持下拉刷新和上拉更多两种模式
     */
    public static final int MODE_BOTH = 0x3;

    private Context context;
    /**
     * 滚动对象
     */
    private Scroller scroller;
    /**
     * 判断用户手指的移动距离是否足以响应为move
     */
    private int touchSlop;

    private float initialMotionY;
    private float lastMotionX;
    private float lastMotionY;
    private boolean isBeingDragged = false;

    /**
     * 记录headerView当前的状态
     */
    private int headerState = PULL_TO_REFRESH;
    /**
     * 记录footerView当前的状态
     */
    private int footerState = PULL_TO_REFRESH;
    /**
     * 当前所支持的模式
     */
    private int mode = 3;
    /**
     * 当前处于的模式
     */
    private int currentMode;

    /**
     * 根据不同的mode，contentView所在父View的位置不同，下拉刷新时为1，上拉更多时为1，上拉下拉都支持时为2
     */
    private int index = 1;

    /**
     * 标示当处于刷新状态时，是否需要禁用滑动
     */
    private boolean disableScrollingWhileRefreshing = false;

    /**
     * 标示是否允许滑动刷新
     */
    private boolean isPullToRefreshEnabled = true;

    private RefLoadLayout headerLayout;
    private RefLoadLayout footerLayout;
    private int headerHeight;


    /**
     * 刷新回调接口
     */
    private OnRefreshListener onRefreshListener;

    /**
     * 加载更多回调接口
     */
    private OnLoadMoreListener onLoadMoreListener;

    public PullToRefreshView(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        this.scroller = new Scroller(context);

        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.context = context;
        this.addLoadingView();
    }

    public void setSpkey(String spkey) {
        if (null != headerLayout) {
            headerLayout.setSpKey(spkey);
        }
        if (null != footerLayout) {
            footerLayout.setSpKey(spkey);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) inItLinstener();
    }


    boolean isLastRow = false;

    private void inItLinstener() {
        if (null == onRefreshListener) {
            headerLayout.setVisibility(View.GONE);
        }
        if (null == onLoadMoreListener) {
            footerLayout.setVisibility(View.GONE);
            return;
        }
        View childView = this.getChildAt(index);
        if (childView instanceof ListView) {
            ListView listView = (ListView) childView;
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.d("onScrollStateChanged", scrollState + "");
                    if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && scrollState != SCROLL_STATE_TOUCH_SCROLL) {
                        isLastRow = false;
                        setLoadingMore();
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                        isLastRow = true;
                    } else {
                        isLastRow = false;
                    }

                }
            });
        } else if (childView instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) childView;
        }

    }

    /**
     * @方法描述: 根据当前模式设置，加载头部和底部布局
     */
    public void addLoadingView() {

        String pullDownLabel = context
                .getString(R.string.pull_to_refresh_pull_down_label);
        String refreshingDownLabel = context
                .getString(R.string.pull_to_refresh_refreshing_down_label);
        String releaseDownLabel = context
                .getString(R.string.pull_to_refresh_release_down_label);
        String pullUpLabel = context
                .getString(R.string.pull_to_refresh_pull_up_label);
        String refreshingUpLabel = context
                .getString(R.string.pull_to_refresh_refreshing_up_label);
        String releaseUpLabel = context
                .getString(R.string.pull_to_refresh_release_up_label);

		/* 加载头部和底部View */
        if (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) {
            headerLayout = new RefLoadLayout(context,
                    MODE_PULL_DOWN_TO_REFRESH, releaseDownLabel, pullDownLabel,
                    refreshingDownLabel);
            addView(headerLayout, 0, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            measureView(headerLayout);
            headerHeight = headerLayout.getMeasuredHeight();

        }
        if (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) {
            footerLayout = new RefLoadLayout(context, MODE_PULL_UP_TO_REFRESH,
                    releaseUpLabel, pullUpLabel, refreshingUpLabel);
            LayoutParams lp2 = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            addView(footerLayout, lp2);
            measureView(footerLayout);
            headerHeight = footerLayout.getMeasuredHeight();

        }

		/* 隐藏头部和底部View */
        switch (mode) {
            case MODE_BOTH:
                index = 2;
                setPadding(0, -headerHeight, 0, -headerHeight);
                break;
            case MODE_PULL_UP_TO_REFRESH:
                index = 1;
                setPadding(0, 0, 0, -headerHeight);
                break;
            case MODE_PULL_DOWN_TO_REFRESH:
            default:
                index = 1;
                setPadding(0, -headerHeight, 0, 0);
                break;
        }

    }

    /**
     * 在头部和底部View添加完成后，重新布局，以避免在隐藏headerView和footerView时会把一部分内容（contentView）隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        View contentView = null;
        LayoutParams lp1 = null;
        switch (mode) {
            case MODE_BOTH:
                contentView = this.getChildAt(index);
                lp1 = (LayoutParams) contentView.getLayoutParams();
                lp1.setMargins(0, headerHeight, 0, headerHeight);
                break;
            case MODE_PULL_UP_TO_REFRESH:
                contentView = this.getChildAt(index);
                lp1 = (LayoutParams) contentView.getLayoutParams();
                lp1.setMargins(0, 0, 0, headerHeight);
                break;
            case MODE_PULL_DOWN_TO_REFRESH:
            default:
                contentView = this.getChildAt(index);
                lp1 = (LayoutParams) contentView.getLayoutParams();
                lp1.setMargins(0, headerHeight, 0, 0);
                break;
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @SuppressLint("NewApi")
    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {


        if (!isPullToRefreshEnabled) {
            return false;
        }

        if ((isLoadingMore() || isRefreshing())
                && disableScrollingWhileRefreshing) {
            return true;
        }

        final int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_UP) {
            isBeingDragged = false;
            return false;
        }

        if (action != MotionEvent.ACTION_DOWN && isBeingDragged) {
            return true;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (isReadyForPull()) {
                    lastMotionY = initialMotionY = event.getY();
                    lastMotionX = event.getX();
                    isBeingDragged = false;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (isReadyForPull()) {
                    final float y = event.getY();
                    final float dy = y - lastMotionY;
                    final float yDiff = Math.abs(dy);
                    final float xDiff = Math.abs(event.getX() - lastMotionX);

                    if (yDiff > touchSlop && yDiff > xDiff) {
                        if ((mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH)
                                && dy >= 0.0001f
                                && isReadyForPullDown()
                                && !headerLayout.isRefreshing() && !footerLayout.isRefreshing()) {
                        /* 可以下拉刷新 */
                            lastMotionY = y;
                            isBeingDragged = true;
                            currentMode = MODE_PULL_DOWN_TO_REFRESH;
                        } else if ((mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH)
                                && dy <= 0.0001f
                                && isReadyForPullUp()
                                && !footerLayout.isRefreshing() && !headerLayout.isRefreshing()) {
                        /* 可以上拉更多 */
                            lastMotionY = y;
                            isBeingDragged = true;
                            currentMode = MODE_PULL_UP_TO_REFRESH;
                        }
//                        else if ((isRefreshing() && getScrollY() < 0)
//                                || (isLoadingMore() && getScrollY() > 0)) {
//                        /* 当前headerView或footerView处于显示状态，开启跟随手指滑动模式 */
//                            lastMotionY = y;
//                            isBeingDragged = true;
//                            currentMode = MODE_PULL_TO_SCROLL_HEADER_OR_FOOTER;
//                        }
                    }
                }
            }
            case MotionEvent.ACTION_UP:
                lastScrollY = getChildAt(index).getScrollY();
                break;
        }
        return isBeingDragged;
    }

    private int lastScrollY;

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        if (!isPullToRefreshEnabled) {
            return false;
        }

        if (isRefreshing() && disableScrollingWhileRefreshing) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getEdgeFlags() != 0) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (isReadyForPull()) {
                    lastMotionY = initialMotionY = event.getY();
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (isBeingDragged) {
                    lastMotionY = event.getY();
                    this.pullEvent();
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {


                if (isBeingDragged) {
                    isBeingDragged = false;
                    switch (currentMode) {
                        case MODE_PULL_TO_SCROLL_HEADER_OR_FOOTER:
                    /* 将headerView和footerView隐藏 */
                            // smoothScrollTo(0);
                            break;
                        case MODE_PULL_UP_TO_REFRESH:
                    /* 判断是否激活加载更多 */
                            if (footerState == RELEASE_TO_REFRESH
                                    && null != onLoadMoreListener) {
                                setLoadingMoreInternal(true);
                                onLoadMoreListener.onLoadMore();
                            } else {
                                if (!isRefreshing()) {
                                    smoothScrollTo(0);
                                }
                            }
                            break;
                        case MODE_PULL_DOWN_TO_REFRESH:
                    /* 判断是否激活刷新 */
                            if (headerState == RELEASE_TO_REFRESH
                                    && null != onRefreshListener) {
                                setRefreshingInternal(true);
                                onRefreshListener.onRefresh();

                            } else {
                                if (!isRefreshing()) {
                                    smoothScrollTo(0);
                                }
                            }
                            break;
                    }
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean pullEvent() {

        final int newHeight;
        final int oldHeight = this.getScrollY();
        switch (currentMode) {
            case MODE_PULL_TO_SCROLL_HEADER_OR_FOOTER:
                newHeight = Math.round((initialMotionY - lastMotionY));
                Log.i("newHeight", "1++" + newHeight);
                break;
            case MODE_PULL_UP_TO_REFRESH:
                newHeight = Math.round(Math.max(initialMotionY - lastMotionY, 0)
                        / FRICTION);
                Log.i("newHeight", "2++" + newHeight);
                break;
            case MODE_PULL_DOWN_TO_REFRESH:
            default:
                newHeight = Math.round(Math.min(initialMotionY - lastMotionY, 0)
                        / FRICTION);
                Log.i("newHeight", "3++" + newHeight);
                break;
        }
        if (isRefreshing()) {
            /* 处于刷新状态下，第一次继续下拉，此时headerView已经显示在头部 */
            if (getChildAt(index) instanceof ScrollView
                    && ((ScrollView) getChildAt(index)).getChildAt(0)
                    .getHeight() > getChildAt(index).getHeight()) {
                getChildAt(index).scrollTo(0, lastScrollY + newHeight);
            }
        } else if (isLoadingMore()) {
            /* 处于刷新状态下，第一次继续下拉，此时headerView已经显示在头部 */
            if (getChildAt(index) instanceof ScrollView
                    && ((ScrollView) getChildAt(index)).getChildAt(0)
                    .getHeight() > getChildAt(index).getHeight()) {
                getChildAt(index).scrollTo(
                        0,
                        lastScrollY + newHeight);
            }
        } else {
            scrollTo(newHeight);
        }
        if (newHeight != 0) {
            switch (currentMode) {
                case MODE_PULL_UP_TO_REFRESH:
                    if (footerState == PULL_TO_REFRESH
                            && headerHeight < Math.abs(newHeight)) {
                        footerState = RELEASE_TO_REFRESH;
                        footerLayout.releaseToRefresh();
                        return true;

                    } else if (footerState == RELEASE_TO_REFRESH
                            && headerHeight >= Math.abs(newHeight)) {
                        footerState = PULL_TO_REFRESH;
                        footerLayout.pullToRefresh();
                        return true;
                    }
                    break;
                case MODE_PULL_DOWN_TO_REFRESH:
                    if (headerState == PULL_TO_REFRESH
                            && headerHeight < Math.abs(newHeight)) {
                        headerState = RELEASE_TO_REFRESH;
                        headerLayout.releaseToRefresh();
                        return true;

                    } else if (headerState == RELEASE_TO_REFRESH
                            && headerHeight >= Math.abs(newHeight)) {
                        headerState = PULL_TO_REFRESH;
                        headerLayout.pullToRefresh();
                        return true;
                    }
                    break;
            }
            requestLayout();
        }

        return oldHeight != newHeight;
    }

    /**
     * @方法描述: 判断当前状态是否可以进行上拉更多或下拉刷新的滑动操作
     */
    private boolean isReadyForPull() {
        switch (mode) {
            case MODE_PULL_DOWN_TO_REFRESH:
                return isReadyForPullDown();
            case MODE_PULL_UP_TO_REFRESH:
                return isReadyForPullUp();
            case MODE_BOTH:
                return isReadyForPullUp() || isReadyForPullDown();
        }
        return false;
    }

    /**
     * @方法描述: 判断当前状态是否可以进行下拉刷新操作
     */
    private boolean isReadyForPullDown() {
        // TODO Auto-generated method stub
        if (getChildCount() > 1) {
            View childView = this.getChildAt(index);
            if (childView instanceof ListView) {
                ListView listView = (ListView) childView;
                if (null == listView)
                    return false;
                if (listView.getChildCount() > 0) {
                    int top = listView.getChildAt(0).getTop();
                    int pad = listView.getListPaddingTop();
                    if ((Math.abs(top - pad)) < 3
                            && ((ListView) childView).getFirstVisiblePosition() == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }else {
                    return true;
                }
            } else if (childView instanceof ScrollView) {
                if (((ScrollView) childView).getScrollY() == 0) {
                    return true;
                } else {
                    return false;
                }
            }

        }
        return false;
    }

    /**
     * @方法描述:判断当前状态是否可以上拉更多的滑动操作
     */
    private boolean isReadyForPullUp() {
        View childView = this.getChildAt(index);
        if (childView instanceof ListView) {
            ListView listView = (ListView) childView;
            if (isLastItemVisible(listView)) {
                return true;
            } else {
                return false;
            }
        } else if (childView instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) childView;
            int off = scrollView.getScrollY()
                    + scrollView.getHeight()
                    - scrollView.getChildAt(0).getHeight();
            if (off >= 0) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    protected boolean isLastItemVisible(ListView listView) {
        final Adapter adapter = listView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = listView.getLastVisiblePosition();

        /**
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - listView.getFirstVisiblePosition();
            final int childCount = listView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = listView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= listView.getBottom();
            }
        }

        return false;
    }

    /**
     * @方法描述: 是否允许上拉更多或下拉刷新的滑动操作
     */
    public final boolean isPullToRefreshEnabled() {
        return isPullToRefreshEnabled;
    }

    /**
     * @方法描述: 当处于刷新状态时，是否需要禁用滑动
     */
    public final boolean isDisableScrollingWhileRefreshing() {
        return disableScrollingWhileRefreshing;
    }

    /**
     * @方法描述: 当前正处于刷新中
     */
    public final boolean isRefreshing() {
        return headerState == REFRESHING || headerState == MANUAL_REFRESHING;
    }

    /**
     * @方法描述: 当前正处于加载更多中
     */
    public final boolean isLoadingMore() {
        return footerState == REFRESHING || footerState == MANUAL_REFRESHING;
    }

    /**
     * @方法描述: 设置当处于刷新状态时，是否需要禁用滑动
     */
    public final void setDisableScrollingWhileRefreshing(
            boolean disableScrollingWhileRefreshing) {
        this.disableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
    }

    /**
     * @方法描述: 结束刷新状态
     */
    public final void onRefreshComplete() {
        if (headerState != PULL_TO_REFRESH) {
            resetHeader();

        }
    }

    /**
     * @方法描述: 结束加载更多状态
     */
    public final void onLoadMoreComplete() {
        if (footerState != PULL_TO_REFRESH) {
            resetFooter();
        }
    }

    /**
     * @方法描述: 设置否允许滑动刷新
     */
    public final void setPullToRefreshEnabled(boolean enable) {
        this.isPullToRefreshEnabled = enable;
    }

    /**
     * @方法描述: 强制设置为刷新状态
     */
    public final void setRefreshing() {
        this.setRefreshing(true);
    }

    /**
     * @方法描述: 强制设置为加载更多状态
     */
    public final void setLoadingMore() {
        this.setLoadingMore(true);
    }

    /**
     * @方法描述: 强制设置为刷新状态
     */
    public final void setRefreshing(boolean doScroll) {
        if (!isRefreshing()) {
            setRefreshingInternal(doScroll);
            headerState = MANUAL_REFRESHING;
            if (null != onRefreshListener)
                onRefreshListener.onRefresh();
        }
    }

    /**
     * @方法描述: 强制设置为加载更多状态
     */
    public final void setLoadingMore(boolean doScroll) {
        if (!isLoadingMore()) {
            setLoadingMoreInternal(doScroll);
            footerState = MANUAL_REFRESHING;
            if (null != onLoadMoreListener) {
                onLoadMoreListener.onLoadMore();

            }

        }
    }

    protected final int getCurrentMode() {
        return currentMode;
    }

    protected final RefLoadLayout getFooterLayout() {
        return footerLayout;
    }

    protected final RefLoadLayout getHeaderLayout() {
        return headerLayout;
    }

    protected final int getHeaderHeight() {
        return headerHeight;
    }

    protected final int getMode() {
        return mode;
    }

    /**
     * @方法描述: 重置headerView
     */
    protected void resetHeader() {
        headerState = PULL_TO_REFRESH;
        isBeingDragged = false;

        if (null != headerLayout) {
            headerLayout.reset();
        }

        smoothScrollTo(0);
    }

    /**
     * @方法描述: 重置footerView
     */
    protected void resetFooter() {
        footerState = PULL_TO_REFRESH;
        isBeingDragged = false;

        if (null != footerLayout) {
            footerLayout.reset();
        }

        smoothScrollTo(0);
    }

    /**
     * @方法描述: 强制设置为刷新状态，并显示出headerView
     */
    protected void setRefreshingInternal(boolean doScroll) {
        headerState = REFRESHING;
        if (null != headerLayout) {
            headerLayout.refreshing();
        }
        if (doScroll) {
            smoothScrollTo(-headerHeight);
        }
    }

    /**
     * @方法描述: 强制设置为加载更多状态，并显示出footerView
     */
    protected void setLoadingMoreInternal(boolean doScroll) {
        footerState = REFRESHING;
        if (null != footerLayout) {
            footerLayout.refreshing();
        }
        if (doScroll) {
            smoothScrollTo(headerHeight);
        }
    }

    protected final void scrollTo(int y) {
        scrollTo(0, y);
    }

    protected final void smoothScrollTo(int y) {
        scroller.startScroll(0, getScrollY(), 0, -(getScrollY() - y), 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, this.scroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * @方法描述: 设置下拉刷新回调接口
     */
    public final void setOnRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    /**
     * @方法描述: 设置上拉加载更多回调接口
     */
    public final void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public static interface OnRefreshListener {
        public void onRefresh();
    }

    public static interface OnLoadMoreListener {
        public void onLoadMore();
    }

}
