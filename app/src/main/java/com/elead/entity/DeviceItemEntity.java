package com.elead.entity;

import java.util.Comparator;

/**
 * Created by xieshibin on 2016/12/22.
 */

public class DeviceItemEntity implements Comparator {
    //设备名
    public String name;
    //设备号
    public String udid;
    //绑定时间
    public String last_bind_time;

    @Override
    public int compare(Object lhs, Object rhs) {
        return 0;
    }
}
