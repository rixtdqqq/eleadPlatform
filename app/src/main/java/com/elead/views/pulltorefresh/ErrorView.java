package com.elead.views.pulltorefresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.elead.eplatform.R;


public class ErrorView extends AnimShowView {

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(
                R.layout.layout_error, this);
    }
}
