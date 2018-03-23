package com.elead.upcard.modle;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class DutyInfo {
    public String time;//: "2016-12-04 16:56:32",
    public boolean is_normal;// true,
    public String wifimac;// "0C:1D:AF:C9:1C:d5",
    public String type;//WiFi"
    public String latitude;//纬度
    public String longitude;//经度
    public String wifiname;
    public String gpsaddr;
    public String locationName;


    public DutyInfo() {
    }

    public DutyInfo(String time) {
        this.time = time;
    }

    public DutyInfo(boolean is_normal, String wifimac, String type, String time) {
        this.is_normal = is_normal;
        this.wifimac = wifimac;
        this.type = type;
        this.time = time;
    }

    @Override
    public String toString() {
        return "DutyInfo{" +
                "time='" + time + '\'' +
                ", wifimac='" + wifimac + '\'' +
                '}';
    }
}
