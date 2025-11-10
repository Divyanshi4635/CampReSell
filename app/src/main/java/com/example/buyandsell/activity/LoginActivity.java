package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyandsell.R;
import com.example.buyandsell.utils.UserManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);
        signupButton = findViewById(R.id.btn_signup);
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void performLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Allow login via University ID by auto-converting to email
        if (!email.contains("@")) {
            email = email + "@uohyd.ac.in";
        }

        UserManager userManager = UserManager.getInstance();
        if (userManager.login(email, password)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Navigate back to profile
            finish();
        } else {
            // Try Room user login
            attemptRoomLogin(email, password);
        }
    }

    private void attemptRoomLogin(final String email, final String password) {
        new android.os.AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    com.example.buyandsell.data.CustomerDao dao = com.example.buyandsell.data.AppDatabase
                            .getInstance(LoginActivity.this).customerDao();
                    com.example.buyandsell.data.CustomerEntity user = dao.login(email, password);
                    if (user != null) {
                        // Bridge to existing UserManager model
                        com.example.buyandsell.models.Customer customer = new com.example.buyandsell.models.Customer(
                                user.id, user.name, user.email
                        );
                        // Ensure seller eligibility by giving a default rating > 3
                        customer.setRating(4.5f);
                        // Copy phone from Room entity so it appears on Profile
                        customer.getProfile().setPhone(user.mobileNumber);
                        com.example.buyandsell.utils.UserManager.getInstance().setLoggedInUser(customer, user.isAdmin);
                        return true;
                    }
                } catch (Exception ignored) {}
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials. Try admin@buyandsell.com / admin123", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}
