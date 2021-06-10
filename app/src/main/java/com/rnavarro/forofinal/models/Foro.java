package com.rnavarro.forofinal.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Foro implements Parcelable {
    private String nameforo;
    private String id;

    public Foro(String nameforo) {
        this.nameforo = nameforo;
    }
    public Foro (){}

    protected Foro(Parcel in) {
        nameforo = in.readString();
        id = in.readString();
    }

    public static final Creator<Foro> CREATOR = new Creator<Foro>() {
        @Override
        public Foro createFromParcel(Parcel in) {
            return new Foro(in);
        }

        @Override
        public Foro[] newArray(int size) {
            return new Foro[size];
        }
    };

    public String getNameforo() {
        return nameforo;
    }

    public void setNameforo(String nameforo) {
        this.nameforo = nameforo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameforo);
        dest.writeString(id);
    }
}
