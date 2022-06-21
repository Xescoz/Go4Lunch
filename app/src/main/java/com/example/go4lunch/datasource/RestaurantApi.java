package com.example.go4lunch.datasource;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.model.Result;
import com.example.go4lunch.ui.MapsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestaurantApi {

    @GET("nearbysearch/json")
    Call<RestaurantResults> getAllRestaurants(@Query("key") String key, @Query("type") String type, @Query("location") String location, @Query("radius") String radius);

    @GET("details/json")
    Call<Result> getRestaurantDetails(@Query("key") String key, @Query("place_id") String placeId);
}
