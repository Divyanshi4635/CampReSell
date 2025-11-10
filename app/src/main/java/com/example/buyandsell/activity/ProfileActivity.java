package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyandsell.R;
import com.example.buyandsell.models.Customer;
import com.example.buyandsell.utils.UserManager;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView customerNameText;
    private TextView customerEmailText;
    private TextView customerPhoneText;
    private TextView customerAddressText;
    private TextView customerRatingText;
    private Button loginButton;
    private Button logoutButton;
    private Button updateDetailsButton;
    private Button manageProductsButton;
    private Button viewUsersButton;
    private Button manageBidsButton;

    private UserManager userManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userManager = UserManager.getInstance();
        initializeViews();
        setupClickListeners();
        updateProfileDisplay();
    }

    private void initializeViews() {
        profileImageView = findViewById(R.id.iv_profile_image);
        customerNameText = findViewById(R.id.tv_customer_name);
        customerEmailText = findViewById(R.id.tv_customer_email);
        customerPhoneText = findViewById(R.id.tv_profile_phone);
        customerAddressText = findViewById(R.id.tv_profile_address);
        customerRatingText = findViewById(R.id.tv_customer_rating);
        loginButton = findViewById(R.id.btn_login);
        logoutButton = findViewById(R.id.btn_logout);
        updateDetailsButton = findViewById(R.id.btn_edit_profile);
        manageProductsButton = findViewById(R.id.btn_manage_products);
        viewUsersButton = findViewById(R.id.btn_view_users);
        manageBidsButton = findViewById(R.id.btn_manage_bids);
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.newIntent(ProfileActivity.this);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.logout();
                updateProfileDisplay();
                Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        updateDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Update details functionality coming soon!", Toast.LENGTH_SHORT).show();
                // TODO: Implement update details dialog
            }
        });

        manageProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ManageProductsActivity.newIntent(ProfileActivity.this));
            }
        });

        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisteredUsersActivity.newIntent(ProfileActivity.this));
            }
        });

        manageBidsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ManageBidsActivity.newIntent(ProfileActivity.this));
            }
        });
    }

    private void updateProfileDisplay() {
        if (userManager.isLoggedIn()) {
            Customer customer = userManager.getCurrentUser();
            
            // Show logged in state
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            updateDetailsButton.setVisibility(View.VISIBLE);
            boolean admin = userManager.isAdmin();
            manageProductsButton.setVisibility(admin ? View.VISIBLE : View.GONE);
            viewUsersButton.setVisibility(admin ? View.VISIBLE : View.GONE);
            manageBidsButton.setVisibility(admin ? View.VISIBLE : View.GONE);
            
            // Display user information
            customerNameText.setText("Name: " + customer.getName());
            customerEmailText.setText("Email: " + customer.getEmail());
            customerPhoneText.setText("Phone: " + customer.getProfile().getPhone());
            customerAddressText.setText("Address: " + customer.getProfile().getAddress());
            customerRatingText.setText("Rating: " + userManager.getUserRating() + " ⭐");
            
            // Set profile image (placeholder)
            profileImageView.setImageResource(android.R.drawable.ic_menu_myplaces);
            
        } else {
            // Show not logged in state
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            updateDetailsButton.setVisibility(View.GONE);
            manageProductsButton.setVisibility(View.GONE);
            viewUsersButton.setVisibility(View.GONE);
            manageBidsButton.setVisibility(View.GONE);
            
            // Display empty profile
            customerNameText.setText("Name: Not logged in");
            customerEmailText.setText("Email: -");
            customerPhoneText.setText("Phone: -");
            customerAddressText.setText("Address: -");
            customerRatingText.setText("Rating: -");
            
            // Set empty profile image
            profileImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfileDisplay();
    }
}