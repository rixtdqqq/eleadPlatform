package com.elead.views.pulltorefresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.elead.eplatform.R;


public class EmptyView extends AnimShowView {


    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(
                R.layout.layout_empty, this);
    }
}
