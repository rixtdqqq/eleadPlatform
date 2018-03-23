package com.elead.develop.entity;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class PoPreViewEnty {
    private int imgSrc;
    private String TopTitle;
    private String BottomTime;
    private String unit;
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTopTitle() {
        return TopTitle;
    }

    public void setTopTitle(String topTitle) {
        TopTitle = topTitle;
    }

    public String getBottomTime() {
        return BottomTime;
    }

    public void setBottomTime(String bottomTime) {
        BottomTime = bottomTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
