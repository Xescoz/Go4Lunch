package com.example.go4lunch.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityMainBinding;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.ui.list.RestaurantListFragment;
import com.example.go4lunch.ui.list.WorkmatesListFragment;
import com.example.go4lunch.viewmodel.WorkmateViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private FirebaseUser user;
    private FragmentManager fragmentManager;
    private String fragmentSelected;
    private final MapsFragment mapsFragment = new MapsFragment();
    private final RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
    private final WorkmatesListFragment workmatesListFragment = new WorkmatesListFragment();
    private Workmate workmate;
    private WorkmateViewModel workmateViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBar.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);

        fragmentManager = getSupportFragmentManager();
        fragmentSelected = "mapsFragment";
        showFragment(mapsFragment);

        initAutoComplete();

        configureBottomView();
        configureNavigationView();

        user = FirebaseAuth.getInstance().getCurrentUser();

        initDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();
    }

    /** Init the DB with the current workmate, create a new user if it does not exist or update the current user if it exist */
    private void initDB(){
        workmateViewModel.getCurrentUserFromDB(user.getUid()).observe(this, workmate -> {
            this.workmate = workmate;
            if(workmate == null){
                Workmate workmateToCreate = new Workmate(user.getDisplayName(), null, getPhoto(), null, null,false);
                workmateViewModel.writeNewUser(workmateToCreate,user.getUid());
            }
            else
                workmateViewModel.updateUserDB(getPhoto());
        });
    }

    private String getPhoto(){
        String photo = "";
        if (user.getPhotoUrl() != null)
            photo = user.getPhotoUrl().toString();

        return photo;
    }

    /** Listener for drawer menu and autocomplete */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_search:
                if(!fragmentSelected.equals("workmatesListFragment")){
                binding.appBar.autocompleteFragment.setVisibility(View.VISIBLE);
                binding.appBar.toolbar.setVisibility(View.GONE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Init place autoComplete */
    private void initAutoComplete() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.GOOGLE_MAPS_KEY, Locale.FRANCE);
        }
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG,Place.Field.TYPES));
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteFragment.setCountry("FR");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                for (Place.Type type : place.getTypes()) {
                    if (type == Place.Type.RESTAURANT) {
                        autoCompleteSuccess(place);
                        Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
                    }
                }
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        initProfile();
        return true;
    }

    /** Init the profile in the menu */
    private void initProfile() {
        TextView profileName = findViewById(R.id.profile_name);
        TextView profileEmailAddress = findViewById(R.id.profile_email_address);
        ImageView profilePicture = findViewById(R.id.profile_picture);
        profileName.setText(user.getDisplayName());
        profileEmailAddress.setText(user.getEmail());
        Glide.with(this)
                .load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_baseline_account_circle_white)
                .into(profilePicture);
    }

    /** Configure the button of the bottomView with each fragment */
    private void configureBottomView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_action:
                        showToolBar();
                        showFragment(mapsFragment);
                        fragmentSelected = "mapsFragment";
                        break;
                    case R.id.list_action:
                        showToolBar();
                        showFragment(restaurantListFragment);
                        fragmentSelected = "restaurantListFragment";
                        break;
                    case R.id.workmates_action:
                        showToolBar();
                        showFragment(workmatesListFragment);
                        fragmentSelected = "workmatesListFragment";
                        break;
                }
                return true;
            }
        });
    }

    /** Configure the button of the NavigationView in the menu */
    private void configureNavigationView() {
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_lunch:
                        showDetailActivity();
                        break;
                    case R.id.menu_settings:
                        openSettings();
                        break;
                    case R.id.menu_logout:
                        signOut();
                        break;
                }
                return true;
            }
        });
    }

    /** Open settings Activity */
    private void openSettings(){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    /** Sign out of and go back to login Activity */
    public void signOut() {
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    /** Show the detail Activity of the current restaurant */
    private void showDetailActivity() {
        if (workmate.getCurrentRestaurant() != null) {
            Intent intent = new Intent(this, RestaurantDetailActivity.class);
            intent.putExtra("place_id", workmate.getCurrentRestaurant());
            this.startActivity(intent);
        }
        else
            Toast.makeText(MainActivity.this, R.string.no_restaurant_selected, Toast.LENGTH_SHORT).show();
    }


    private void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_placeholder, fragment)
                .commit();
    }

    /** Show toolbar and hide autocomplete */
    private void showToolBar() {
        binding.appBar.autocompleteFragment.setVisibility(View.GONE);
        binding.appBar.toolbar.setVisibility(View.VISIBLE);
    }

    /** Configure the autocomplete result depending on the current fragment */
    private void autoCompleteSuccess(Place place) {
        switch (fragmentSelected) {
            case "mapsFragment":
                Bundle bundle = new Bundle();
                bundle.putParcelable("position", place.getLatLng());
                bundle.putString("name", place.getName());
                bundle.putString("placeId", place.getId());
                MapsFragment mapsFragmentWithArgument = new MapsFragment();
                mapsFragmentWithArgument.setArguments(bundle);
                showFragment(mapsFragmentWithArgument);
                break;
            case "restaurantListFragment":
                Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("place_id", place.getId());
                MainActivity.this.startActivity(intent);
                break;
            case "workmatesListFragment":
                break;
        }
    }

}