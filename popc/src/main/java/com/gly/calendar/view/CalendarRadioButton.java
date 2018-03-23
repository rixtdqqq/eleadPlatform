package com.gly.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class CalendarRadioButton extends RadioButton {

	int checkColor = 0;
	private Paint linPaint;
	private float density;

	public CalendarRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		checkColor = Color.parseColor("#3BB1EF");
		density = getResources().getDisplayMetrics().density;
		linPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linPaint.setStrokeWidth(2 * density);
		setGravity(Gravity.CENTER);
		setButtonDrawable(null);
		setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
				invalidate();
				if (isCheck) {
					setTextColor(checkColor);

				} else {
					setTextColor(Color.BLACK);
				}
				System.out.println("isCheck: " + isCheck);
			}
		});

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (isChecked()) {
			linPaint.setColor(checkColor);
			float y = getHeight() - linPaint.getStrokeWidth()/2;
			canvas.drawLine(0, y, getWidth(), y, linPaint);
		} else {
			linPaint.setColor(Color.BLACK);
		}

	}

}
