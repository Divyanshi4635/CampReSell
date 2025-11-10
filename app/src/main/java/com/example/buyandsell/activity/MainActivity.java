package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.buyandsell.Fragment.HomeFragment;
import com.example.buyandsell.Fragment.SearchFragment;

import com.example.buyandsell.R;

public class MainActivity extends FragmentActivity {

    private LinearLayout navHome, navSell, navCategory, navProfile;
    private DrawerLayout drawerLayout;
    private LinearLayout navigationDrawer;
    private Switch darkModeSwitch;
    private LinearLayout languageLayout, notificationsLayout, developerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_drawer);

        initializeViews();
        setupCustomBottomNavigation();
        setupDrawerSettings();

        seedDemoUsers();

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void seedDemoUsers() {
        new android.os.AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    com.example.buyandsell.data.CustomerDao dao = com.example.buyandsell.data.AppDatabase
                            .getInstance(MainActivity.this).customerDao();
                    if (dao.getById("21XYZ001") == null) {
                        com.example.buyandsell.data.CustomerEntity u1 = new com.example.buyandsell.data.CustomerEntity();
                        u1.id = "21XYZ001";
                        u1.name = "Demo Seller";
                        u1.email = "21XYZ001@uohyd.ac.in";
                        u1.mobileNumber = "+91-9000000001";
                        u1.password = "password";
                        u1.isAdmin = false;
                        dao.insert(u1);
                    }
                    if (dao.getById("21XYZ002") == null) {
                        com.example.buyandsell.data.CustomerEntity u2 = new com.example.buyandsell.data.CustomerEntity();
                        u2.id = "21XYZ002";
                        u2.name = "Demo Bidder";
                        u2.email = "21XYZ002@uohyd.ac.in";
                        u2.mobileNumber = "+91-9000000002";
                        u2.password = "password";
                        u2.isAdmin = false;
                        dao.insert(u2);
                    }
                } catch (Exception ignored) {}
                return null;
            }
        }.execute();
    }

    private void initializeViews() {
        navHome = findViewById(R.id.nav_home);
        navSell = findViewById(R.id.nav_sell);
        navCategory = findViewById(R.id.nav_category);
        navProfile = findViewById(R.id.nav_profile);
        
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawer = findViewById(R.id.navigation_drawer);
        darkModeSwitch = findViewById(R.id.switch_dark_mode);
        languageLayout = findViewById(R.id.layout_language);
        notificationsLayout = findViewById(R.id.layout_notifications);
        developerLayout = findViewById(R.id.layout_developer);
    }

    private void setupCustomBottomNavigation() {
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
                updateNavigationState(navHome);
            }
        });

        navSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSell();
                updateNavigationState(navSell);
            }
        });

        navCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCategory();
                updateNavigationState(navCategory);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfile();
                updateNavigationState(navProfile);
            }
        });
    }

    private void setupDrawerSettings() {
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
                // TODO: Implement dark mode theme switching
            } else {
                Toast.makeText(this, "Light mode enabled", Toast.LENGTH_SHORT).show();
                // TODO: Implement light mode theme switching
            }
        });

        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Language settings", Toast.LENGTH_SHORT).show();
                // TODO: Implement language selection
            }
        });

        notificationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new android.content.Intent(MainActivity.this, NotificationsActivity.class));
            }
        });

        developerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Developer: CampReSell Team\nVersion: 1.0.0\nBuild: 2024", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateNavigationState(View activeView) {
        // Reset all navigation items
        resetNavigationItem(navHome);
        resetNavigationItem(navCategory);
        resetNavigationItem(navSell);
        resetNavigationItem(navProfile);
        
        // Set active state
        setActiveNavigationItem(activeView);
    }

    private void resetNavigationItem(View view) {
        // Find ImageView and TextView within the LinearLayout
        ImageView icon = null;
        TextView text = null;
        
        // Search for ImageView and TextView in the LinearLayout
        for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
            View child = ((LinearLayout) view).getChildAt(i);
            if (child instanceof ImageView) {
                icon = (ImageView) child;
            } else if (child instanceof TextView) {
                text = (TextView) child;
            }
        }
        
        if (icon != null) {
            icon.setColorFilter(getResources().getColor(R.color.navInactive));
        }
        if (text != null) {
            text.setTextColor(getResources().getColor(R.color.navInactive));
        }
    }

    private void setActiveNavigationItem(View view) {
        // Find ImageView and TextView within the LinearLayout
        ImageView icon = null;
        TextView text = null;
        
        // Search for ImageView and TextView in the LinearLayout
        for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
            View child = ((LinearLayout) view).getChildAt(i);
            if (child instanceof ImageView) {
                icon = (ImageView) child;
            } else if (child instanceof TextView) {
                text = (TextView) child;
            }
        }
        
        if (icon != null) {
            icon.setColorFilter(getResources().getColor(R.color.navActive));
        }
        if (text != null) {
            text.setTextColor(getResources().getColor(R.color.navActive));
        }
    }

    private void navigateToHome() {
        loadFragment(new HomeFragment());
    }

    private void navigateToSell() {
        // Start Sell Activity
        startActivity(com.example.buyandsell.activity.SellActivity.newIntent(this));
    }

    private void navigateToCategory() {
        // Start Category Activity
        startActivity(CategoryActivity.newIntent(this));
    }

    private void navigateToProfile() {
        // Start Profile Activity
        startActivity(com.example.buyandsell.activity.ProfileActivity.newIntent(this));
    }

    // Handle search functionality
    public void searchItem(String query) {
        // Show search results fragment
        SearchFragment searchFragment = SearchFragment.newInstance(query);
        loadFragmentWithBackStack(searchFragment);
    }

    // Open settings drawer
    public void openSettingsDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(navigationDrawer);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void loadFragmentWithBackStack(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}