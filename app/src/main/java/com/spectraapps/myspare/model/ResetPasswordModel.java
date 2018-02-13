package com.spectraapps.myspare.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MahmoudAyman on 13/02/2018.
 */

public class ResetPasswordModel implements Parcelable{
    /**
     * type : ""
     * title : ""
     */

    private String type;
    private String title;

    protected ResetPasswordModel(Parcel in) {
        type = in.readString();
        title = in.readString();
    }

    public static final Creator<ResetPasswordModel> CREATOR = new Creator<ResetPasswordModel>() {
        @Override
        public ResetPasswordModel createFromParcel(Parcel in) {
            return new ResetPasswordModel(in);
        }

        @Override
        public ResetPasswordModel[] newArray(int size) {
            return new ResetPasswordModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(title);
    }
}
