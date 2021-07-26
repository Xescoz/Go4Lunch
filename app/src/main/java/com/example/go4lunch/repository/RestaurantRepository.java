package com.example.go4lunch.repository;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.datasource.RetrofitClient;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.ui.MapsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {
    private final String TAG = getClass().getSimpleName();
    private final MutableLiveData<RestaurantResults> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<RestaurantResults> requestRestaurants(Location locationUser) {
        String location = locationUser.getLatitude()+","+locationUser.getLongitude();
        Log.d(TAG, "location repository = "+location);
        Call<RestaurantResults> call = RetrofitClient.getInstance().getMyApi().getAllRestaurants( BuildConfig.GOOGLE_MAPS_KEY,"restaurant",location,"400");
        call.enqueue(new Callback<RestaurantResults>() {
            @Override
            public void onResponse(Call<RestaurantResults> call, Response<RestaurantResults> response) {
                Log.d(TAG, "getRestaurants response="+response );
                if (response.isSuccessful() && response.body()!=null ) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RestaurantResults> call, Throwable t) {
                Log.e(TAG, "onFailure :" +t.getMessage());
                mutableLiveData.setValue(null);
                t.printStackTrace();
            }

        });
        return mutableLiveData;
    }
}
