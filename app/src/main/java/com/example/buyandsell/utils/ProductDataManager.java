package com.example.buyandsell.utils;

import com.example.buyandsell.models.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Product Data Manager - Provides hardcoded sample data for the app
 */
public class ProductDataManager {
    
    private static List<Product> sampleProducts;
    
    static {
        initializeSampleData();
    }
    
    private static void initializeSampleData() {
        sampleProducts = new ArrayList<>();
        
        // Electronics - Mobile & Accessories
        sampleProducts.add(new Product("1", "iPhone 14 Pro", "Latest Apple smartphone with A16 Bionic chip", "Electronics", 999.99, "2024"));
        sampleProducts.add(new Product("2", "Samsung Galaxy S23", "Premium Android smartphone with excellent camera", "Electronics", 899.99, "2024"));
        sampleProducts.add(new Product("3", "OnePlus 11", "Flagship phone with fast charging", "Electronics", 699.99, "2024"));
        sampleProducts.add(new Product("4", "Google Pixel 7", "Best Android camera phone", "Electronics", 599.99, "2024"));
        sampleProducts.add(new Product("5", "AirPods Pro", "Wireless earbuds with noise cancellation", "Electronics", 249.99, "2024"));
        sampleProducts.add(new Product("6", "Sony WH-1000XM5", "Premium noise-cancelling headphones", "Electronics", 349.99, "2024"));
        sampleProducts.add(new Product("7", "Apple Watch Series 9", "Smartwatch with advanced health features", "Electronics", 399.99, "2024"));
        
        // Electronics - Computers
        sampleProducts.add(new Product("8", "MacBook Pro M2", "Powerful laptop for professionals and students", "Electronics", 1299.99, "2024"));
        sampleProducts.add(new Product("9", "Dell XPS 15", "High-performance Windows laptop", "Electronics", 1299.99, "2024"));
        sampleProducts.add(new Product("10", "HP Pavilion", "Affordable laptop for students", "Electronics", 549.99, "2024"));
        sampleProducts.add(new Product("11", "iPad Air", "Versatile tablet for work and play", "Electronics", 599.99, "2024"));
        sampleProducts.add(new Product("12", "Samsung Galaxy Tab", "Android tablet with S-Pen", "Electronics", 649.99, "2024"));
        
        // Electronics - Gaming
        sampleProducts.add(new Product("13", "PlayStation 5", "Latest gaming console", "Electronics", 499.99, "2024"));
        sampleProducts.add(new Product("14", "Xbox Series X", "Powerful gaming console", "Electronics", 499.99, "2024"));
        sampleProducts.add(new Product("15", "Nintendo Switch", "Portable gaming device", "Electronics", 299.99, "2024"));
        sampleProducts.add(new Product("16", "Gaming Mouse", "High-precision RGB gaming mouse", "Electronics", 79.99, "2024"));
        sampleProducts.add(new Product("17", "Mechanical Keyboard", "RGB mechanical keyboard", "Electronics", 129.99, "2024"));
        
        // Books
        sampleProducts.add(new Product("18", "Java Programming Guide", "Complete guide to Java development", "Books", 49.99, "2024"));
        sampleProducts.add(new Product("19", "Android Development", "Learn Android app development from scratch", "Books", 59.99, "2024"));
        sampleProducts.add(new Product("20", "Data Structures & Algorithms", "Essential computer science concepts", "Books", 39.99, "2024"));
        sampleProducts.add(new Product("21", "Clean Code", "Software craftsmanship handbook", "Books", 44.99, "2024"));
        sampleProducts.add(new Product("22", "The Pragmatic Programmer", "Software engineering classic", "Books", 34.99, "2024"));
        sampleProducts.add(new Product("23", "Python Cookbook", "Recipes for Python programming", "Books", 54.99, "2024"));
        sampleProducts.add(new Product("24", "System Design Interview", "Prepare for technical interviews", "Books", 49.99, "2024"));
        sampleProducts.add(new Product("25", "Harry Potter Complete Set", "All 7 books in beautiful box set", "Books", 99.99, "2024"));
        sampleProducts.add(new Product("26", "The Great Gatsby", "F. Scott Fitzgerald classic novel", "Books", 12.99, "2024"));
        
        // Clothing - Shoes
        sampleProducts.add(new Product("27", "Nike Air Max", "Comfortable running shoes", "Clothing", 129.99, "2024"));
        sampleProducts.add(new Product("28", "Adidas Ultraboost", "Premium running shoes", "Clothing", 179.99, "2024"));
        sampleProducts.add(new Product("29", "Air Jordan 1", "Classic basketball sneakers", "Clothing", 159.99, "2024"));
        sampleProducts.add(new Product("30", "Vans Old Skool", "Classic skate shoes", "Clothing", 59.99, "2024"));
        sampleProducts.add(new Product("31", "Converse Chuck Taylor", "Timeless canvas sneakers", "Clothing", 54.99, "2024"));
        
        // Clothing - Apparel
        sampleProducts.add(new Product("32", "Levi's Jeans", "Classic denim jeans", "Clothing", 79.99, "2024"));
        sampleProducts.add(new Product("33", "Adidas Hoodie", "Warm and comfortable hoodie", "Clothing", 69.99, "2024"));
        sampleProducts.add(new Product("34", "Nike Dri-FIT T-Shirt", "Moisture-wicking athletic shirt", "Clothing", 29.99, "2024"));
        sampleProducts.add(new Product("35", "Patagonia Fleece Jacket", "Comfortable fleece jacket", "Clothing", 89.99, "2024"));
        sampleProducts.add(new Product("36", "Levi's Denim Jacket", "Classic denim jacket", "Clothing", 99.99, "2024"));
        
        // Home & Garden - Kitchen
        sampleProducts.add(new Product("37", "Coffee Maker", "Automatic drip coffee maker", "Home & Garden", 89.99, "2024"));
        sampleProducts.add(new Product("38", "Instant Pot", "Multi-functional pressure cooker", "Home & Garden", 99.99, "2024"));
        sampleProducts.add(new Product("39", "Air Fryer", "Healthy cooking without oil", "Home & Garden", 79.99, "2024"));
        sampleProducts.add(new Product("40", "Stainless Steel Cookware Set", "Complete kitchen cookware set", "Home & Garden", 149.99, "2024"));
        sampleProducts.add(new Product("41", "Vitamix Blender", "High-performance blender", "Home & Garden", 349.99, "2024"));
        
        // Home & Garden - Decoration
        sampleProducts.add(new Product("42", "Garden Tools Set", "Complete set of gardening tools", "Home & Garden", 45.99, "2024"));
        sampleProducts.add(new Product("43", "Indoor Plants Set", "Set of 5 low-maintenance plants", "Home & Garden", 39.99, "2024"));
        sampleProducts.add(new Product("44", "Woven Basket Set", "Decorative storage baskets", "Home & Garden", 34.99, "2024"));
        sampleProducts.add(new Product("45", "Wall Art Set", "Modern abstract wall decorations", "Home & Garden", 59.99, "2024"));
        sampleProducts.add(new Product("46", "LED Strip Lights", "Smart RGB LED lighting", "Home & Garden", 29.99, "2024"));
        
        // Sports & Fitness
        sampleProducts.add(new Product("47", "Yoga Mat", "Non-slip yoga mat for exercise", "Sports", 29.99, "2024"));
        sampleProducts.add(new Product("48", "Dumbbells Set", "Adjustable weight dumbbells", "Sports", 149.99, "2024"));
        sampleProducts.add(new Product("49", "Resistance Bands", "Complete resistance band set", "Sports", 24.99, "2024"));
        sampleProducts.add(new Product("50", "Basketball", "Official size basketball", "Sports", 34.99, "2024"));
        sampleProducts.add(new Product("51", "Soccer Ball", "Professional soccer ball", "Sports", 29.99, "2024"));
        sampleProducts.add(new Product("52", "Tennis Racket", "Professional tennis racket", "Sports", 89.99, "2024"));
        sampleProducts.add(new Product("53", "Golf Clubs Set", "Complete golfing equipment", "Sports", 399.99, "2024"));
        sampleProducts.add(new Product("54", "Exercise Bike", "Home fitness cycling machine", "Sports", 249.99, "2024"));
        
        // Vehicles & Transportation
        sampleProducts.add(new Product("55", "Mountain Bike", "High-quality mountain bike", "Vehicles", 599.99, "2024"));
        sampleProducts.add(new Product("56", "City Bike", "Urban commuting bicycle", "Vehicles", 299.99, "2024"));
        sampleProducts.add(new Product("57", "Car Accessories", "Essential car accessories kit", "Vehicles", 199.99, "2024"));
        sampleProducts.add(new Product("58", "Car Phone Mount", "Magnetic phone mount for car", "Vehicles", 24.99, "2024"));
        sampleProducts.add(new Product("59", "Car Vacuum", "Portable car vacuum cleaner", "Vehicles", 39.99, "2024"));
        sampleProducts.add(new Product("60", "Electric Scooter", "Foldable electric scooter", "Vehicles", 449.99, "2024"));
        
        // Toys & Games
        sampleProducts.add(new Product("61", "Rubik's Cube", "Classic 3x3 puzzle cube", "Toys & Games", 12.99, "2024"));
        sampleProducts.add(new Product("62", "Chess Set", "Wooden chess board with pieces", "Toys & Games", 49.99, "2024"));
        sampleProducts.add(new Product("63", "Lego Architecture Set", "Build famous landmarks", "Toys & Games", 89.99, "2024"));
        sampleProducts.add(new Product("64", "Jigsaw Puzzle", "1000-piece landscape puzzle", "Toys & Games", 24.99, "2024"));
        
        // Musical Instruments
        sampleProducts.add(new Product("65", "Acoustic Guitar", "6-string steel string guitar", "Musical Instruments", 299.99, "2024"));
        sampleProducts.add(new Product("66", "Electric Guitar", "Epiphone electric guitar", "Musical Instruments", 399.99, "2024"));
        sampleProducts.add(new Product("67", "Digital Piano", "88-key weighted keyboard", "Musical Instruments", 599.99, "2024"));
        sampleProducts.add(new Product("68", "Violin", "Student violin with bow", "Musical Instruments", 199.99, "2024"));
    }
    
    /**
     * Search products by name
     */
    public static List<Product> searchByName(String query) {
        List<Product> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return results;
        }
        
        String searchQuery = query.toLowerCase().trim();
        for (Product product : sampleProducts) {
            if (product.getProductName().toLowerCase().contains(searchQuery)) {
                results.add(product);
            }
        }
        return results;
    }
    
    /**
     * Search products by category
     */
    public static List<Product> searchByCategory(String category) {
        List<Product> results = new ArrayList<>();
        if (category == null || category.trim().isEmpty()) {
            return results;
        }
        
        String searchCategory = category.toLowerCase().trim();
        for (Product product : sampleProducts) {
            if (product.getCategory().toLowerCase().contains(searchCategory)) {
                results.add(product);
            }
        }
        return results;
    }
    
    /**
     * Get all products
     */
    public static List<Product> getAllProducts() {
        return new ArrayList<>(sampleProducts);
    }
    
    /**
     * Get products by specific category
     */
    public static List<Product> getProductsByCategory(String category) {
        List<Product> results = new ArrayList<>();
        for (Product product : sampleProducts) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                results.add(product);
            }
        }
        return results;
    }
    
    /**
     * Get product by ID
     */
    public static Product getProductById(String productId) {
        for (Product product : sampleProducts) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    
    /**
     * Get ongoing bids (sample data)
     */
    public static List<Product> getOngoingBids() {
        List<Product> bids = new ArrayList<>();
        // Return first 6 products as ongoing bids
        for (int i = 0; i < Math.min(6, sampleProducts.size()); i++) {
            bids.add(sampleProducts.get(i));
        }
        return bids;
    }
}
