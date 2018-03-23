package com.elead.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21 0021.
 */

public class PagerViewAdapter extends PagerAdapter {

    private final List<View> mViews;
    private final Context mContext;

    public PagerViewAdapter(Context context, List<View> mViews) {
       this.mContext =context;
        this.mViews=mViews;
    }


    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }
}
