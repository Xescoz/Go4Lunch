package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("result")
    private Restaurant restaurant;

    public Result(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
