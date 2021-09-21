package com.example.go4lunch.ui.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ItemWorkmateBinding;
import com.example.go4lunch.model.Workmate;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorkmateRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateViewHolder> {

    private final Context context;
    private final List<Workmate> workmateList;

    public WorkmateRecyclerViewAdapter(List<Workmate> workmateList, Context context) {
        this.workmateList = workmateList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public WorkmateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WorkmateViewHolder(ItemWorkmateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateViewHolder holder, int position) {
        Workmate workmate = workmateList.get(position);
        holder.bindWorkmate(workmate,context);
    }

    @Override
    public int getItemCount() {
        return workmateList.size();
    }
}
