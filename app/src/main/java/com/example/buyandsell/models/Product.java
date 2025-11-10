package com.example.buyandsell.models;

public class Product {
    private String productId;
    private String productName;
    private String description;
    private String category;
    private double price;
    private String buyTime;

    public Product() {}

    public Product(String productId, String productName, String description,
                   String category, double price, String buyTime) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.category = category;
        this.price = price;
        this.buyTime = buyTime;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getBuyTime() { return buyTime; }
    public void setBuyTime(String buyTime) { this.buyTime = buyTime; }
}
