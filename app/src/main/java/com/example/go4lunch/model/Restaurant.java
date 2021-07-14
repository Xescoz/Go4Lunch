package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

public class Restaurant {
    /**
     * Identifier
     */
    private long id;

    /**
     * Name of the restaurant
     */
    private String name;

    /**
     * Address of the restaurant
     */
    @SerializedName("vicinity")
    private String address;

    /**
     * Image of the restaurant
     */
    @SerializedName("icon")
    private String image;

    /**
     * Location of the restaurant
     */
    private Geometry geometry;


    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    /**
     * Opening time of the restaurant
     */
    private String openingTime;

    /**
     * Rating of the restaurant
     */
    private String rating;

    /**
     * Number of person in the restaurant
     */
    private int numberPersons;

    public Restaurant(long id, String name, String address, String image, Geometry geometry,double lat
                      ,double lng, String openingTime, String rating, int numberPersons) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.geometry = geometry;
        this.lat = lat;
        this.lng = lng;
        this.openingTime = openingTime;
        this.rating = rating;
        this.numberPersons = numberPersons;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public int getNumberPersons() {
        return numberPersons;
    }

    public void setNumberPersons(int numberPersons) {
        this.numberPersons = numberPersons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
