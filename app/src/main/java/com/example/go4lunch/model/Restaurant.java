package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant {
    /**
     * Identifier
     */
    private String place_id;

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

    private List<Photo> photos;

    /**
     * Location of the restaurant
     */
    private Geometry geometry;

    /**
     * Opening time of the restaurant
     */
    private String openingTime;

    /**
     * Rating of the restaurant
     */
    private double rating;

    /**
     * Number of person in the restaurant
     */
    private int numberPersons;

    public Restaurant(String place_id, String name, String address, String image, Geometry geometry, String openingTime, double rating, int numberPersons,List<Photo> photos) {
        this.place_id = place_id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.geometry = geometry;
        this.openingTime = openingTime;
        this.rating = rating;
        this.numberPersons = numberPersons;
        this.photos = photos;
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

    public String getId() {
        return place_id;
    }

    public void setId(String id) {
        this.place_id = id;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
