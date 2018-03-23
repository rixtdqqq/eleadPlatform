package com.elead.qs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.elead.eplatform.R;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class SanjiaoTextView extends View {

    private final float density;
    private Paint paint;
    private String text;

    public SanjiaoTextView(Context context) {
        this(context, null);
    }

    public SanjiaoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SanjiaoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.drawable.project_tip_bg);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextAlign(Paint.Align.CENTER);
        density = getResources().getDisplayMetrics().density;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getWidth() * 0.75f, getHeight() * 0.25f + paint.getTextSize() / 2f, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paint.setTextSize(h / 1.8f);
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }
}
