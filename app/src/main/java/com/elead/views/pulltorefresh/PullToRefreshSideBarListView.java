package com.elead.views.pulltorefresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.elead.eplatform.R;
import com.elead.utils.EloadingDialog;
import com.elead.views.Sidebar;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class PullToRefreshSideBarListView extends FrameLayout {
    private PullToRefreshView pulltorefreshview;
    private ListView listview;
    private View emptyview;
    private ErrorView errorview;
    protected Sidebar sidebar;
    private boolean showSiderBar;

    public PullToRefreshSideBarListView(Context context) {
        this(context, null);
    }

    public PullToRefreshSideBarListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshSideBarListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_sidebar_list_view, this);
        listview = (ListView) findViewById(R.id.listview);
        pulltorefreshview = (PullToRefreshView) findViewById(R.id.pulltorefreshview);
        listview.setDivider(new ColorDrawable(Color.TRANSPARENT));
        listview.setDividerHeight(0);
        listview.setFadingEdgeLength(0);
        listview.setOverscrollFooter(new ColorDrawable(Color.TRANSPARENT));
        listview.setOverscrollHeader(new ColorDrawable(Color.TRANSPARENT));
        listview.setOverScrollMode(OVER_SCROLL_NEVER);
        listview.setVerticalScrollBarEnabled(true);

        initSidebar();
    }

    private void initSidebar() {
        sidebar = (Sidebar) findViewById(R.id.sidebar);
        if (!showSiderBar)
            sidebar.setVisibility(View.GONE);
    }

    public void addErrorView() {
        if (null == errorview) {
            errorview = new ErrorView(getContext());
            addView(errorview);
        }
    }

    public void removeErrorView() {
        if (null != errorview) {
            removeView(errorview);
            errorview = null;
        }
    }


    public void addEmptyView() {
        if (null == emptyview) {
            emptyview = new EmptyView(getContext());
            addView(emptyview);
        }
    }

    public void removeEmptyView() {
        if (null != emptyview) {
            removeView(emptyview);
            emptyview = null;
        }
    }

    public void showLoadingView() {
        removeEmptyView();
        removeErrorView();
        EloadingDialog.ShowDialog(getContext());
    }

    public void removeLoadingView() {
        EloadingDialog.cancle();
    }

    public void setLoadingMoreInternal(boolean b) {
        pulltorefreshview.setLoadingMoreInternal(b);
        if (b)
            showLoadingView();
    }

    public void setRefreshingInternal(boolean b) {
        pulltorefreshview.setRefreshingInternal(b);
        if (b)
            showLoadingView();
    }

    public void onRefreshComplete() {
        pulltorefreshview.onRefreshComplete();
        removeLoadingView();
    }

    public void onLoadMoreComplete() {
        pulltorefreshview.onLoadMoreComplete();
        removeLoadingView();
    }


    public void setOnRefreshListener(PullToRefreshView.OnRefreshListener onRefreshListener) {
        pulltorefreshview.setOnRefreshListener(onRefreshListener);
        showLoadingView();
    }

    public void setOnLoadMoreListener(PullToRefreshView.OnLoadMoreListener onLoadMoreListener) {
        pulltorefreshview.setOnLoadMoreListener(onLoadMoreListener);
    }

    public ListView getListview() {
        return listview;
    }

    public void setAdapter(BaseAdapter adapter) {
        listview.setAdapter(adapter);

        if (showSiderBar) {
            sidebar.setListView(listview);
//            sidebar.init(adapter.);
        }
    }

    public void setSpkey(String key) {
        pulltorefreshview.setSpkey(key);
    }

}
