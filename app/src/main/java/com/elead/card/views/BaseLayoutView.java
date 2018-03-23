package com.elead.card.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/11/4 0004.
 */

public abstract class BaseLayoutView extends LinearLayout {

    public Context mContext;
    public View mView;
    public float density;

    public BaseLayoutView(Context context) {
        this(context, null);
    }

    public BaseLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public BaseLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        density = mContext.getResources().getDisplayMetrics().density;
        int id = initLayout();
        if (0 != id) {
            mView = LayoutInflater.from(context).inflate(initLayout(), this);
        }
        initView();
    }

    /*public View getView(int id) {
        if (null != mView) {
            return mView.findViewById(id);
        } else {
            return null;
        }
    }*/

    public LinearLayout.LayoutParams getChildLayoutPrarm() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        return layoutParams;
    }

    public abstract int initLayout();

    public abstract void initView();

    private final SparseArray<View> mViews = new SparseArray<View>();;
    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                requestFocus();
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
