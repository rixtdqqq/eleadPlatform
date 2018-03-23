package com.elead.upcard.modle;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class SignInfo {

    public String ret_code;
    public String ret_message;
    public List<RecordsValue> records;

    public SignInfo() {

    }

    public SignInfo(String ret_code, List<RecordsValue> records, String ret_message) {
        this.ret_code = ret_code;
        this.records = records;
        this.ret_message = ret_message;
    }


    @Override
    public String toString() {
        return "SignInfo{" +
                "ret_code='" + ret_code + '\'' +
                ", ret_message='" + ret_message + '\'' +
                ", records=" + records +
                '}';
    }
}
