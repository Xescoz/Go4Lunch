package com.example.go4lunch.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.databinding.ItemWorkmateBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.ui.list.WorkmateViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantDetailRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantDetailViewHolder> {

    private final Context context;
    private final List<Workmate> workmateList;
    private final Restaurant restaurant;

    public RestaurantDetailRecyclerViewAdapter(List<Workmate> workmateList, Restaurant restaurant,Context context) {
        this.workmateList = workmateList;
        this.context = context;
        this.restaurant = restaurant;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantDetailViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RestaurantDetailViewHolder(ItemWorkmateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantDetailViewHolder holder, int position) {
        Workmate workmate = workmateList.get(position);
        holder.bindWorkmate(workmate,restaurant,context);
    }

    @Override
    public int getItemCount() {
        return workmateList.size();
    }
}
