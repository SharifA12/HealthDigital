package com.example.healthdigital;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.sql.Timestamp;
import java.util.Date;

public class ReminderEntry{
    private String title;
    private String notes;
    private Date date;
    private String location;


    private ReminderEntry(){};

    public ReminderEntry(String title,String notes, String location, Date date){
        this.title = title;
        this.notes = notes;
        this.location = location;
        this.date = date;
    }

    public String getLocation() {
        return location;
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

    public void setLocation(String location) {
        this.location = location;
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
        if (location != null){
            return title + " " + location;
        }
        return title + "  ";
    }

}
