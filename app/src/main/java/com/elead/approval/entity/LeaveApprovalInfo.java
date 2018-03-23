package com.elead.approval.entity;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class LeaveApprovalInfo extends ApprovalCommonInfo {
    private String leaveType;
    private String startTime;
    private String endTime;
    private String leaveDay;

    public LeaveApprovalInfo(String leaveType, String startTime, String endTime, String leaveDay) {
        this.leaveType = leaveType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.leaveDay = leaveDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay;
    }

    public LeaveApprovalInfo() {

    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
}
