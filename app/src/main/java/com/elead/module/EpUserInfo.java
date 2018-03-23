package com.elead.module;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class EpUserInfo {
    public String mobile_phone;
    public String project_name;
    public String work_no;
    public String email;
    public String name;
    public String dept_name;

    public EpUserInfo() {

    }

    public EpUserInfo(String mobile_phone, String project_name, String work_no, String email, String name, String dept_name) {
        this.mobile_phone = mobile_phone;
        this.project_name = project_name;
        this.work_no = work_no;
        this.email = email;
        this.name = name;
        this.dept_name = dept_name;
    }
}
