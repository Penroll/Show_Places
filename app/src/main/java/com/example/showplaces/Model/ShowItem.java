package com.example.showplaces.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class ShowItem {
    //Name
    //Label
    //Address
    //Coordinates

    public String name = null;

    public String label = null;

    public String address = null;

    public LatLng coordinates = null;

    public ShowItem(String name, String label, String address) {
        this.name = name;
        this.label = label;
        this.address = address;
        geocodeAddress();
    }

    private void geocodeAddress() {

    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public boolean equals(Object other) {
        if(!(other instanceof ShowItem)) {
            return false;
        }
        ShowItem otherItem = (ShowItem) other;
        return Objects.equals(this.name, otherItem.name) && Objects.equals(this.address, otherItem.address) && Objects.equals(this.label, otherItem.label);
    }

}
