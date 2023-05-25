package com.example.healthdigital;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class ReminderEntry implements Parcelable {
    private String title;
    private String notes;
    private Date date = new Date();
    private LatLng latLng = new LatLng(0,0);


    public ReminderEntry(String title,String notes){
        this.title = title;
        this.notes = notes;
    }

    public ReminderEntry(String title,String notes, Date date){
        this.title = title;
        this.notes = notes;
        this.date = date;
    }

    public ReminderEntry(String title,String notes, LatLng latLng){
        this.title = title;
        this.notes = notes;
        this.latLng = latLng;
    }

    public ReminderEntry(String title,String notes, LatLng latLng, Date date){
        this.title = title;
        this.notes = notes;
        this.latLng = latLng;
        this.date = date;
    }

    protected ReminderEntry(Parcel in) {
        title = in.readString();
        notes = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<ReminderEntry> CREATOR = new Creator<ReminderEntry>() {
        @Override
        public ReminderEntry createFromParcel(Parcel in) {
            return new ReminderEntry(in);
        }

        @Override
        public ReminderEntry[] newArray(int size) {
            return new ReminderEntry[size];
        }
    };

    public LatLng getLatLng() {
        return latLng;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return title + "  " + date.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(notes);
        parcel.writeParcelable(latLng, i);
    }
}
