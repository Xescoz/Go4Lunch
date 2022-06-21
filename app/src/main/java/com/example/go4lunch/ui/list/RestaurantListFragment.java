package com.example.go4lunch.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.databinding.FragmentRestaurantListBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.ui.BaseFragment;
import com.example.go4lunch.viewmodel.RestaurantViewModel;
import com.example.go4lunch.viewmodel.WorkmateViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RestaurantListFragment extends BaseFragment {

    private RestaurantRecyclerViewAdapter adapter;
    private static final String TAG = RestaurantListFragment.class.getSimpleName();
    private RestaurantViewModel restaurantViewModel;
    private WorkmateViewModel workmateViewModel;
    private FragmentRestaurantListBinding binding;
    private List<Restaurant> restaurantsList = new ArrayList<>();
    private LatLng location;
    private String searchPlaceId;
    private List<Workmate> workmateList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWorkmate();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void getLocationUser(LatLng locationUser) {
        initRestaurantList(locationUser);
    }

    private void initRecyclerView() {
        adapter = new RestaurantRecyclerViewAdapter(this.getActivity(), restaurantsList, workmateList, location);
        binding.restaurantRecyclerView.setAdapter(adapter);
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void initRestaurantList(LatLng location) {
        restaurantViewModel.getRestaurants(location).observe(this, restaurants -> {
            restaurantsList = restaurants.getRestaurantResults();
            this.location = location;
        });
    }

    private void initWorkmate(){
        workmateViewModel.getAllUserFromDB(false).observe(getViewLifecycleOwner(), workmateList -> {
            this.workmateList = workmateList;
            initRecyclerView();
        });
    }
}