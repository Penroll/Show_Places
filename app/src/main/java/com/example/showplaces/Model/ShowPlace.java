package com.example.showplaces.Model;

import android.graphics.Bitmap;

import java.util.Objects;

public class ShowPlace {

    public String address;

    public Double lat;

    public Double lon;

    public String name;

    public Bitmap icon = null;

    public ShowPlace(String address, Double lat, Double lon, String name) {
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public ShowPlace(String address) {
        this.address = address;
    }

    public ShowPlace(String address, String name) {
        this.address = address;
        this.name = name;
    }


    public boolean equals(Object other) {
        if(!(other instanceof ShowPlace)) {
            return false;
        }
        ShowPlace otherPlace = (ShowPlace) other;
        return Objects.equals(this.name, otherPlace.name) && Objects.equals(this.address, otherPlace.address);
    }
}
