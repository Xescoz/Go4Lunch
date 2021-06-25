package com.example.go4lunch.ui.List;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.databinding.FragmentRestaurantListBinding;
import com.example.go4lunch.model.Restaurant;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends Fragment {

    private RestaurantRecyclerViewAdapter adapter;
    private final List<Restaurant> restaurantList = new ArrayList<>();
    private FragmentRestaurantListBinding binding;
    private RecyclerView restaurantRecyclerView;

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
        initList();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
        adapter = new RestaurantRecyclerViewAdapter(restaurantList, this.getContext());
        binding.restaurantRecyclerView.setAdapter(adapter);
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void initList() {
        Restaurant restaurant = new Restaurant(1, "La pizzeria", "2 chemin paquerette", "https://inspyre.club/wp-content/uploads/2021/04/Cabinet-approves-Rs-11000-crore-PLI-scheme-to-promote-food-processing-696x418.jpg", "200m", "Open until 1pm", "1", 2);
        restaurantList.add(restaurant);
        Restaurant restaurant2 = new Restaurant(2, "La paella", "2 chemin llll", "https://inspyre.club/wp-content/uploads/2021/04/Cabinet-approves-Rs-11000-crore-PLI-scheme-to-promote-food-processing-696x418.jpg", "100", "Open until 15pm", "2", 0);
        restaurantList.add(restaurant2);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        adapter.notifyDataSetChanged();
    }
}