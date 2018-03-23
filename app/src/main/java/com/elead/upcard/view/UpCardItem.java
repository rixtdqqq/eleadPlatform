package com.elead.upcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.elead.application.MyApplication;
import com.elead.card.cardviews.BaseCard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * @desc 打卡记录
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class UpCardItem extends BaseCard {

    private LinearLayout ll_up_card;
    private UpCardItemView startWork;
    private UpCardItemView endWork;

    public UpCardItemView getStartWork() {
        return startWork;
    }

    public UpCardItemView getEndWork() {
        return endWork;
    }

    public UpCardItem(Context context) {
        this(context, null);
    }

    public UpCardItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpCardItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.up_card_i_view;
    }

    @Override
    public void initView() {
        ll_up_card = getView(R.id.ll_up_card);
        startWork = new UpCardItemView(mContext);
        setItemData(startWork, false, true, "上", "上班打卡");
        ll_up_card.addView(startWork);
        endWork = new UpCardItemView(mContext);
        setItemData(endWork, true, false, "下", "下班打卡");
        ll_up_card.addView(endWork);
        setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (MyApplication.size[1] /3.678f)));
    }

    public void setItemData(UpCardItemView upCardItemView, boolean isShowLineTop, boolean isShowLineBottom, String type,
                            String label) {
        upCardItemView.isShowLineTop(isShowLineTop);
        upCardItemView.isShowLineBottom(isShowLineBottom);
        upCardItemView.setUpCardType(type);
        upCardItemView.setUpCardLabel(label);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
