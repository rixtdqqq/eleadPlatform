package com.elead.card.mode;

public class BaseCardInfo {
    public int isShowTitle;
    public String cardType;
    public String cardTitle;
    public int tabType;
    public CardDetail cardDetail;

    public int cardHeight;
    public String color;

    public BaseCardInfo() {
    }

    public BaseCardInfo(String cardType, String cardTitle, int tabType, CardDetail cardDetail) {
        this.cardType = cardType;
        this.cardTitle = cardTitle;
        this.tabType = tabType;
        this.cardDetail = cardDetail;
    }

    public BaseCardInfo(String cardType, String cardTitle) {
        this.cardType = cardType;
        this.cardTitle = cardTitle;
    }

    public BaseCardInfo(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof BaseCardInfo) {
            BaseCardInfo cardInfo = (BaseCardInfo) obj;
            return cardInfo.cardType.equals(cardType);
        } else {
            return obj.equals(cardType);
        }
    }

    @Override
    public String toString() {
        return "BaseCardInfo{" +
                "isShowTitle=" + isShowTitle +
                ", cardType='" + cardType + '\'' +
                ", cardTitle='" + cardTitle + '\'' +
                ", tabType='" + tabType + '\'' +
                ", cardDetail=" + cardDetail +
                ", cardHeight=" + cardHeight +
                ", color='" + color + '\'' +
                '}';
    }

    //    @Override
//    public boolean equals(Object obj) {
//        // TODO Auto-generated method stub
//        if (obj instanceof BaseCardInfo) {
//            BaseCardInfo cardInfo = (BaseCardInfo) obj;
//            return cardInfo.c\.equals(resName);
//        } else {
//            return obj.equals(resName);
//        }
//    }

}
