package com.example.go4lunch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResults {
    @SerializedName("results")
    private List<Restaurant> restaurantResults;

    public RestaurantResults(List<Restaurant> restaurantResults) {
        this.restaurantResults = restaurantResults;
    }

    public List<Restaurant> getRestaurantResults() {
        return restaurantResults;
    }

    public void setRestaurantResults(List<Restaurant> restaurantResults) {
        this.restaurantResults = restaurantResults;
    }
}
