package com.elead.approval.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 待审批实体类
 *
 */
public class ApprovalWaitEntity implements Parcelable {

    private String typeStauts;

    private String name;
    private String date;

    public ApprovalWaitEntity(){
        super();
    }

    protected ApprovalWaitEntity(Parcel in) {
        typeStauts = in.readString();
        name = in.readString();
        date = in.readString();
    }

    public static final Creator<ApprovalWaitEntity> CREATOR = new Creator<ApprovalWaitEntity>() {
        @Override
        public ApprovalWaitEntity createFromParcel(Parcel in) {
            return new ApprovalWaitEntity(in);
        }

        @Override
        public ApprovalWaitEntity[] newArray(int size) {
            return new ApprovalWaitEntity[size];
        }
    };

    public String getType() {
        return typeStauts;
    }

    public void setType(String type) {
        this.typeStauts = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(typeStauts);
        parcel.writeString(name);
        parcel.writeString(date);
    }
}
