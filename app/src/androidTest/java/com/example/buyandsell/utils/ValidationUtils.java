package com.example.buyandsell.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public class ValidationUtils {

    /**
     * Validate email format
     */
    public static boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validate search query
     */
    public static boolean isValidSearchQuery(String query) {
        return query != null && query.trim().length() >= 2;
    }

    /**
     * Validate product price
     */
    public static boolean isValidPrice(double price) {
        return price > 0 && price <= 1000000; // Reasonable price range
    }

    /**
     * Validate product name
     */
    public static boolean isValidProductName(String productName) {
        return productName != null && productName.trim().length() >= 2 && productName.trim().length() <= 100;
    }

    /**
     * Validate description
     */
    public static boolean isValidDescription(String description) {
        return description != null && description.trim().length() >= 10 && description.trim().length() <= 1000;
    }
}