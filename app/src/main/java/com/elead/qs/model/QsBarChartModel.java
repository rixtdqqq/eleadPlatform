package com.elead.qs.model;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class QsBarChartModel {
        public String title;
        public ArrayList<BarEntry> yVals1;

        public QsBarChartModel(ArrayList<BarEntry> yVals1) {
            this.yVals1 = yVals1;
        }

        public QsBarChartModel(String name, ArrayList<BarEntry> yVals1) {
            this.title = name;
            this.yVals1 = yVals1;
        }
    }