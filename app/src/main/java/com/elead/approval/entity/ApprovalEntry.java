package com.elead.approval.entity;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class ApprovalEntry {
    private String name;
    private String ApprovalStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }
}
