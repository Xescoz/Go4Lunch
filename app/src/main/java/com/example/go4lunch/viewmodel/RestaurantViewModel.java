package com.example.go4lunch.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.repository.RestaurantRepository;

import java.util.List;

public class RestaurantViewModel extends ViewModel {
    private final RestaurantRepository restaurantRepository;
    private MutableLiveData<RestaurantResults> mutableLiveData;

    public RestaurantViewModel(){
        restaurantRepository = new RestaurantRepository();
    }

    public LiveData<RestaurantResults> getRestaurants(Location location){
        if(mutableLiveData==null){
            mutableLiveData = restaurantRepository.requestRestaurants(location);
        }
        return mutableLiveData;
    }
}
