package com.elead.approval.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 经典模板实体类
 *
 */
public class ApprovalClassicModelEntity{

    private String provider;
    private String name;
    private String description;
    private int imgId;
    private boolean isAdd;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
