package com.elead.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by xieshibin on 2016/12/9.
 */

public class ItemEntity implements Comparator {
    //标题
    public String title;
    //时间
    public String time;
    //作者
    public String author;
    //图片
    public String image;
    //链接
    public String link;
    //图片链接地址
    public String imageLink;

    @Override
    public int compare(Object lhs, Object rhs) {
        return 0;
    }
}
