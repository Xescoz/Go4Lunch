package com.example.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.viewmodel.RestaurantViewModel;
import com.example.go4lunch.viewmodel.WorkmateViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends BaseFragment implements GoogleMap.OnMarkerClickListener {
    private LatLng searchPlacePosition;
    private String searchPlaceName;
    private String searchPlaceId;

    private LatLng location;
    private GoogleMap map;
    private static final String TAG = MapsFragment.class.getSimpleName();
    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(48.864716, 2.349014);
    private static final int DEFAULT_ZOOM = 16;

    private RestaurantViewModel restaurantViewModel;
    private WorkmateViewModel workmateViewModel;

    private List<Workmate> workmateList = new ArrayList<>();
    private List<Restaurant> restaurantsList = new ArrayList<>();

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         */
        @Override
        public void onMapReady(GoogleMap map) {
            MapsFragment.this.map = map;

            updateLocationUI();

            if (searchPlacePosition !=null){
                moveCameraToRestaurantPosition(searchPlacePosition, searchPlaceName, searchPlaceId);
            }
            else {
                moveCameraToCurrentPosition(location);
            }
            map.setOnMarkerClickListener(MapsFragment.this);
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            searchPlacePosition = getArguments().getParcelable("position");
            searchPlaceName = getArguments().getString("name");
            searchPlaceId = getArguments().getString("placeId");
        }


        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);
    }

    /** Init the list of Restaurants and call the creation of markers for each restaurant */
    private void initRestaurantList(LatLng location) {
        initWorkmateList();
        restaurantViewModel.getRestaurants(location).observe(this, restaurants -> {
            restaurantsList = restaurants.getRestaurantResults();
            for (int i = 0; i < restaurantsList.size(); i++) {
                Restaurant restaurant = restaurantsList.get(i);
                LatLng position = new LatLng(restaurant.getGeometry().getLocation().getLat(), restaurant.getGeometry().getLocation().getLng());
                addMarker(restaurant,position);
            }
        });
    }

    /** Create the marker on the map */
    private void addMarker(Restaurant restaurant, LatLng position){
        Marker marker;
        if (isGreen(restaurant)){
            marker = map.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title(restaurant.getName()));
        }
        else{
            marker = map.addMarker(new MarkerOptions()
                    .position(position)
                    .title(restaurant.getName()));
        }
        assert marker != null;
        marker.setTag(restaurant.getPlaceId());
    }

    /** Make a marker green if it's selected by someone */
    private Boolean isGreen(Restaurant restaurant){
        boolean isGreen = false;
        for (int i = 0; i < workmateList.size(); i++) {
            if (restaurant.getPlaceId().equals(workmateList.get(i).getCurrentRestaurant())){
                isGreen = true;
            }
        }
        return isGreen;
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        String restaurantId = (String) marker.getTag();

        Intent intent = new Intent(this.getContext(), RestaurantDetailActivity.class);
        intent.putExtra("place_id", restaurantId);
        MapsFragment.this.requireContext().startActivity(intent);

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    /** Init the list of workmates */
    private void initWorkmateList(){
        workmateViewModel.getAllUserFromDB(false).observe(getViewLifecycleOwner(), workmateList -> {
            this.workmateList = workmateList;
        });
    }

    @Override
    public void getLocationUser(LatLng locationUser) {
            location = locationUser;
            if(map != null)
                moveCameraToCurrentPosition(locationUser);
    }

    /** Move camera to position of the user. If there is no position set location to Paris */
    private void moveCameraToCurrentPosition(LatLng locationUser){
        if (locationUser != null) {
            map.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(locationUser, DEFAULT_ZOOM));
            initRestaurantList(location);
        }
        else {
            Log.d(TAG, "Current location is null. Using defaults.");
            map.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }

    }

    /** Move camera to position of the restaurant from autocomplete on map */
    private void moveCameraToRestaurantPosition(LatLng locationRestaurant, String name, String id){
        Marker marker;
        if (locationRestaurant != null) {
            map.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(locationRestaurant, DEFAULT_ZOOM));
            marker = map.addMarker(new MarkerOptions()
                    .position(locationRestaurant)
                    .title(name));

            assert marker != null;
            marker.setTag(id);
        }
        else {
            Log.d(TAG, "Current location is null. Using defaults.");
            map.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }

    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}