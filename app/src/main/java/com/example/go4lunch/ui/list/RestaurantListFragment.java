package com.example.go4lunch.ui.list;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.databinding.FragmentRestaurantListBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.RestaurantResults;
import com.example.go4lunch.ui.BaseFragment;
import com.example.go4lunch.ui.MapsFragment;
import com.example.go4lunch.viewmodel.RestaurantViewModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;


public class RestaurantListFragment extends BaseFragment {

    private RestaurantRecyclerViewAdapter adapter;
    private static final String TAG = RestaurantListFragment.class.getSimpleName();
    private RestaurantViewModel restaurantViewModel;
    private FragmentRestaurantListBinding binding;
    private List<Restaurant> restaurantsList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
    }

    @Override
    public void getLocationUser(Location locationUser) {
        initList(locationUser);
        Log.d(TAG, "location = "+locationUser);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new RestaurantRecyclerViewAdapter(this.getContext(),restaurantsList);
        binding.restaurantRecyclerView.setAdapter(adapter);
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void initList(Location location) {
        restaurantViewModel.getRestaurants(location).observe(this, restaurants ->{
                restaurantsList = restaurants.getRestaurantResults();
                adapter.updateRestaurants(restaurantsList, location);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        adapter.notifyDataSetChanged();
    }

}