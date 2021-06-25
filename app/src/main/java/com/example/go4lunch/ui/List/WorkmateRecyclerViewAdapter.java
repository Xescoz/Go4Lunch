package com.example.go4lunch.ui.List;

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

public class WorkmateRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Workmate> workmateList;

    public WorkmateRecyclerViewAdapter(List<Workmate> workmateList, Context context) {
        this.workmateList = workmateList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public WorkmateRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WorkmateRecyclerViewAdapter.ViewHolder(ItemWorkmateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateRecyclerViewAdapter.ViewHolder holder, int position) {
        Workmate workmate = workmateList.get(position);

        Glide.with(context)
                .load(workmate.getPicture())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.binding.itemWorkmateImage);

        if (workmate.getRestaurant().isEmpty()) {
            holder.binding.itemWorkmateEatingText.setText(context.getString(R.string.workmate_eating_empty, workmate.getName()));
            holder.binding.itemWorkmateEatingText.setTextColor(context.getResources().getColor(R.color.light_grey));
        } else
            holder.binding.itemWorkmateEatingText.setText(context.getString(R.string.workmate_eating, workmate.getName(), workmate.getRestaurant()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemWorkmateBinding binding;

        public ViewHolder(ItemWorkmateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        return workmateList.size();
    }
}
