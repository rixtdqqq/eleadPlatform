package com.elead.approval.entity;

import java.util.ArrayList;
import java.util.List;



/**
 * @desc 存在后台的数据
 * @auth Created by Administrator on 2016/10/13 0013.
 */
public class ApprovalListEntity {

    private String userName;
    private List<ApprovalClassicModelEntity> classicModelList = new ArrayList<>();

    public List<ApprovalClassicModelEntity> getClassicModelList() {
        return classicModelList;
    }

    public void setClassicModelList(List<ApprovalClassicModelEntity> classicModelList) {
        this.classicModelList.clear();
        this.classicModelList.addAll(classicModelList);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
