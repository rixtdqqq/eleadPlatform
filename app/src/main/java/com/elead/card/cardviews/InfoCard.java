package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.BaseLayoutView;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class InfoCard extends BaseLayoutView implements ICard {

    private TextView tv_title, tv_content;

    public InfoCard(Context context) {
        this(context, null);
    }

    public InfoCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public int initLayout() {
        return R.layout.cardinfo;
    }

    @Override
    public void initView() {
        tv_title = getView(R.id.tv_title);
        tv_content = getView(R.id.tv_content);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        tv_title.setText(baseCardInfo.cardTitle);
    }
}
