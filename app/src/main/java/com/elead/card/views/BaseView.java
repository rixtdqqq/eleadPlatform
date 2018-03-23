package com.elead.card.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BaseView extends View {

	public BaseView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public BaseView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setFocusableInTouchMode(true);
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
