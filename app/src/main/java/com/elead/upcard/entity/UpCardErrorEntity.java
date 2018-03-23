package com.elead.upcard.entity;

/**
 * @desc
 * @auth Created by Administrator on 2017/1/10 0010.
 */

public class UpCardErrorEntity {
    public String time;
    public int type;
    public String off_time;
    public String on_time;
    public String date;
    public boolean on_normal;
    public boolean off_normal;

    public boolean isSelect;
    public float position;

    public UpCardErrorEntity() {
    }

    public UpCardErrorEntity(String time, int type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public String toString() {
        return "UpCardErrorEntity{" +
                "time='" + time + '\'' +
                ", type=" + type +
                ", off_time='" + off_time + '\'' +
                ", on_time='" + on_time + '\'' +
                ", date='" + date + '\'' +
                ", on_normal=" + on_normal +
                ", off_normal=" + off_normal +
                ", isSelect=" + isSelect +
                ", position=" + position +
                '}';
    }
}
