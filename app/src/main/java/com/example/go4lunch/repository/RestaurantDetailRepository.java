package com.example.go4lunch.repository;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.datasource.RetrofitClient;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.model.Result;
import com.example.go4lunch.ui.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailRepository {
    private final String TAG = getClass().getSimpleName();
    private final MutableLiveData<Restaurant> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Restaurant> requestRestaurantDetails(String placeId) {
        Call<Result> call = RetrofitClient.getInstance().getMyApi().getRestaurantDetails(BuildConfig.GOOGLE_MAPS_KEY, placeId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //Log.d(TAG, "getRestaurants response="+response );
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getRestaurants response=" + response);
                    Log.i(TAG, "onResponse: " + response.body().getRestaurant().getName());
                    mutableLiveData.setValue(response.body().getRestaurant());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(TAG, "onFailure :" + t.getMessage());
                mutableLiveData.setValue(null);
                t.printStackTrace();
            }

        });
        return mutableLiveData;
    }
}
