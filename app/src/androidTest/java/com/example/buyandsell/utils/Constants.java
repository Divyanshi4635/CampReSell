package com.example.buyandsell.utils;

/**
 * Constants used throughout the application
 */
public class Constants {

    // Navigation constants
    public static final String NAV_HOME = "home";
    public static final String NAV_SELL = "sell";
    public static final String NAV_CATEGORY = "category";
    public static final String NAV_PROFILE = "profile";

    // Intent extras
    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String EXTRA_SEARCH_QUERY = "search_query";
    public static final String EXTRA_CATEGORY_NAME = "category_name";

    // SharedPreferences keys
    public static final String PREFS_NAME = "BuySellPrefs";
    public static final String PREFS_USER_LOGGED_IN = "user_logged_in";
    public static final String PREFS_USER_NAME = "user_name";
    public static final String PREFS_USER_EMAIL = "user_email";

    // API endpoints (if you have backend)
    public static final String BASE_URL = "https://your-api-domain.com/";
    public static final String API_SEARCH = "api/search";
    public static final String API_PRODUCTS = "api/products";

    // Request codes
    public static final int REQUEST_CODE_SELL = 1001;
    public static final int REQUEST_CODE_PROFILE = 1002;
    public static final int REQUEST_CODE_CATEGORY = 1003;

    // Error messages
    public static final String ERROR_NO_INTERNET = "No internet connection";
    public static final String ERROR_SEARCH_FAILED = "Search failed. Please try again.";
    public static final String ERROR_NO_PRODUCTS_FOUND = "No products found matching your search.";
}