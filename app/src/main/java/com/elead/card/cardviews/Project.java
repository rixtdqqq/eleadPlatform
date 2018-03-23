package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;

import com.elead.card.mode.BaseCardInfo;
import com.elead.card.mode.WaveDate;
import com.elead.card.mode.WaveDate.WaveInfo;
import com.elead.card.views.BaseWaveView;
import com.elead.card.views.BaseWaveView.Mstyle;
import com.elead.card.views.CusSpinner;
import com.elead.eplatform.R;

import java.util.ArrayList;
import java.util.List;


public class Project extends BaseCard {

    public Project(Context context) {
        this(context, null);
    }

    public Project(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    private CusSpinner spinner1, spinner2, spinner3;
    private BaseWaveView waveChartView;

    @Override
    public int initLayout() {
        return R.layout.card_chart_project;
    }

    @Override
    public void initView() {
        spinner1 = (CusSpinner) getView(R.id.card_visits_spinner1);
        spinner1.setText(new String[]{"Month", "嚓嚓嚓", "Wednesday", "Thursday"});

        spinner2 = (CusSpinner) getView(R.id.card_visits_spinner2);
        spinner2.setText(new String[]{"Range", "ggg", "Thursday", "Thursday"});

        spinner3 = (CusSpinner) getView(R.id.card_visits_spinner3);
        spinner3.setText(new String[]{"Last", "哈哈哈哈", "啊啊啊啊", "sdasda"});

        waveChartView = (BaseWaveView) getView(R.id.project_wave_chart_view);

        waveChartView.setMstyle(Mstyle.Curve);
        WaveDate wavedate = new WaveDate();
        initWaveView(wavedate);
        initWaveView(wavedate);
        int[] blue = new int[]{0x80277dcc, 0x8000deff};
        int[] purple = new int[]{0x80e20fb8, 0x80ffa800};
        wavedate.addColor(blue);
        wavedate.addColor(purple);

        waveChartView.init(wavedate);
    }

    private void initWaveView(WaveDate date) {

        List<WaveInfo> chartInfos = new ArrayList<WaveInfo>();
        List<String> aaaa = new ArrayList<String>();

        for (int i = 0; i < 50; i++) {
            String text = "";
            switch (i % 7) {
                case 0:
                    text = "Monday";
                    break;
                case 1:
                    text = "Tuesday";
                    break;
                case 2:
                    text = "Wednesday";
                    break;
                case 3:
                    text = "Thursday";
                    break;
                case 4:
                    text = "Friday";
                    break;
                case 5:
                    text = "Saturday";
                    break;
                case 6:
                    text = "Sunday";
                    break;
                default:
                    break;
            }

            WaveInfo waveInfo = new WaveInfo((float) (Math.random() * 18f),
                    (float) (Math.random() * 18f), Math.random() * 20000f
                    + " $");
            chartInfos.add(waveInfo);
            aaaa.add(text);
        }

        date.addWaveInfo(chartInfos);
        date.addBottomText(aaaa);
        System.out.println("chartInfossize:" + chartInfos.size());
        System.out.println("aaaasize:" + aaaa.size());
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
