package com.elead.entity;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class SearchFriendEntity {
    private String WorkNumber;
    private String Name;
    private String HxUserName;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHxUserName() {
        return HxUserName;
    }

    public void setHxUserName(String hxUserName) {
        HxUserName = hxUserName;
    }

    public String getWorkNumber() {
        return WorkNumber;
    }

    public void setWorkNumber(String workNumber) {
        WorkNumber = workNumber;
    }
}
