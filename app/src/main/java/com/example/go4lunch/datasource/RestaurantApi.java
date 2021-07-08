package com.example.go4lunch.datasource;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.ui.MapsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestaurantApi {

    @GET("json")
    Call<RestaurantResults> getAllRestaurants(@Query("key") String key,@Query("type") String restaurant,@Query("location") String location,  @Query("radius")String radius);
}
