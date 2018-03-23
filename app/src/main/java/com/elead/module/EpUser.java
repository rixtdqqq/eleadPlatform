package com.elead.module;

import android.text.TextUtils;

import static com.elead.utils.MyTextUtils.getUserInitialLetter;

/**
 * @desc
 * @auth Created by Administrator on 2016/9/6 0006.
 */
public class EpUser {


    public String id;    // 主键id（企业人员id）
    public String code;    // 用户代码（一般由英文或数字字符组成，作为用户登录名）
    public String name;    // 用户姓名（一般表示用户的真实姓名，可以是中文名）
    public String english_name;    // 英文名（企业微信通讯录中的字段）
    public String pwd;    // 密码密文（采用md5加密）
    public String mima;    // 密码明文
    public int em_uid;    // 对应环信系统用户id
    public int role_id;    // 角色id
    public String role_name;    // 角色名
    public String userid;    // 员工UserID（参照钉钉管理后台）
    public String dept_name;    // 部门
    public String work_no;    // 工号
    public String gender;    // 性别
    public String job_title;    // 职位（钉钉企业通讯录中的字段）/职务（企业微信通讯录中的字段）
    public String mobile_phone;    // 手机号
    public String fixed_phone;    // 分机号（钉钉企业通讯录中的字段）/座机号（企业微信通讯录中的字段）
    public String email;    // 邮箱
    public String work_place;    // 办公地点
    public String notes;    // 备注

    public String initialLetter;//缩写
    public int londingState;//
    public String project_name;//项目

    public EpUser(String nick, String username, String initialLetter) {
        this.role_name = nick;
        this.code = username;
        this.initialLetter = initialLetter;
    }

    public EpUser(String[] mobile_phoneandname) {
        mobile_phone = mobile_phoneandname[0];
       role_name= name = mobile_phoneandname[1];
        if (!TextUtils.isEmpty(mobile_phone) && !TextUtils.isEmpty(name)) {
            this.initialLetter = getUserInitialLetter(name);
        }
    }


    public String getUsername() {
        return code;
    }

    public void setEmail(String email) {
        if (null != email) this.email = email;
    }

    public String getCode()


    {
        return code;
    }

    public void setCode(String code) {
        if (null != code) this.code = code;
    }


    public void setPwd(String pwd) {
        if (null != pwd) this.pwd = pwd;
    }

    public enum UserType {
        one, two, three, four
    }

    public UserType userType = UserType.three;

    public EpUser() {

    }


    @Override
    public boolean equals(Object o) {
        EpUser user = (EpUser) o;
        return role_name.equals(user.role_name);
    }

    public EpUser(String uName, String pWord) {
        this.code = uName;
        this.pwd = pWord;
    }


    public void setWork_place(String work_place) {
        if (null != work_place) this.work_place = work_place;
    }


    public void setId(String id) {
        if (null != id)
            this.id = id;
    }


    public void setName(String name) {
        if (null != name) this.name = name;
    }


    public void setEnglish_name(String english_name) {
        if (null != english_name) this.english_name = english_name;
    }


    public void setMima(String mima) {
        if (null != mima)
            this.mima = mima;
    }


    public void setEm_uid(int em_uid) {
        if (0 != em_uid)
            this.em_uid = em_uid;
    }


    public void setRole_id(int role_id) {

        if (0 != role_id) this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWork_no() {
        return work_no;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getMobile_phone() {
        return mobile_phone + "";
    }

    public void setMobile_phone(String mobile_phone) {
        if (null != mobile_phone) this.mobile_phone = mobile_phone;
    }

    public String getFixed_phone() {
        return fixed_phone;
    }

    public void setFixed_phone(String fixed_phone) {
        this.fixed_phone = fixed_phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInitialLetter() {
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    public int getLondingState() {
        return londingState;
    }

    public void setLondingState(int londingState) {
        this.londingState = londingState;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public void setDept_name(String dept_name) {
        if (null != dept_name)
            this.dept_name = dept_name;
    }

    public void setProject_name(String project_name) {
        if (null != project_name)
            this.project_name = project_name;
    }
}
