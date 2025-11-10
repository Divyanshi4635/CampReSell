package com.example.buyandsell.models;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private Profile profile;

    public Customer() {}

    public Customer(String customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.profile = new Profile();
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    private float rating;

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    // Inner Profile class
    public static class Profile {
        private String address;
        private String phone;
        private String profileImage;

        public Profile() {}

        // Getters and Setters for Profile
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getProfileImage() { return profileImage; }
        public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    }
}