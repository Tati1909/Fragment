package com.example.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

public class NotesEntity implements Parcelable {
    public String id;
    public String title;
    public String description;
    public long creationDate;

    //заводим конструктор по умолчанию для FireStore
    public NotesEntity() {

    }

    public NotesEntity(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = Calendar.getInstance().getTimeInMillis();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

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

    public long getCreationDate() {
        return creationDate;
    }

    protected NotesEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        creationDate = in.readLong();
    }

    public static String generateNewId() {
        return UUID.randomUUID().toString();
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
        return title + " " + description + " " + creationDate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(creationDate);
    }
}
