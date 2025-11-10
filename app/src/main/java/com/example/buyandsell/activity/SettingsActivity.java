package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.buyandsell.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch darkModeSwitch;
    private Button languageButton;
    private Button notificationsButton;
    private Button aboutButton;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        darkModeSwitch = findViewById(R.id.switch_dark_mode);
        languageButton = findViewById(R.id.btn_language);
        notificationsButton = findViewById(R.id.btn_notifications);
        aboutButton = findViewById(R.id.btn_about);
    }

    private void setupClickListeners() {
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
                // TODO: Implement dark mode theme switching
            } else {
                Toast.makeText(this, "Light mode enabled", Toast.LENGTH_SHORT).show();
                // TODO: Implement light mode theme switching
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Language settings", Toast.LENGTH_SHORT).show();
                // TODO: Implement language selection
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Notification settings", Toast.LENGTH_SHORT).show();
                // TODO: Implement notification preferences
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "About Buy & Sell App v1.0", Toast.LENGTH_SHORT).show();
                // TODO: Show about dialog
            }
        });
    }
}
