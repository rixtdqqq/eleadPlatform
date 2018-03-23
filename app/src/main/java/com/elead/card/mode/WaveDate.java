package com.elead.card.mode;

import java.util.ArrayList;
import java.util.List;

public class WaveDate extends BaseCardInfo {
    private List<List<WaveInfo>> waveInfos = new ArrayList<List<WaveInfo>>();
    private List<List<String>> bottomTexts = new ArrayList<List<String>>();
    private List<int[]> colors = new ArrayList<int[]>();

    public static class WaveInfo {
        public float tvalue;
        public float bvalue;
        public String clickTip;

        public WaveInfo(float tvalue, float bvalue, String clickTip) {
            super();
            this.tvalue = tvalue;
            this.bvalue = bvalue;
            this.clickTip = clickTip;
        }

        public WaveInfo() {
            super();
            // TODO Auto-generated constructor stub
        }

    }

    public void addWaveInfo(List<WaveInfo> waveInfos) {
        this.waveInfos.add(waveInfos);

    }

    public void addBottomText(List<String> bottomTexts) {
        this.bottomTexts.add(bottomTexts);
    }

    public WaveDate(List<List<WaveInfo>> waveInfos,
                    List<List<String>> bottomTexts) {
        super();
        this.waveInfos = waveInfos;
        this.bottomTexts = bottomTexts;
    }

    public WaveDate() {
        super();
        // TODO Auto-generated constructor stub
    }


    public List<int[]> getColor() {
        return colors;
    }

    public void addColor(int[] color) {
        this.colors.add(color);
        ;
    }

    public List<List<WaveInfo>> getWaveInfos() {
        return waveInfos;
    }

    public void setWaveInfos(List<List<WaveInfo>> waveInfos) {
        this.waveInfos = waveInfos;
    }

    public List<List<String>> getBottomTexts() {
        return bottomTexts;
    }

    public void setBottomTexts(List<List<String>> bottomTexts) {
        this.bottomTexts = bottomTexts;
    }

}
