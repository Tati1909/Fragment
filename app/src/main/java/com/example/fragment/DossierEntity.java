package com.example.fragment;

import android.os.Parcel;
import android.os.Parcelable;

public class DossierEntity implements Parcelable {
    public String title;
    public String description;
    public String date;

    public DossierEntity(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public static final Creator<DossierEntity> CREATOR = new Creator<DossierEntity>() {
        @Override
        public DossierEntity createFromParcel(Parcel in) {
            return new DossierEntity(in);
        }

        @Override
        public DossierEntity[] newArray(int size) {
            return new DossierEntity[size];
        }
    };

    protected DossierEntity(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
    }
}
