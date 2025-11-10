package com.example.buyandsell.utils;

import com.example.buyandsell.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for search functionality
 * Based on sequence diagram steps: searchItem() -> getProduct() -> ProductName()
 */
public class SearchUtils {

    /**
     * Search for products based on query (Sequence Diagram step 1: searchItem())
     * @param query Search term
     * @return List of matching products
     */
    public static List<Product> searchProducts(String query, List<Product> allProducts) {
        List<Product> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String searchQuery = query.toLowerCase().trim();

        for (Product product : allProducts) {
            if (product.getProductName().toLowerCase().contains(searchQuery) ||
                    product.getDescription().toLowerCase().contains(searchQuery) ||
                    product.getCategory().toLowerCase().contains(searchQuery)) {
                results.add(product);
            }
        }

        return results;
    }

    /**
     * Check if search returned any results (Activity Diagram: IsFound?)
     */
    public static boolean isSearchFound(List<Product> searchResults) {
        return searchResults != null && !searchResults.isEmpty();
    }

    /**
     * Get product names for display (Sequence Diagram step 2: ProductName())
     */
    public static List<String> getProductNames(List<Product> products) {
        List<String> productNames = new ArrayList<>();
        for (Product product : products) {
            productNames.add(product.getProductName());
        }
        return productNames;
    }

    /**
     * Simulate product data for testing
     */
    public static List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("1", "Smartphone", "Latest smartphone with great features",
                "Electronics", 599.99, "2025"));
        products.add(new Product("2", "Laptop", "High-performance laptop for work and gaming",
                "Electronics", 999.99, "2025"));
        products.add(new Product("3", "Java Programming Book", "Learn Java programming language",
                "Books", 49.99, "2025"));
        products.add(new Product("4", "Phone Case", "Protective case for smartphones",
                "Accessories", 19.99, "2025"));
        products.add(new Product("5", "Headphones", "Wireless headphones with noise cancellation",
                "Electronics", 199.99, "2025"));

        return products;
    }
}