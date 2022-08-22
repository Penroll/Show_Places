package com.example.showplaces.Model;

import android.graphics.Bitmap;

import java.util.Objects;

public class ShowContact extends ShowItem {
    //Name
    //Label
    //Description
    //Address
    //Coordinates

    public Bitmap icon = null;


    public ShowContact(String name, String label, String address) {
        super(name, label, address);
    }

    public boolean equals(Object other) {
        if(!(other instanceof ShowContact)) {
            return false;
        }
        ShowContact otherPlace = (ShowContact) other;
        return Objects.equals(this.name, otherPlace.name) && Objects.equals(this.address, otherPlace.address);
    }
}
