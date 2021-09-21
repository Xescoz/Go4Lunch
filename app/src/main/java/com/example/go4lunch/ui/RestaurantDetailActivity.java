package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityRestaurantDetailBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.viewmodel.RestaurantViewModel;
import com.example.go4lunch.viewmodel.WorkmateViewModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    private static final String TAG = RestaurantDetailActivity.class.getSimpleName();
    private ActivityRestaurantDetailBinding binding;
    private Restaurant restaurant;
    private WorkmateViewModel workmateViewModel;
    private Workmate workmate;
    private List<Workmate> workmateList = new ArrayList<>();
    private RestaurantDetailRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);

        initDB();
        initWorkmates();
        initRestaurant();
    }

    private void initRestaurant(){
        String placeId = getIntent().getStringExtra("place_id");

        RestaurantViewModel restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        restaurantViewModel.getRestaurantDetail(placeId).observe(this, restaurant ->{
            this.restaurant = restaurant;
            initView();
        });
    }

    private void initView(){

        binding.restaurantDetailName.setText(restaurant.getName());
        binding.restaurantDetailAddress.setText(restaurant.getAddress());

        if (restaurant.getRating() == 0)
            binding.itemRestaurantRatingBar.setRating(0);
        else {
            binding.itemRestaurantRatingBar.setRating((restaurant.getRating()/5)*3);
        }

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key="+ BuildConfig.GOOGLE_MAPS_KEY;

        if(restaurant.getPhotos() != null && restaurant.getPhotos().size() > 0)
            url = url+"&photoreference="+restaurant.getPhotos().get(0).getPhotoReference();
        Glide.with(this.getApplicationContext())
                .load(url)
                .error(restaurant.getImage())
                .into(binding.restaurantDetailImage);

        initRecyclerView();
        if(workmate!=null)
            initColors();
    }

    private void initListener(){
        binding.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = restaurant.getPhoneNumber().replace(" ","");
                Intent phoneCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                startActivity(phoneCall);
            }
        });

        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likesSwitch();
            }
        });

        binding.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                startActivity(browserIntent);
            }
        });

        binding.addCurrentRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRestaurantSwitch();
            }
        });
    }

    private void initDB(){
        workmateViewModel.getCurrentUserFromDB().observe(this, workmate -> {
            this.workmate = workmate;
            initListener();
        });

    }

    private void updateWorkmate(){
        workmateViewModel.mutableLiveDataCurrentRestaurantIsUpdated.observe(this, isUpdated ->{
            if(isUpdated)
                workmateViewModel.getCurrentUserFromDB().observe(this, workmate -> {
                    this.workmate = workmate;
                });
        });
    }

    private void updateLikes(){
        workmateViewModel.mutableLiveDataLikesIsUpdated.observe(this, isUpdated ->{
            if(isUpdated)
                workmateViewModel.getCurrentUserFromDB().observe(this, workmate -> {
                    this.workmate = workmate;
                });
        });
    }

    private void initColors(){
        if(workmate.getCurrentRestaurant() == null || !restaurant.getPlaceId().equals(workmate.getCurrentRestaurant())){
            binding.addCurrentRestaurant.setColorFilter(ContextCompat.getColor(RestaurantDetailActivity.this, R.color.white));
            Log.i(TAG, "White: ");
        }
        else {
            binding.addCurrentRestaurant.setColorFilter(ContextCompat.getColor(RestaurantDetailActivity.this, R.color.green_light));
            Log.i(TAG, "Green: ");
        }
    }


    private void currentRestaurantSwitch(){
        if(workmate.getCurrentRestaurant() == null || !restaurant.getPlaceId().equals(workmate.getCurrentRestaurant())){
            workmateViewModel.updateCurrentRestaurant(restaurant.getPlaceId(),restaurant.getName());
            binding.addCurrentRestaurant.setColorFilter(ContextCompat.getColor(RestaurantDetailActivity.this, R.color.green_light));
        }
        else {
            workmateViewModel.updateCurrentRestaurant(null,null);
            binding.addCurrentRestaurant.setColorFilter(ContextCompat.getColor(RestaurantDetailActivity.this, R.color.white));
        }
        Log.i(TAG, "workmate: " + workmate.getCurrentRestaurant());
        updateWorkmate();
    }

    private Boolean checkIfLiked(){
        if(workmate.getLikes() == null){
            workmate.setLikes(new ArrayList<>());
            workmate.getLikes().add(restaurant.getPlaceId());
            return false;
        }

        for(int i = 0; i<workmate.getLikes().size();i++)
            if(workmate.getLikes().get(i).equals(restaurant.getPlaceId())){
                workmate.getLikes().remove(i);
                return true;
            }
        workmate.getLikes().add(restaurant.getPlaceId());
        return false;
    }


    private void likesSwitch(){
        if(checkIfLiked()){
            workmateViewModel.updateLikes(workmate.getLikes());
            binding.likeButton.setColorFilter(ContextCompat.getColor(RestaurantDetailActivity.this, R.color.fui_transparent));
        }
        else {
            workmateViewModel.updateLikes(workmate.getLikes());
            binding.likeButton.setColorFilter(ContextCompat.getColor(RestaurantDetailActivity.this, R.color.colorPrimary));
        }
        //Log.i(TAG, "workmate: " + workmate.getCurrentRestaurant());
        updateLikes();
    }

    private void initRecyclerView() {
        adapter = new RestaurantDetailRecyclerViewAdapter(workmateList,restaurant, this);
        binding.workmatesRecyclerView.setAdapter(adapter);
        binding.workmatesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initWorkmates(){
        workmateViewModel.getAllUserFromDB(true).observe(this, workmateList -> {
            this.workmateList = workmateList;
        });
    }
}