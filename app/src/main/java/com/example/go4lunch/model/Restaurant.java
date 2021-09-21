package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant {
    /**
     * Identifier
     */
    @SerializedName("place_id")
    private String placeId;

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
     * Phone number of the restaurant
     */
    @SerializedName("formatted_phone_number")
    private String phoneNumber;

    /**
     * Website url of the restaurant
     */
    private String website;

    /**
     * Image of the restaurant
     */
    @SerializedName("icon")
    private String image;

    private List<Photo> photos;

    /**
     * Location of the restaurant
     */
    private Geometry geometry;

    /**
     * Opening time of the restaurant
     */
    @SerializedName("opening_hours")
    private OpenNow openingTime;

    /**
     * Rating of the restaurant
     */
    private float rating;

    /**
     * Number of person in the restaurant
     */
    private int numberPersons;

    public Restaurant() {
    }

    public Restaurant(String place_id, String name, String address, String image, Geometry geometry, OpenNow openingTime, float rating, int numberPersons, List<Photo> photos, String phoneNumber, String website) {
        this.placeId = place_id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.geometry = geometry;
        this.openingTime = openingTime;
        this.rating = rating;
        this.numberPersons = numberPersons;
        this.photos = photos;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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

    public OpenNow getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(OpenNow openingTime) {
        this.openingTime = openingTime;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
