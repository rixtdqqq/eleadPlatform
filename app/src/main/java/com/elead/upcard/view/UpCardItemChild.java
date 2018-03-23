package com.elead.upcard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.elead.card.cardviews.BaseCard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * @desc 打卡记录
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class UpCardItemChild extends BaseCard {

    private LinearLayout ll_up_card;
    private UpCardItemView startWork;
    private UpCardItemView endWork;
    private Resources resources;

    public UpCardItemView getStartWork() {
        return startWork;
    }

    public UpCardItemView getEndWork() {
        return endWork;
    }

    public LinearLayout getLl_up_card() {
        return ll_up_card;
    }

    public UpCardItemChild(Context context) {
        this(context, null);
    }

    public UpCardItemChild(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpCardItemChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.up_card_i_view;
    }

    @Override
    public void initView() {
        resources = mContext.getResources();
        ll_up_card = getView(R.id.ll_up_card);
        ll_up_card.setBackgroundColor(Color.WHITE);

        startWork = new UpCardItemView(mContext);
        setTextColor(startWork);
        startWork.getTv_up_card_status().setBackgroundResource(R.drawable.up_card_normal_bg);
        setItemData(startWork, false, true, "上", "上班打卡", "08:32", "正常", "广东省深圳市龙岗区坂田街道坂田吉华路592号");
        ll_up_card.addView(startWork);

        UpCardItemChildView childView = new UpCardItemChildView(mContext);
        setItemChildData(childView, "14:54", "广东省深圳市龙岗区坂田街道黄君山山海广场");
        ll_up_card.addView(childView);

        endWork = new UpCardItemView(mContext);
        setTextColor(endWork);
        endWork.getTv_up_card_status().setBackgroundResource(R.drawable.up_card_abnormal_bg);
        setItemData(endWork, true, false, "下", "下班打卡", "17:54", "早退", "广东省深圳市龙岗区坂田街道黄君山山海广场");
        ll_up_card.addView(endWork);
    }

    public void setItemData(UpCardItemView upCardItemView, boolean isShowLineTop, boolean isShowLineBottom, String type,
                             String label, String time, String status, String location){
        upCardItemView.isShowLineTop(isShowLineTop);
        upCardItemView.isShowLineBottom(isShowLineBottom);
        upCardItemView.setUpCardType(type);
        upCardItemView.setUpCardLabel(label);
        upCardItemView.setUpCardTime(time);
        upCardItemView.setUpCardStatus(status);
        upCardItemView.setUpCardLocation(location);
    }

    public void setItemChildData(UpCardItemChildView upCardItemChildView, String time, String location){
        upCardItemChildView.setUpCardTime(time);
        upCardItemChildView.setUpCardLocation(location);
    }

    public void setTextColor(UpCardItemView upCardItemView){
        upCardItemView.getTv_up_card_label().setTextColor(resources.getColor(R.color.up_card_212121));
        upCardItemView.getTv_up_card_time().setTextColor(resources.getColor(R.color.up_card_212121));
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
