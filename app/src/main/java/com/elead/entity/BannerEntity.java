package com.elead.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * bannerEntity实体类
 *
 */
public class BannerEntity implements Parcelable {

    private String image;

    private String detail;


    private String desrpen;

    private String desrpcn;

    private String fontcolor;

    public BannerEntity(){
        super();
    }

    protected BannerEntity(Parcel in) {
        image = in.readString();
        detail = in.readString();
        desrpen = in.readString();
        desrpcn = in.readString();
        fontcolor = in.readString();
    }

    public static final Creator<BannerEntity> CREATOR = new Creator<BannerEntity>() {
        @Override
        public BannerEntity createFromParcel(Parcel in) {
            return new BannerEntity(in);
        }

        @Override
        public BannerEntity[] newArray(int size) {
            return new BannerEntity[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDesrpen(String desrpen) {
        this.desrpen = desrpen;
    }

    public void setDesrpcn(String desrpcn) {
        this.desrpcn = desrpcn;
    }

    public void setFontcolor(String fontcolor) {
        this.fontcolor = fontcolor;
    }

    public String getDetail() {
        return detail;
    }
    public String getDesrpen() {
        return desrpen;
    }

    public String getDesrpcn() {
        return desrpcn;
    }

    public String getFontcolor() {
        return fontcolor;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(detail);
        parcel.writeString(desrpen);
        parcel.writeString(desrpcn);
        parcel.writeString(fontcolor);
    }
}
