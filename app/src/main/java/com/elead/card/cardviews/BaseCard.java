package com.elead.card.cardviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.interfaces.ICard;
import com.elead.card.views.BaseLayoutView;

/**
 * Created by Administrator on 2016/10/18 0018.
 */

public abstract class BaseCard extends BaseLayoutView implements ICard{

    public BaseCard(Context context) {
        this(context, null);
    }

    public BaseCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public BaseCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
