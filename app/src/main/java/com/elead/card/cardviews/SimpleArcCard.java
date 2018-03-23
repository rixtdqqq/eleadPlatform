package com.elead.card.cardviews;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.elead.application.MyApplication;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.mode.ShapArcInfo;
import com.elead.card.mode.SimpleArcCardInfo;
import com.elead.card.views.CalendarItemView;
import com.elead.card.views.ShapArcView;
import com.elead.utils.IoUtil;

public class SimpleArcCard extends BaseCard {

    public SimpleArcCard(Context context) {
        this(context, null);
    }

    public SimpleArcCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleArcCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDate(String cardData) {
        if (getChildCount() > 1 || TextUtils.isEmpty(cardData)) {
            return;
        }
        setOrientation(LinearLayout.VERTICAL);
        SimpleArcCardInfo simpleArcCardInfo = JSON.parseObject(cardData,
                SimpleArcCardInfo.class);
        int screenWidth = MyApplication.size[0];
        int paddingTop = (int) (screenWidth / 19f);
        setPadding(0, 0, 0, paddingTop);

        if (simpleArcCardInfo.heveTitle) {
            CalendarItemView calendarItemView = new CalendarItemView(mContext);
            calendarItemView.setTitle(simpleArcCardInfo.title);
            addView(calendarItemView);
        }
        LinearLayout arcContinor = new LinearLayout(mContext);
        arcContinor.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = paddingTop;
        arcContinor.setLayoutParams(layoutParams);

        int childwidth = (int) (screenWidth / 38f * 10f);
        if (null == simpleArcCardInfo.shapArcInfos) {
            return;
        }
        int count = simpleArcCardInfo.shapArcInfos.size();
        int margin2 = (screenWidth - count * childwidth) / (count + 1);
        LayoutParams childLayoutPrarm = new LayoutParams(childwidth, -2);
        childLayoutPrarm.setMargins(margin2, 0, 0, 0);
        for (ShapArcInfo shapArcInfo : simpleArcCardInfo.shapArcInfos) {
            ShapArcView arcView1 = new ShapArcView(mContext);
            arcView1.init(shapArcInfo.pencent, shapArcInfo.topText,
                    shapArcInfo.botText, shapArcInfo.arcColor,
                    shapArcInfo.arcColor);
            arcView1.setLayoutParams(childLayoutPrarm);
            arcContinor.addView(arcView1);
        }
        addView(arcContinor);
    }

    @Override
    public int initLayout() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void initView() {
        int random = (int) Math.random();
        switch (random) {
            case 0:
                setDate(IoUtil.getFromAssets(mContext, "simplecarddate1"));
                break;
            case 1:
                setDate(IoUtil.getFromAssets(mContext, "simplecarddate2"));
                break;
            case 2:
                setDate(IoUtil.getFromAssets(mContext, "simplecarddate3"));
                break;
        }

    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
