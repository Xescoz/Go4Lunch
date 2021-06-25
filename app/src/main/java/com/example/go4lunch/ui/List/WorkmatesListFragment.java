package com.example.go4lunch.ui.List;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.databinding.FragmentWorkmatesBinding;
import com.example.go4lunch.model.Workmate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkmatesListFragment extends Fragment {

    FragmentWorkmatesBinding binding;
    WorkmateRecyclerViewAdapter adapter;
    private final List<Workmate> workmateList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        adapter.notifyDataSetChanged();
    }

    private void initList() {
        Workmate workmate = new Workmate(1, "Jean", "La pizzeria", "https://i.pinimg.com/564x/cf/10/a1/cf10a15b77cfdb2e712091b265c3af45.jpg");
        workmateList.add(workmate);
        Workmate workmate2 = new Workmate(2, "Bob", "", "https://i2.wp.com/zeeoii.com/wp-content/uploads/2020/07/heart-3840x2160-4k-hd-wallpaper-green-leaves-bush-10662.jpg?resize=1024%2C576&ssl=1");
        workmateList.add(workmate2);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkmatesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new WorkmateRecyclerViewAdapter(workmateList, this.getContext());
        binding.workmatesRecyclerView.setAdapter(adapter);
        binding.workmatesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}