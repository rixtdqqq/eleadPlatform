package com.gly.calendar.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CalendarViewAdapter<V extends View> extends PagerAdapter {
    public static final String TAG = "CalendarViewAdapter";
    public static final int MAX_VALUE = 498;
    private V[] views;

    public CalendarViewAdapter(V[] views) {
        super();
        this.views = views;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if ((container).getChildCount() == views.length) {
            (container).removeView(views[position % views.length]);
        }
        (container).addView(views[position % views.length], 0);
        return views[position % views.length];
    }

    @Override
    public int getCount() {
        return MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container);
    }

    public V[] getAllItems() {
        return views;
    }

}
