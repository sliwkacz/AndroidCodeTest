package com.rockspin.androiddevtest.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bsliwa on 10.03.2017.
 */

public class CosmonautActivity implements Parcelable {

    private String date;
    private String duration;
    private String eva;
    private String country;
    private String purpose;
    private String crew;
    private String vehicle;

    protected CosmonautActivity(Parcel in) {
        date = in.readString();
        duration = in.readString();
        eva = in.readString();
        country = in.readString();
        purpose = in.readString();
        crew = in.readString();
        vehicle = in.readString();
    }

    public static final Creator<CosmonautActivity> CREATOR = new Creator<CosmonautActivity>() {
        @Override
        public CosmonautActivity createFromParcel(Parcel in) {
            return new CosmonautActivity(in);
        }

        @Override
        public CosmonautActivity[] newArray(int size) {
            return new CosmonautActivity[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEva() {
        return eva;
    }

    public void setEva(String eva) {
        this.eva = eva;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(duration);
        parcel.writeString(eva);
        parcel.writeString(country);
        parcel.writeString(purpose);
        parcel.writeString(crew);
        parcel.writeString(vehicle);
    }
}
