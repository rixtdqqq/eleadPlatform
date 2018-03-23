package com.elead.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class GroupEntry {
   private Drawable drawable;
    private String GroudTitle;
    private String itemTitle;

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getGroudTitle() {
        return GroudTitle;
    }

    public void setGroudTitle(String groudTitle) {
        GroudTitle = groudTitle;
    }
}
