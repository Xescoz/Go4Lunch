package com.example.go4lunch.ui.list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemRestaurantBinding;
import com.example.go4lunch.model.OpenNow;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.ui.RestaurantDetailActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    private final ItemRestaurantBinding binding;

    public RestaurantViewHolder(ItemRestaurantBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void bindRestaurant(Restaurant restaurant, List<Workmate> workmateList, LatLng location, Activity activity) {

        /* Set title and address */
        binding.itemRestaurantTitle.setText(restaurant.getName());
        binding.itemRestaurantAddress.setText(restaurant.getAddress());

        /* Set open or close */
        if (restaurant.getOpeningTime() == null) {
            OpenNow closed = new OpenNow(false);
            restaurant.setOpeningTime(closed);
        }
        if (restaurant.getOpeningTime().isOpenNow()) {
            binding.itemRestaurantOpenTime.setText(activity.getString(R.string.open));
            binding.itemRestaurantOpenTime.setTextColor(activity.getResources().getColor(R.color.green));
        } else {
            binding.itemRestaurantOpenTime.setText(activity.getString(R.string.close));
            binding.itemRestaurantOpenTime.setTextColor(activity.getResources().getColor(R.color.red));
        }

        /* Create location user and restaurant */
        Location locationUser = new Location("location");
        locationUser.setLatitude(location.latitude);
        locationUser.setLongitude(location.longitude);


        Location restaurantLocation = new Location("restaurantLocation");
        restaurantLocation.setLatitude(restaurant.getGeometry().getLocation().getLat());
        restaurantLocation.setLongitude(restaurant.getGeometry().getLocation().getLng());

        /* Set distance */
        if (location != null)
            binding.itemRestaurantDistance.setText(Math.round(locationUser.distanceTo(restaurantLocation)) + " m");

        /* Set number of person */
        if (numberOfPerson(workmateList,restaurant) == 0) {
            binding.itemRestaurantNumberPerson.setVisibility(View.GONE);
        } else {
            binding.itemRestaurantNumberPerson.setText(activity.getApplicationContext().getString(R.string.number_of_person, numberOfPerson(workmateList,restaurant)));
        }

        /* Set rating */
        if (restaurant.getRating() == 0)
            binding.itemRestaurantRatingBar.setRating(0);
        else {
            binding.itemRestaurantRatingBar.setRating((restaurant.getRating()/5)*3);
        }

        /* Set photo of restaurant */
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=" + BuildConfig.GOOGLE_MAPS_KEY;

        if (restaurant.getPhotos() != null && restaurant.getPhotos().size() > 0)
            url = url + "&photoreference=" + restaurant.getPhotos().get(0).getPhotoReference();

        Glide.with(activity.getApplicationContext())
                .load(url)
                .error(restaurant.getImage())
                .into(binding.itemRestaurantImage);

        /* Set on click listener to open detail restaurant */
        binding.itemRestaurantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), RestaurantDetailActivity.class);
                intent.putExtra("place_id", restaurant.getPlaceId());
                activity.startActivity(intent);
            }
        });

    }


    private int numberOfPerson(List<Workmate> workmateList, Restaurant restaurant){
        int numberOfPerson = 0;
        if( workmateList != null) {
            for (int i = 0; i < workmateList.size(); i++) {
                if (restaurant.getPlaceId().equals(workmateList.get(i).getCurrentRestaurant()))
                    numberOfPerson++;
            }
        }
        return numberOfPerson;
    }

}