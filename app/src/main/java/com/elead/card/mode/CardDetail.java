package com.elead.card.mode;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class CardDetail {
    public int isShowDate;
    public int menuCount;
    public int circleCount;
    public int cardIndex;

    public CardDetail() {

    }

    public CardDetail(int isShowDate, int menuCount, int circleCount) {
        this.isShowDate = isShowDate;
        this.menuCount = menuCount;
        this.circleCount = circleCount;
    }
}
