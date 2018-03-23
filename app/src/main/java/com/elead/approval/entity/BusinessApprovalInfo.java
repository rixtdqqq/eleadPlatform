package com.elead.approval.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class BusinessApprovalInfo extends ApprovalCommonInfo {
    private List<BusInessMingXIInfo> busInessMingXIInfos;

    public List<BusInessMingXIInfo> getBusInessMingXIInfos() {
        return busInessMingXIInfos;
    }

    public void setBusInessMingXIInfos(List<Map<Integer, String>> contents) {
        busInessMingXIInfos = new ArrayList<>();
        int index = 0;
        for (Map<Integer, String> map : contents) {
            busInessMingXIInfos.add(new BusInessMingXIInfo(map.get(0), map.get(1), map.get(2), index));
            index++;
        }
    }


}
