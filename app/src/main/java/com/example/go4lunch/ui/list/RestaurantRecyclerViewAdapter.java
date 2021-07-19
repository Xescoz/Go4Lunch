package com.example.go4lunch.ui.list;

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
import com.example.go4lunch.model.RestaurantResults;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private List<Restaurant> restaurantList;

    public RestaurantRecyclerViewAdapter(Context context, List<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    public void updateRestaurants(List<Restaurant> restaurants){
        this.restaurantList = restaurants;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.binding.itemRestaurantTitle.setText(restaurant.getName());
        holder.binding.itemRestaurantAddress.setText(restaurant.getAddress());
        //holder.binding.itemRestaurantDistance.setText(restaurant.getLocation());
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

        if(restaurant.getRating()<=2 && restaurant.getRating()!= 0)
            holder.binding.itemRestaurantOneStar.setVisibility(View.VISIBLE);

        else if(restaurant.getRating()>2 && restaurant.getRating()<4) {
            holder.binding.itemRestaurantOneStar.setVisibility(View.VISIBLE);
            holder.binding.itemRestaurantTwoStar.setVisibility(View.VISIBLE);
        }

        else if(restaurant.getRating()>=4) {
            holder.binding.itemRestaurantOneStar.setVisibility(View.VISIBLE);
            holder.binding.itemRestaurantTwoStar.setVisibility(View.VISIBLE);
            holder.binding.itemRestaurantThreeStar.setVisibility(View.VISIBLE);
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
