package com.example.fragment;

import android.os.Parcel;
import android.os.Parcelable;
public class NotesEntity implements Parcelable {
    public String title;
    public String description;
    public String date;

    public static final Creator<NotesEntity> CREATOR = new Creator<NotesEntity>() {
        @Override
        public NotesEntity createFromParcel(Parcel in) {
            return new NotesEntity(in);
        }

        @Override
        public NotesEntity[] newArray(int size) {
            return new NotesEntity[size];
        }
    };

    public NotesEntity(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    protected NotesEntity(Parcel in) {
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

    @Override
    public String toString() {
        return title + " " + description + " " + date;
    }
}
