package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.mode.WaveDate;
import com.elead.card.mode.WaveDate.WaveInfo;
import com.elead.card.views.BaseWaveView;
import com.elead.card.views.BaseWaveView.Mstyle;
import com.elead.card.views.CalendarItemView;
import com.elead.card.views.CusSpinner;
import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;

public class CorrugateLineCard extends BaseCard {
    private CusSpinner cusspinner;
    private BaseWaveView baseWave;
    private View rect_shijigongshi;
    private View rect_ziyuanguihua;
    private TextView tv_shijigongshi;
    private TextView tv_ziyuanguihua;
    private LinearLayout lin_container;
    private CalendarItemView calendarItemView;

    public CorrugateLineCard(Context context) {
        this(context, null);
    }

    public CorrugateLineCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CorrugateLineCard(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.WHITE);
        // TODO Auto-generated constructor stub
    }


    private void initDate() {
        // TODO Auto-generated method stub
        cusspinner.setText(new String[]{"年", "月", "季度", "周"});
        WaveDate waveDate = new WaveDate();
        List<List<String>> bottomTexts = new ArrayList<List<String>>();
        List<List<WaveInfo>> waveInfos = new ArrayList<List<WaveInfo>>();

        for (int j = 0; j < 2; j++) {
            List<String> strings = new ArrayList<String>();
            List<WaveInfo> infos = new ArrayList<WaveInfo>();
            for (int i = 0; i < 50; i++) {
                WaveInfo waveInfo = new WaveInfo((float) (Math.random() * 18f),
                        (float) (Math.random() * 18f), Math.random() * 20000f
                        + " $");
                infos.add(waveInfo);
                strings.add("W" + i + "");
            }
            waveInfos.add(infos);
            if (j == 0) {
                bottomTexts.add(strings);
            }
        }

        waveDate.setBottomTexts(bottomTexts);
        waveDate.setWaveInfos(waveInfos);
        baseWave.init(waveDate);
        baseWave.setMstyle(Mstyle.Curve);

        int[] blue = new int[]{0x80FFFFFF, 0x80b8a3ee};
        int[] purple = new int[]{0x80FFFFFF, 0x80f89499};
        waveDate.addColor(blue);
        waveDate.addColor(purple);
    }

    @Override
    public int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.card_the_people_stable_of;
    }

    @Override
    public void initView() {
        cusspinner = getView(R.id.stable_sp);
        baseWave = getView(R.id.wave_view);
        lin_container = getView(R.id.lin_container);

        rect_shijigongshi = getView(R.id.rect_shijigongshi);
        rect_ziyuanguihua = getView(R.id.rect_ziyuanguihua);
        tv_shijigongshi = getView(R.id.tv_shijigongshi);
        tv_ziyuanguihua = getView(R.id.tv_ziyuanguihua);

        initDate();
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        calendarItemView = new CalendarItemView(mContext);
        calendarItemView.setTitle(baseCardInfo.cardTitle);
        lin_container.addView(calendarItemView, 0);
    }
}
