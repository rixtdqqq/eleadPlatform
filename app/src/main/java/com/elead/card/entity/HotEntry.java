package com.elead.card.entity;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class HotEntry {
    private int imgRourse;
    private String title;
    private String content;
    private int Number;
    private String time;
    private String amonut;

    public int getImgRourse() {
        return imgRourse;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return Number;
    }

    public String getTime() {
        return time;
    }

    public void setImgRourse(int imgRourse) {
        this.imgRourse = imgRourse;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmonut(String amonut) {
        this.amonut = amonut;
    }

    public String getAmonut() {

        return amonut;
    }
}
