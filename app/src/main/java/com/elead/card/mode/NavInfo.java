package com.elead.card.mode;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 * Tab页实体类
 */

public class NavInfo {
    private List<BaseCardInfo> cards;
    private String tabTitle;
    private String navTitle;
    private String imgUrl;

    public NavInfo() {
    }

    public List<BaseCardInfo> getCards() {
        return cards;
    }

    public void setCards(List<BaseCardInfo> cards) {
        this.cards = cards;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getNavTitle() {
        return navTitle;
    }

    public void setNavTitle(String navTitle) {
        this.navTitle = navTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public NavInfo(List<BaseCardInfo> cards, String tabTitle, String navTitle, String imgUrl) {
        this.cards = cards;
        this.tabTitle = tabTitle;
        this.navTitle = navTitle;
        this.imgUrl = imgUrl;
    }
}
