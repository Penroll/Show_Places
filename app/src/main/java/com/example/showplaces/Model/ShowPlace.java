package com.example.showplaces.Model;


import java.util.Objects;

public class ShowPlace extends ShowItem {

    public String description = null;

    public ShowPlace(String name, String label, String address) {
        super(name, label, address);
    }


    public boolean equals(Object other) {
        if(!(other instanceof ShowPlace)) {
            return false;
        }
        ShowPlace otherItem = (ShowPlace) other;
        return Objects.equals(this.name, otherItem.name) && Objects.equals(this.address, otherItem.address) && Objects.equals(this.label, otherItem.label);
    }
}
