package com.example.buyandsell.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity product);

    @Update
    void update(ProductEntity product);

    @Query("SELECT * FROM products WHERE status = :status")
    List<ProductEntity> getByStatus(String status);

    @Query("SELECT * FROM products")
    List<ProductEntity> getAll();

    @Query("SELECT * FROM products WHERE productId = :productId LIMIT 1")
    ProductEntity getById(String productId);

    @Query("SELECT * FROM products WHERE sellerId = :sellerId")
    List<ProductEntity> getBySeller(String sellerId);
}


