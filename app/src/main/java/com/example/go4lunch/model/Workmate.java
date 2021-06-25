package com.example.go4lunch.model;

public class Workmate {
    /**
     * Identifier
     */
    private long id;

    /**
     * Name of the workmate
     */
    private String name;

    /**
     * Restaurant chosen by the workmate
     */
    private String restaurant;

    /**
     * Profile picture of the workmate
     */
    private String picture;

    public Workmate(long id, String name, String restaurant, String picture) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.picture = picture;
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

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
