
package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class MoreAppImgCard extends BaseCard {

    public MoreAppImgCard(Context context) {
        super(context);
    }

    public MoreAppImgCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreAppImgCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.card_more_app_img;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
