package com.example.go4lunch.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemWorkmateBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;

public class RestaurantDetailViewHolder extends RecyclerView.ViewHolder {
    private final ItemWorkmateBinding binding;

    public RestaurantDetailViewHolder(ItemWorkmateBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void bindWorkmate(Workmate workmate, Restaurant restaurant, Context context){
        Glide.with(context)
                .load(workmate.getPicture())
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_baseline_account_circle_black)
                .into(binding.itemWorkmateImage);

        if (workmate.getCurrentRestaurant().equals(restaurant.getPlaceId())) {
            binding.itemWorkmateEatingText.setText(context.getString(R.string.workmate_joining, workmate.getName()));
        }

        else {
            binding.itemWorkmateLayout.setVisibility(View.GONE);
        }

    }
}
