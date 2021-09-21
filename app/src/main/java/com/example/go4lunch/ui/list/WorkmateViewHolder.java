package com.example.go4lunch.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemWorkmateBinding;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.ui.RestaurantDetailActivity;

public class WorkmateViewHolder extends RecyclerView.ViewHolder {

    private final ItemWorkmateBinding binding;

    public WorkmateViewHolder(ItemWorkmateBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void bindWorkmate(Workmate workmate, Context context){
        Glide.with(context)
                .load(workmate.getPicture())
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.ic_baseline_account_circle_black)
                .into(binding.itemWorkmateImage);

        if (workmate.getCurrentRestaurant() == null) {
            binding.itemWorkmateEatingText.setText(context.getString(R.string.workmate_eating_empty, workmate.getName()));
            binding.itemWorkmateEatingText.setTextColor(context.getResources().getColor(R.color.light_grey));
        } else
            binding.itemWorkmateEatingText.setText(context.getString(R.string.workmate_eating, workmate.getName(), workmate.getCurrentRestaurantName()));

        binding.itemWorkmateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workmate.getCurrentRestaurant() != null) {
                    Intent intent = new Intent(context, RestaurantDetailActivity.class);
                    intent.putExtra("place_id", workmate.getCurrentRestaurant());
                    context.startActivity(intent);
                }
            }
        });
    }

}
