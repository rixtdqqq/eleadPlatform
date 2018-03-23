package com.elead.operate.entity;

/**
 * @desc 需求实体
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class OperateDemandEntity {
    String checkItem;
    String problemNumber;
    String compliance;

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(String problemNumber) {
        this.problemNumber = problemNumber;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }
}
