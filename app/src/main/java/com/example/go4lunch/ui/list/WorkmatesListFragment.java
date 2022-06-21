package com.example.go4lunch.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.databinding.FragmentWorkmatesBinding;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.viewmodel.WorkmateViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class WorkmatesListFragment extends Fragment {

    FragmentWorkmatesBinding binding;
    WorkmateRecyclerViewAdapter adapter;
    private List<Workmate> workmateList = new ArrayList<>();
    private WorkmateViewModel workmateViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);

        initWorkmates();
    }

    private void initWorkmates(){
        workmateViewModel.getAllUserFromDB(true).observe(getViewLifecycleOwner(), workmateList -> {
            this.workmateList = workmateList;
            initRecyclerView();
        });
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkmatesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new WorkmateRecyclerViewAdapter(workmateList,this.getContext());
        binding.workmatesRecyclerView.setAdapter(adapter);
        binding.workmatesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}