package com.example.buyandsell.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.buyandsell.activity.CategoryActivity;
import com.example.buyandsell.activity.MainActivity;
import com.example.buyandsell.activity.ProfileActivity;
import com.example.buyandsell.activity.SellActivity;

/**
 * Utility class for handling navigation between activities
 * Based on sequence diagram navigation steps
 * Updated for API 18 compatibility - No BottomNavigationView
 */
public class NavigationHelper {

    /**
     * Setup custom button navigation for API 18
     */
    public static void setupCustomNavigation(
            final Context context,
            Button navHome,
            Button navSell,
            Button navCategory,
            Button navProfile) {

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome(context);
            }
        });

        navSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSell(context);
            }
        });

        navCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCategory(context);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfile(context);
            }
        });
    }

    /**
     * Navigate to Home (Sequence Diagram step 4: homeNav())
     */
    public static void navigateToHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * Navigate to Sell (Sequence Diagram step 7-8: sellNav() -> visitSell())
     */
    public static void navigateToSell(Context context) {
        Intent intent = SellActivity.newIntent(context);
        context.startActivity(intent);
    }

    /**
     * Navigate to Category (Sequence Diagram step 5-6: categoryNav() -> productCat())
     */
    public static void navigateToCategory(Context context) {
        Intent intent = CategoryActivity.newIntent(context);
        context.startActivity(intent);
    }

    /**
     * Navigate to Profile (Sequence Diagram step 9-10: profileNav() -> visitProfile())
     */
    public static void navigateToProfile(Context context) {
        Intent intent = ProfileActivity.newIntent(context);
        context.startActivity(intent);
    }
}