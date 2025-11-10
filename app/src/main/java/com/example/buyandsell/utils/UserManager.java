package com.example.buyandsell.utils;

import com.example.buyandsell.models.Customer;

/**
 * User Manager - Handles user authentication and profile management
 */
public class UserManager {
    
    private static UserManager instance;
    private Customer currentUser;
    private boolean isLoggedIn = false;
    private boolean isAdmin = false;
    
    // Admin credentials
    private static final String ADMIN_EMAIL = "admin@buyandsell.com";
    private static final String ADMIN_PASSWORD = "admin123";
    
    private UserManager() {
        // Private constructor for singleton
    }
    
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
    
    /**
     * Login with email and password
     */
    public boolean login(String email, String password) {
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            // Create admin user
            currentUser = new Customer("admin_001", "Admin User", ADMIN_EMAIL);
            currentUser.getProfile().setPhone("+1-555-0123");
            currentUser.getProfile().setAddress("123 Admin Street, Tech City");
            currentUser.getProfile().setProfileImage("admin_avatar");
            currentUser.setRating(4.5f); // Set default rating above 3
            
            isLoggedIn = true;
            isAdmin = true;
            return true;
        }
        return false;
    }

    public void setLoggedInUser(Customer user, boolean admin) {
        this.currentUser = user;
        this.isLoggedIn = true;
        this.isAdmin = admin;
    }
    
    /**
     * Logout current user
     */
    public void logout() {
        currentUser = null;
        isLoggedIn = false;
        isAdmin = false;
    }
    
    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    /**
     * Get current user
     */
    public Customer getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    
    /**
     * Update user profile
     */
    public boolean updateProfile(String name, String phone, String address) {
        if (currentUser != null) {
            currentUser.setName(name);
            currentUser.getProfile().setPhone(phone);
            currentUser.getProfile().setAddress(address);
            return true;
        }
        return false;
    }
    
    /**
     * Get user rating (fake data)
     */
    public float getUserRating() {
        if (currentUser != null) {
            return 4.5f; // Fake rating
        }
        return 0.0f;
    }
}
