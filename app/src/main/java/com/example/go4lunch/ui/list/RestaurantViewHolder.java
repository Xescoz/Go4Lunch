package com.example.go4lunch.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemRestaurantBinding;
import com.example.go4lunch.model.Restaurant;

public class RestaurantViewHolder extends RecyclerView.ViewHolder{
    private final ItemRestaurantBinding binding;

    public RestaurantViewHolder(ItemRestaurantBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void bind(Restaurant restaurant, Location location, Context context){
        binding.itemRestaurantTitle.setText(restaurant.getName());
        binding.itemRestaurantAddress.setText(restaurant.getAddress());
        binding.itemRestaurantOpenTime.setText(restaurant.getOpeningTime());


        Location restaurantLocation=new Location("restaurantLocation");
        restaurantLocation.setLatitude(restaurant.getGeometry().getLocation().getLat());
        restaurantLocation.setLongitude(restaurant.getGeometry().getLocation().getLng());

        binding.itemRestaurantDistance.setText(Math.round(location.distanceTo(restaurantLocation))+" m");


        if(restaurant.getNumberPersons() == 0){
            binding.itemRestaurantNumberPerson.setVisibility(View.GONE);
            binding.itemRestaurantNumberPersonImage.setVisibility(View.GONE);
        }
        else {
            binding.itemRestaurantNumberPerson.setText(context.getString(R.string.number_of_person, restaurant.getNumberPersons()));
        }

        if(restaurant.getRating()<=2 && restaurant.getRating()!= 0)
            binding.itemRestaurantOneStar.setVisibility(View.VISIBLE);

        else if(restaurant.getRating()>2 && restaurant.getRating()<4) {
            binding.itemRestaurantOneStar.setVisibility(View.VISIBLE);
            binding.itemRestaurantTwoStar.setVisibility(View.VISIBLE);
        }

        else if(restaurant.getRating()>=4) {
            binding.itemRestaurantOneStar.setVisibility(View.VISIBLE);
            binding.itemRestaurantTwoStar.setVisibility(View.VISIBLE);
            binding.itemRestaurantThreeStar.setVisibility(View.VISIBLE);
        }

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key="+ BuildConfig.GOOGLE_MAPS_KEY;

        if(restaurant.getPhotos() != null && restaurant.getPhotos().size() > 0)
            url = url+"&photoreference="+restaurant.getPhotos().get(0).getPhotoReference();
        Glide.with(context)
                .load(url)
                .error(restaurant.getImage())
                .into(binding.itemRestaurantImage);

    }

}

