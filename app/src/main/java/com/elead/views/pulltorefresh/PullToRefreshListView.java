package com.elead.views.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class PullToRefreshListView extends FrameLayout {
    private PullToRefreshView pulltorefreshview;
    private ListView listview;
    private View emptyview;
    private ErrorView errorview;

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_list_view, this);
        listview = (ListView) findViewById(R.id.listview);
        pulltorefreshview = (PullToRefreshView) findViewById(R.id.pulltorefreshview);
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


    public void setLoadingMoreInternal() {
        pulltorefreshview.setLoadingMoreInternal(true);
    }

    public void setRefreshingInternal() {
        pulltorefreshview.setRefreshingInternal(true);
    }

    public void onRefreshComplete() {
        pulltorefreshview.onRefreshComplete();

    }

    public void onLoadMoreComplete() {
        pulltorefreshview.onLoadMoreComplete();

    }


    public void setOnRefreshListener(PullToRefreshView.OnRefreshListener onRefreshListener) {
        pulltorefreshview.setOnRefreshListener(onRefreshListener);
    }

    public void setOnLoadMoreListener(PullToRefreshView.OnLoadMoreListener onLoadMoreListener) {
        pulltorefreshview.setOnLoadMoreListener(onLoadMoreListener);
    }

    public ListView getListview() {
        return listview;
    }

    public void setAdapter(BaseAdapter adapter) {
        listview.setAdapter(adapter);
    }

    public void setSpkey(String key) {
        pulltorefreshview.setSpkey(key);
    }

}
