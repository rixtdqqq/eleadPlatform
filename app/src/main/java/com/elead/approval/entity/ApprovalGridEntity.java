package com.elead.approval.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 审批GridView Entity实体类
 *
 */
public class ApprovalGridEntity implements Parcelable {

    private String image;

    private String name;

    private int imgId;

    public ApprovalGridEntity(){
        super();
    }

    protected ApprovalGridEntity(Parcel in) {
        image = in.readString();
        name = in.readString();
    }

    public static final Creator<ApprovalGridEntity> CREATOR = new Creator<ApprovalGridEntity>() {
        @Override
        public ApprovalGridEntity createFromParcel(Parcel in) {
            return new ApprovalGridEntity(in);
        }

        @Override
        public ApprovalGridEntity[] newArray(int size) {
            return new ApprovalGridEntity[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(name);
    }
}
