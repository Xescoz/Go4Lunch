package com.example.go4lunch.ui.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemRestaurantBinding;
import com.example.go4lunch.model.Restaurant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Restaurant> restaurantList;

    public RestaurantRecyclerViewAdapter(List<Restaurant> restaurantList, Context context){
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.binding.itemRestaurantTitle.setText(restaurant.getName());
        holder.binding.itemRestaurantAddress.setText(restaurant.getAddress());
        holder.binding.itemRestaurantDistance.setText(restaurant.getDistance());
        holder.binding.itemRestaurantOpenTime.setText(restaurant.getOpeningTime());
        Glide.with(context)
                .load(restaurant.getImage())
                .into(holder.binding.itemRestaurantImage);
        if(restaurant.getNumberPersons() == 0){
            holder.binding.itemRestaurantNumberPerson.setVisibility(View.GONE);
            holder.binding.itemRestaurantNumberPersonImage.setVisibility(View.GONE);
        }
        else {
            holder.binding.itemRestaurantNumberPerson.setText(context.getString(R.string.number_of_person, restaurant.getNumberPersons()));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemRestaurantBinding binding;

        public ViewHolder(ItemRestaurantBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount(){return restaurantList.size();}
}
