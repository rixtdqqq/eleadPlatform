package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class ParameterMixCard extends BaseCard implements ICard {
    private TextView tilte;

    public ParameterMixCard(Context context) {
        this(context, null);
    }

    public ParameterMixCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParameterMixCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        tilte.setText(baseCardInfo.cardTitle);
    }

    @Override
    public int initLayout() {
        return R.layout.card_parameter_mixcard_item;
    }

    @Override
    public void initView() {
        tilte = getView(R.id.industrial_title);
    }


}
