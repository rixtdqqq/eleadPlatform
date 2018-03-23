package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.BaseLayoutView;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class InfoDesThreePicsCard extends BaseLayoutView implements ICard {
    private TextView tv_title, tv_content;
    private ImageView img_right, img_center, img_left;

    public InfoDesThreePicsCard(Context context) {
        this(context, null);
    }

    public InfoDesThreePicsCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoDesThreePicsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public int initLayout() {
        return R.layout.info_des_three_pics_card;
    }

    @Override
    public void initView() {
        tv_title = getView(R.id.tv_title);
        tv_content = getView(R.id.tv_content);
        img_right = getView(R.id.img_right);
        img_center = getView(R.id.img_center);
        img_left = getView(R.id.img_left);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
