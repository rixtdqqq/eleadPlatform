package com.elead.im.entry;

/**
 * Created by linmin on 2017/1/6.
 */

public class EMUserForNick {

    private String em_nickname;
    private String work_no;
    private String data_msg;
    private String data_code;
    private String em_username;
    private String original_param;
    private String ep_name;

    public EMUserForNick(){

    }

    public EMUserForNick(String em_nickname, String work_no, String data_msg, String data_code, String em_username, String original_param, String ep_name) {
        this.em_nickname = em_nickname;
        this.work_no = work_no;
        this.data_msg = data_msg;
        this.data_code = data_code;
        this.em_username = em_username;
        this.original_param = original_param;
        this.ep_name = ep_name;
    }

    public String getEm_nickname() {
        return em_nickname;
    }

    public void setEm_nickname(String em_nickname) {
        this.em_nickname = em_nickname;
    }

    public String getWork_no() {
        return work_no;
    }

    public void setWork_no(String work_no) {
        this.work_no = work_no;
    }

    public String getData_msg() {
        return data_msg;
    }

    public void setData_msg(String data_msg) {
        this.data_msg = data_msg;
    }

    public String getData_code() {
        return data_code;
    }

    public void setData_code(String data_code) {
        this.data_code = data_code;
    }

    public String getEm_username() {
        return em_username;
    }

    public void setEm_username(String em_username) {
        this.em_username = em_username;
    }

    public String getOriginal_param() {
        return original_param;
    }

    public void setOriginal_param(String original_param) {
        this.original_param = original_param;
    }

    public String getEp_name() {
        return ep_name;
    }

    public void setEp_name(String ep_name) {
        this.ep_name = ep_name;
    }
}
