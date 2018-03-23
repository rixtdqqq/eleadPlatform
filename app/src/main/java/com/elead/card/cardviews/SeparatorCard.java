package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;

/**
 * Created by Administrator on 2016/11/15 0015. 分割卡
 */

public class SeparatorCard extends View implements ICard {
	private int height;
	private final float density;

	public SeparatorCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
	}

	@Override
	public void setDate(BaseCardInfo baseCardInfo) {
		height = (int) (baseCardInfo.cardHeight * density);
		if (!TextUtils.isEmpty(baseCardInfo.color)) {
			setBackgroundColor(Color.parseColor("#" + baseCardInfo.color));
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		int childWidthSize = getMeasuredWidth();
		int childHeightSize = height;
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
