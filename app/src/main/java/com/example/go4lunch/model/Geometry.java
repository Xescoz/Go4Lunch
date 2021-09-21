package com.example.go4lunch.model;

public class Geometry {
    private Location location;

    public Geometry(){}
    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
