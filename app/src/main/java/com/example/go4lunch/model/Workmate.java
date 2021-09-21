package com.example.go4lunch.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Workmate {

    /**
     * Name of the workmate
     */
    private String name;

    /**
     * Restaurant chosen by the workmate
     */
    private String currentRestaurant;

    /**
     * Name of the restaurant chosen by the workmate
     */
    private String currentRestaurantName;

    /**
     * Profile picture of the workmate
     */
    private String picture;

    /**
     * Restaurants likes of the workmate
     */
    private List<String> likes;

    /**
     * Boolean to know if the notification is on for the workmate
     */
    private Boolean notification;

    public Workmate() {
    }

    public Workmate(String name, String currentRestaurant, String picture, String currentRestaurantName, List<String> likes, Boolean notification) {
        this.name = name;
        this.currentRestaurant = currentRestaurant;
        this.picture = picture;
        this.currentRestaurantName = currentRestaurantName;
        this.likes = likes;
        this.notification = notification;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getCurrentRestaurantName() {
        return currentRestaurantName;
    }

    public void setCurrentRestaurantName(String currentRestaurantName) {
        this.currentRestaurantName = currentRestaurantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentRestaurant() {
        return currentRestaurant;
    }

    public void setCurrentRestaurant(String currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
