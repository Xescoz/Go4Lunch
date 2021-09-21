package com.example.go4lunch.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.model.Result;
import com.example.go4lunch.repository.RestaurantDetailRepository;
import com.example.go4lunch.repository.RestaurantsRepository;
import com.google.android.gms.maps.model.LatLng;

public class RestaurantViewModel extends ViewModel {
    private final RestaurantsRepository restaurantRepository;
    private final RestaurantDetailRepository restaurantDetailRepository;
    private MutableLiveData<RestaurantResults> mutableLiveDataRestaurantResults;
    private MutableLiveData<Restaurant> mutableLiveDataRestaurantDetail;

    public RestaurantViewModel() {
        restaurantRepository = new RestaurantsRepository();
        restaurantDetailRepository = new RestaurantDetailRepository();
    }

    public RestaurantViewModel(RestaurantsRepository restaurantsRepository,RestaurantDetailRepository restaurantDetailRepository) {
        this.restaurantRepository = restaurantsRepository;
        this.restaurantDetailRepository = restaurantDetailRepository;
    }


    public LiveData<RestaurantResults> getRestaurants(LatLng location) {
        mutableLiveDataRestaurantResults = restaurantRepository.requestRestaurants(location);

        return mutableLiveDataRestaurantResults;
    }

    public LiveData<Restaurant> getRestaurantDetail(String placeId) {
        mutableLiveDataRestaurantDetail = restaurantDetailRepository.requestRestaurantDetails(placeId);

        return mutableLiveDataRestaurantDetail;
    }
}
