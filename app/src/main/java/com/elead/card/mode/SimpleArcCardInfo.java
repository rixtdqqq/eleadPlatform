package com.elead.card.mode;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class SimpleArcCardInfo  extends BaseCardInfo{
    public String title;
    public boolean heveTitle;
    public String timeDate;
    public List<ShapArcInfo> shapArcInfos;

    public boolean isHeveTitle() {
        return heveTitle;
    }

    public void setHeveTitle(boolean heveTitle) {
        this.heveTitle = heveTitle;
    }

    public List<ShapArcInfo> getShapArcInfos() {
        return shapArcInfos;
    }

    public void setShapArcInfos(List<ShapArcInfo> shapArcInfos) {
        this.shapArcInfos = shapArcInfos;
    }

    public SimpleArcCardInfo( ) {
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public SimpleArcCardInfo(String title, boolean heveTitle, String timeDate,
                             List<ShapArcInfo> shapArcInfos) {
        this.title = title;
        this.heveTitle = heveTitle;
        this.timeDate = timeDate;
        this.shapArcInfos = shapArcInfos;
    }
}
