package com.elead.card.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class FlingText extends BaseFlingView {

	public FlingText(Context context) {
		super(context);
		init(FlingType.X, 10000);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		canvas.drawRect(0, 0, 500, 500, paint);

	}

}
