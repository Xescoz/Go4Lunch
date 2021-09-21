package com.example.go4lunch.ui.list;

import android.app.Activity;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.databinding.ItemRestaurantBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private final Activity activity;
    private final List<Restaurant> restaurantList;
    private final List<Workmate> workmateList;
    private final Location location;

    public RestaurantRecyclerViewAdapter(Activity activity, List<Restaurant> restaurantList, List<Workmate> workmateList, Location location) {
        this.restaurantList = restaurantList;
        this.activity = activity;
        this.workmateList = workmateList;
        this.location = location;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bindRestaurant(restaurant, workmateList, location, activity);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
