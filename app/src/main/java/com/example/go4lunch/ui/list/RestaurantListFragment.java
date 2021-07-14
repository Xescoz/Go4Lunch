package com.example.go4lunch.ui.list;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends BaseFragment {

    private RestaurantRecyclerViewAdapter adapter;
    private static final String TAG = RestaurantListFragment.class.getSimpleName();
    private RestaurantViewModel restaurantViewModel;
    private String location;
    private FragmentRestaurantListBinding binding;
    private List<Restaurant> restaurantsList = new ArrayList<>();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        //initList();
        location = getLocation();
        Log.d(TAG, "location = "+location);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        //initList();
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

    private void initList() {
        restaurantViewModel.getRestaurants().observe(this, restaurants ->{
                restaurantsList = restaurants.getRestaurantResults();
                adapter.updateRestaurants(restaurantsList);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        adapter.notifyDataSetChanged();
    }

}