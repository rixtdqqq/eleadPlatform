package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.elead.application.MyApplication;
import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.BaseLayoutView;
import com.elead.card.views.CalendarItemView;
import com.elead.card.views.CusSpinner;
import com.elead.card.views.ShapArcView;
import com.elead.eplatform.R;

import java.util.Random;

public class MultipleCircleCard extends BaseLayoutView implements ICard {

    private CalendarItemView calendarItemView;
    private int screenWidth;
    private int padding;

    public MultipleCircleCard(Context context) {
        this(context, null);
    }

    public MultipleCircleCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int initLayout() {
        return 0;
    }

    @Override
    public void initView() {
        setOrientation(LinearLayout.VERTICAL);
        screenWidth = MyApplication.size[0];
        padding = (int) (screenWidth / 19f);
        setPadding(0, 0, 0, padding);
    }

    @Override
    public void setDate(BaseCardInfo baseCard) {
        // if (getChildCount() > 1) {
        // return;
        // }
        removeAllViews();

        if (0 != baseCard.isShowTitle || 0 != baseCard.cardDetail.isShowDate) {
            calendarItemView = new CalendarItemView(mContext);
            calendarItemView.setTitle(baseCard.cardTitle);
            if (0 == baseCard.cardDetail.isShowDate) {
                calendarItemView.isShowdate(false);
            }
            addView(calendarItemView);
        }

        int count = baseCard.cardDetail.circleCount;
        if (0 == count) {
            return;
        }

        View view = new View(mContext);
        view.setLayoutParams(new LayoutParams(-1, (int) (0.5 * density)));// 分割线
        view.setBackgroundColor(Color.parseColor("#dedede"));
        addView(view);

        LinearLayout arcContinor = new LinearLayout(mContext);
        arcContinor.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = padding;
        arcContinor.setLayoutParams(layoutParams);

        int childwidth = 0;
        if (count == 1) {
            childwidth = (int) (screenWidth / 38f * 17.07f);
        } else {
            childwidth = (int) (screenWidth / 38f * 10f);
        }

        int margin2 = (screenWidth - count * childwidth) / (count + 1);
        int spinnerMarginTop = (int) (MyApplication.size[0] / 38f * 1f);
        if (null != calendarItemView) {
            // calendarItemView.getTv_left_text().setPadding(margin2, 0, 0, 0);
        }
        LayoutParams childLayoutPrarm = new LayoutParams(childwidth, -2);
        childLayoutPrarm.setMargins(margin2, 0, 0, 0);
        Random r = new Random();
        String[] colors = new String[]{"#3fdec3", "#f9d546", "#2e96c9"};
        String[] stringsTop = new String[]{"有效人天", "已验收", "已付款"};
        String[] stringsBot = new String[]{"4782", "263", "8011394"};
        for (int i = 0; i < count; i++) {
            ShapArcView arcView1 = new ShapArcView(mContext);
            arcView1.init(r.nextFloat(), stringsTop[i], stringsBot[i],
                    colors[i], colors[i]);
            arcView1.setLayoutParams(childLayoutPrarm);
            arcContinor.addView(arcView1);
        }
        RelativeLayout spContinor = null;
        if (0 != baseCard.cardDetail.menuCount) {
            spContinor = new RelativeLayout(mContext);
            RelativeLayout.LayoutParams arcParams = new RelativeLayout.LayoutParams(
                    -1, -2);
            int marginTop = (int) (CusSpinner.DEF_HEIGHT * density + getResources()
                    .getDimension(R.dimen.card_padding_Left))
                    + spinnerMarginTop;
            arcParams.setMargins(0, marginTop, 0, 0);
            arcContinor.setLayoutParams(arcParams);
            spContinor.addView(arcContinor);

            RelativeLayout.LayoutParams splayoutParams = new RelativeLayout.LayoutParams(
                    -1, -2);
            spContinor.setLayoutParams(splayoutParams);
            for (int i = 0; i < baseCard.cardDetail.menuCount; i++) {
                CusSpinner cusSpinner = new CusSpinner(mContext);
                cusSpinner.setText(new String[]{"Mobile" + i, "啊啊啊啊", "阿达达",
                        "阿达达"});
                cusSpinner.setId(i + 9999);
                RelativeLayout.LayoutParams spParams = new RelativeLayout.LayoutParams(
                        -2, -2);
                spParams.setMargins(
                        (int) (MyApplication.size[0] / 38f * 1.62f),
                        spinnerMarginTop, 0, 0);
                if (i > 0) {
                    spParams.addRule(RelativeLayout.END_OF, (i - 1 + 9999));
                }
                spContinor.addView(cusSpinner, spParams);
            }

            addView(spContinor);
        } else {
            addView(arcContinor);
        }
    }

    public void msetDate(BaseCardInfo baseCard) {

    }
}
