package com.example.go4lunch.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityMainBinding;
import com.example.go4lunch.ui.list.RestaurantListFragment;
import com.example.go4lunch.ui.list.WorkmatesListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MapsFragment mapsFragment;
    private ActivityMainBinding binding;
    private FirebaseUser user;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBar.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        showMapFragment();

        configureBottomView();
        configureNavigationView();

        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        initProfile();
        return true;
    }

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

    private void configureBottomView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_action:
                        showMapFragment();
                        break;
                    case R.id.list_action:
                        showRestaurantListFragment();
                        break;
                    case R.id.workmates_action:
                        showWorkmatesListFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void configureNavigationView() {
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_lunch:
                        Toast.makeText(MainActivity.this, "It's lunch time!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_logout:
                        signOut();
                        break;
                }
                return true;
            }
        });
    }

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

    private void showMapFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_placeholder, new MapsFragment(),"MapsFragment")
                .commit();
    }

    private void showRestaurantListFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_placeholder, new RestaurantListFragment())
                .commit();
    }

    private void showWorkmatesListFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_placeholder, new WorkmatesListFragment())
                .commit();
    }
}