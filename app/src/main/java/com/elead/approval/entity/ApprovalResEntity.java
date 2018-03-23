package com.elead.approval.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品领用实体
 * Created by mujun.xu on 2016/9/21 0021.
 */
public class ApprovalResEntity extends ApprovalCommonInfo {
    private String resPurpose;
    private String resReceiveDetail;
    private List<ApprovalResItemEntity> resList = new ArrayList<ApprovalResItemEntity>();

    public ApprovalResEntity() {
        super();
    }

    public String getResPurpose() {
        return resPurpose;
    }

    public void setResPurpose(String resPurpose) {
        this.resPurpose = resPurpose;
    }

    public String getResReceiveDetail() {
        return resReceiveDetail;
    }

    public void setResReceiveDetail(String resReceiveDetail) {
        this.resReceiveDetail = resReceiveDetail;
    }

    public List<ApprovalResItemEntity> getResList() {
        return resList;
    }

    public void setResList(List<ApprovalResItemEntity> resList) {
        this.resList = resList;
    }
}
