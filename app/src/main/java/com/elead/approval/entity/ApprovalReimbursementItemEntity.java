package com.elead.approval.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 报销列表体类
 *
 */
public class ApprovalReimbursementItemEntity implements Parcelable {

    private String type;
    private String money;
    private String moneyDetail;

    public ApprovalReimbursementItemEntity(){
        super();
    }

    protected ApprovalReimbursementItemEntity(Parcel in) {
        type = in.readString();
        money = in.readString();
        moneyDetail = in.readString();
    }

    public static final Creator<ApprovalReimbursementItemEntity> CREATOR = new Creator<ApprovalReimbursementItemEntity>() {
        @Override
        public ApprovalReimbursementItemEntity createFromParcel(Parcel in) {
            return new ApprovalReimbursementItemEntity(in);
        }

        @Override
        public ApprovalReimbursementItemEntity[] newArray(int size) {
            return new ApprovalReimbursementItemEntity[size];
        }
    };

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyDetail() {
        return moneyDetail;
    }

    public void setMoneyDetail(String moneyDetail) {
        this.moneyDetail = moneyDetail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(money);
        parcel.writeString(moneyDetail);
    }
}
