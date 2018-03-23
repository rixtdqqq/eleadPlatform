package com.elead.approval.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class ApprovalCommonInfo {
    private List<String> approvalUsers;
    private String approvalContent;
    private List<String> imageNames;
    private String approvalDsicribe;//描述

    public ApprovalCommonInfo() {
    }

    public List<String> getApprovalUsers() {
        return approvalUsers;
    }

    public void setApprovalUsers(List<String> approvalUsers) {
        this.approvalUsers = approvalUsers;
    }

    public String getApprovalContent() {
        return approvalContent;
    }

    public void setApprovalContent(String approvalContent) {
        this.approvalContent = approvalContent;
    }

    public List<String> getImageNames() {
        return imageNames;
    }

    public void setImageNames(List<String> imageNames) {
        this.imageNames = imageNames;
    }

    public String getApprovalDsicribe() {
        return approvalDsicribe;
    }

    public void setApprovalDsicribe(String approvalDsicribe) {
        this.approvalDsicribe = approvalDsicribe;
    }
}
