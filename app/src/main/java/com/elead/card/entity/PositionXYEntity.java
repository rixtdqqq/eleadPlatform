package com.elead.card.entity;

import android.graphics.RectF;

/**
 * @desc
 * @auth Created by Administrator on 2016/10/27 0027.
 */

public class PositionXYEntity {

    private float x;
    private float y;
    private RectF rectF;
    private boolean isDrawCircel;
    private float number;

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public boolean isDrawCircel() {
        return isDrawCircel;
    }

    public void setDrawCircel(boolean drawCircel) {
        isDrawCircel = drawCircel;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
