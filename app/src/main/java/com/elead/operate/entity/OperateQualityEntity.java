package com.elead.operate.entity;

/**
 * @desc 质量实体
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class OperateQualityEntity {
    String name;
    String prompt;
    String commonly;
    String serious;
    String deadly;

    public String getDefect_rate() {
        return defect_rate;
    }

    public void setDefect_rate(String defect_rate) {
        this.defect_rate = defect_rate;
    }

    public String getDeadly() {
        return deadly;
    }

    public void setDeadly(String deadly) {
        this.deadly = deadly;
    }

    public String getSerious() {
        return serious;
    }

    public void setSerious(String serious) {
        this.serious = serious;
    }

    public String getCommonly() {
        return commonly;
    }

    public void setCommonly(String commonly) {
        this.commonly = commonly;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String defect_rate;

}
