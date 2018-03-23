package com.elead.card.mode;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class ShapArcInfo extends BaseCardInfo {
    public float pencent;
    public String topText;
    public String botText;
    public String arcColor;

    public ShapArcInfo() {
    }

    public ShapArcInfo(float pencent, String topText, String botText, String arcColor) {
        this.pencent = pencent;
        this.topText = topText;
        this.botText = botText;
        this.arcColor = arcColor;
    }
}
