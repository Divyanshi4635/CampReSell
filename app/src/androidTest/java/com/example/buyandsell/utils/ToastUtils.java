package com.example.buyandsell.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class for showing toast messages
 */
public class ToastUtils {

    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showSearchNoResults(Context context) {
        showShortToast(context, "No products found matching your search.");
    }

    public static void showNetworkError(Context context) {
        showLongToast(context, "Network error. Please check your connection.");
    }
}