package com.elead.approval.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 物品列表体类
 *
 */
public class ApprovalResItemEntity implements Parcelable {

    private String name;
    private String count;

    public ApprovalResItemEntity(){
        super();
    }

    protected ApprovalResItemEntity(Parcel in) {
        name = in.readString();
        count = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public static final Creator<ApprovalResItemEntity> CREATOR = new Creator<ApprovalResItemEntity>() {
        @Override
        public ApprovalResItemEntity createFromParcel(Parcel in) {
            return new ApprovalResItemEntity(in);
        }

        @Override
        public ApprovalResItemEntity[] newArray(int size) {
            return new ApprovalResItemEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(count);
    }
}
