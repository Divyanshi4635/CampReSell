package com.example.buyandsell.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buyandsell.R;


public class CategoryActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, CategoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Initialize category functionality
        visitCategory();
    }

    private void visitCategory() {
        // Implement category navigation logic
        // This corresponds to sequence diagram step 5-6
    }
}