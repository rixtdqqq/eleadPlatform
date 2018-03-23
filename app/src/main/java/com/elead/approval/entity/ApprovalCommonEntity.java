package com.elead.approval.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用审批实体
 * Created by mujun.xu on 2016/9/21 0021.
 */
public class ApprovalCommonEntity extends ApprovalCommonInfo {
    private String commonContent;
    private String commonDetails;

    public ApprovalCommonEntity() {
        super();
    }

    public String getCommonContent() {
        return commonContent;
    }

    public void setCommonContent(String commonContent) {
        this.commonContent = commonContent;
    }

    public String getCommonDetails() {
        return commonDetails;
    }

    public void setCommonDetails(String commonDetails) {
        this.commonDetails = commonDetails;
    }
}
