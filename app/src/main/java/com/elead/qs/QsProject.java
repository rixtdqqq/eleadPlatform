package com.elead.qs;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class QsProject {
    private String num;
    private String text;
    private String tip;
    private int tipStyle;

    public QsProject() {

    }

    public QsProject(String num, String text, String tip, int tipStyle) {
        this.num = num;
        this.text = text;
        this.tip = tip;
        this.tipStyle = tipStyle;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getTipStyle() {
        return tipStyle;
    }

    public void setTipStyle(int tipStyle) {
        this.tipStyle = tipStyle;
    }
}
