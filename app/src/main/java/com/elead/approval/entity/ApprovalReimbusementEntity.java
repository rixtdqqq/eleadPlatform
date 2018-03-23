package com.elead.approval.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 报销实体
 * Created by mujun.xu on 2016/9/21 0021.
 */
public class ApprovalReimbusementEntity extends ApprovalCommonInfo {
    private String moneyTotal;
    private List<ApprovalReimbursementItemEntity> reimbursementList = new ArrayList<ApprovalReimbursementItemEntity>();

    public ApprovalReimbusementEntity() {
        super();
    }

    public String getMoneyTotal() {

        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public List<ApprovalReimbursementItemEntity> getReimbursementList() {
        return reimbursementList;
    }

    public void setReimbursementList(List<ApprovalReimbursementItemEntity> reimbursementList) {
        this.reimbursementList = reimbursementList;
    }
}
