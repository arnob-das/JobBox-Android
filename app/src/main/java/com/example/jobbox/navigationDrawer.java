package com.example.jobbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbox.authentication.Login;
import com.example.jobbox.userName.UserProfile;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobbox.databinding.ActivityNavigationDrawerBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.squareup.picasso.Picasso;


public class navigationDrawer extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize firebase
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar);

//        nav header
        // Access the NavigationView and the header view
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0); // Index 0 for the first (and usually only) header

        // handling data of current logged in user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Find the views within the header layout
        ImageView userImage = headerView.findViewById(R.id.userImage);
        TextView userName = headerView.findViewById(R.id.userName);
        TextView userEmail = headerView.findViewById(R.id.userEmail);

        if (currentUser != null) {
            // Set user name
            userName.setText(currentUser.getDisplayName());

            // Set user email
            userEmail.setText(currentUser.getEmail());

            // Set user image (if available)
            if (currentUser.getPhotoUrl() != null) {
                Picasso.get().load(currentUser.getPhotoUrl()).into(userImage);
                //userImage.setImageURI(currentUser.getPhotoUrl());
            }
        }

        DrawerLayout drawer = binding.drawerLayout;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int itemId = item.getItemId(); // Get the selected menu item's ID
                Log.d("NavDrawer", "ID: "+itemId);
                if (itemId == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    // Redirect the user to the login screen or do any other necessary actions
                    Intent intent = new Intent(navigationDrawer.this, Login.class);
                    startActivity(intent);
                    finish(); // Close the current activity to prevent the user from going back

                    Log.d("click","clicked");

                    return true;
                }

                else if (itemId == R.id.nav_home) {
                    // Navigate to HomeFragment
                    setTitle("Home");
                    NavController navController = Navigation.findNavController(navigationDrawer.this, R.id.nav_host_fragment_content_navigation_drawer);
                    navController.navigate(R.id.nav_home);
                    return true;
                } else if (itemId == R.id.nav_gallery) {
                    // Navigate to GalleryFragment
                    setTitle("Post Job");
                    NavController navController = Navigation.findNavController(navigationDrawer.this, R.id.nav_host_fragment_content_navigation_drawer);
                    navController.navigate(R.id.nav_gallery);
                    return true;
                } else if (itemId == R.id.nav_slideshow) {
                    // Navigate to SlideshowFragment
                    NavController navController = Navigation.findNavController(navigationDrawer.this, R.id.nav_host_fragment_content_navigation_drawer);
                    navController.navigate(R.id.nav_slideshow);
                    return true;
                } else if (itemId==R.id.nav_profile) {
                    Intent intent = new Intent(navigationDrawer.this, UserProfile.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}