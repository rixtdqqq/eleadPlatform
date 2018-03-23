package com.elead.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FixSizeListView extends ListView {

	public FixSizeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FixSizeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixSizeListView(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

		MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
