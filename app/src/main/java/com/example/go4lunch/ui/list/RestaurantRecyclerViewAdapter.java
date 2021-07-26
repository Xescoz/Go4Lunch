package com.example.go4lunch.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemRestaurantBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private final Context context;
    private List<Restaurant> restaurantList;
    private Location location;

    public RestaurantRecyclerViewAdapter(Context context, List<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    public void updateRestaurants(List<Restaurant> restaurants, Location location){
        this.restaurantList = restaurants;
        this.location = location;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.bind(restaurant,location,context);
    }

    @Override
    public int getItemCount(){return restaurantList.size();}
}
