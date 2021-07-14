package com.example.go4lunch.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;


public abstract class BaseFragment extends Fragment {

    private String location;
    private static final String TAG = BaseFragment.class.getSimpleName();

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public String getLocation(){
        Log.d(TAG, "location = "+location);
        return location;
    }


    public void setLocation(String location){
        this.location = location;
        Log.d(TAG, "location = "+location);
        Log.d(TAG, "location = "+this.location);
    }



    /*
    @Override
    public void onFindLocation(String location) {
        this.location = location;
    }*/
}