package com.elead.card.mode;

import java.util.List;

public class TbSelectCardInfo extends BaseCardInfo {
    private List<String> items;
    private int selectIndex;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public TbSelectCardInfo(List<String> items) {
        super();
        this.items = items;
    }


    public TbSelectCardInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

}
