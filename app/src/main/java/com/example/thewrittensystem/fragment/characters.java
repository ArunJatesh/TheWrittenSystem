package com.example.thewrittensystem.fragment;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class characters extends ArrayList<Parcelable> {

    // Name of the Android version (e.g. Gingerbread, Honeycomb, Ice Cream Sandwich)
    private String txt;

    // Android version number (e.g. 2.3-2.7, 3.0-3.2.6, 4.0-4.0.4)
    private String id;

    // Drawable resource ID
    private int mImageResourceId;

    public characters(String txt) {
        this.txt = txt;
    }

    public characters(String txt, String id, int mImageResourceId) {
        this.txt = txt;
        this.id = id;
        this.mImageResourceId = mImageResourceId;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    @NonNull
    @Override
    public Stream<Parcelable> stream() {
        return null;
    }

}
