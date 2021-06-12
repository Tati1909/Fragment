package com.example.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class NotesEntity implements Parcelable {
    public String title;
    public String description;
    public long date;

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

    public NotesEntity(String title, String description, long date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    protected NotesEntity(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static long getCurrentDate() {
        //возвращает текущее время в милисекундах
        return Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public String toString() {
        return title + " " + description + " " + date;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date);
    }
}
