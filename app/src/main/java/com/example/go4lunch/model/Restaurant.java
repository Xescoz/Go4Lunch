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
    @SerializedName("name")
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
    @SerializedName("location")
    private String location;

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

    public Restaurant(long id, String name, String address, String image, String distance, String openingTime, String rating, int numberPersons) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.location = distance;
        this.openingTime = openingTime;
        this.rating = rating;
        this.numberPersons = numberPersons;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
