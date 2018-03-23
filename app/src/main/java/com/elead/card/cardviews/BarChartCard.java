package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;

/**
 * Created by jiangdikai on 2017/2/10.
 */

public class BarChartCard extends BaseCard {
    public BarChart mChart;
    public TextView mTitle;
    public BarChartCard(Context context) {
        this(context, null);
    }

    public BarChartCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }

    @Override
    public int initLayout() {
        return R.layout.card_barchart;
    }

    @Override
    public void initView() {
        mChart = (BarChart) mView.findViewById(R.id.chart1);
        mTitle = (TextView) mView.findViewById(R.id.title);
        initChartView();
        setData();
        toggleValue();
    }

    /**
     * 设置标题
     * @param title 标题的文字
     */
    public TextView setTitle(String title){
        mTitle.setText(title);
        return mTitle;
    }

    /**
     * 设置标题文字大小
     * @param size
     * @return
     */
    public TextView setTitleSize(float size){
        mTitle.setTextSize(size);
        return mTitle;
    }

    private void toggleValue() {
        for (IDataSet set : mChart.getData().getDataSets())
            set.setDrawValues(!set.isDrawValuesEnabled());
        mChart.invalidate();
    }
    private void initChartView() {
        mChart.setMaxVisibleValueCount(60);
        mChart.setDescription(null);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setExtraOffsets(0, 0, 20, 15);
        initXAxis();
        initLeftYAxis();
    }

    private void initLeftYAxis() {
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisLineWidth(1);
        leftAxis.setLabelCount(8, false);
        leftAxis.setXOffset(10);
        leftAxis.setZeroLineWidth(30);
        leftAxis.enableGridDashedLine(20f, 10f, 5f);
        leftAxis.setTextColor(Color.parseColor("#393E51"));
        leftAxis.setAxisLineColor(Color.parseColor("#393E51"));
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextSize(16);
        initLimitLine(leftAxis);
    }

    private void initLimitLine(YAxis leftAxis) {
        LimitLine yLimitLine = new LimitLine(45f,"");
        yLimitLine.setLineColor(Color.parseColor("#7A671C"));
        yLimitLine.enableDashedLine(20f, 10f, 5f);
        leftAxis.addLimitLine(yLimitLine);
    }

    private void initXAxis() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setYOffset(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(1);
        xAxis.setTextColor(Color.parseColor("#3B444F"));
        xAxis.setAxisLineColor(Color.parseColor("#393E51"));
        xAxis.setTextSize(16);
    }

    private void setData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(0,10));
        yVals1.add(new BarEntry(1,20));
        yVals1.add(new BarEntry(2,30));
        yVals1.add(new BarEntry(3,40));
        yVals1.add(new BarEntry(4,50));
        yVals1.add(new BarEntry(5,60));
        dataIntoView(yVals1);
    }
    public void setData(ArrayList<BarEntry> yVals1) {
        dataIntoView(yVals1);
    }

    private void dataIntoView(ArrayList<BarEntry> yVals1) {
        BarDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);

            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setColors(Color.parseColor("#208B8C"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.4f);


            mChart.setData(data);
        }
    }
}
