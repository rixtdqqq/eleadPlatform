package com.elead.upcard.modle;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class RecordsValue {
    public List<DutyInfo> others; //
    public boolean has_abnormal;
    public DutyInfo off_duty;
    public DutyInfo on_duty;
    public String date = "";//"2016-12-07",

    public RecordsValue() {

    }

    public RecordsValue(List<DutyInfo> others, boolean has_abnormal, DutyInfo off_duty, DutyInfo on_duty) {
        this.others = others;
        this.has_abnormal = has_abnormal;
        this.off_duty = off_duty;
        this.on_duty = on_duty;
    }

    @Override
    public String toString() {
        return "RecordsValue{" +
                "others=" + others +
                ", has_abnormal=" + has_abnormal +
                ", off_duty=" + off_duty +
                ", on_duty=" + on_duty +
                '}';
    }
}
