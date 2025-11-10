package com.example.buyandsell.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(CustomerEntity customer);

    @Query("SELECT * FROM customers WHERE id = :id LIMIT 1")
    CustomerEntity getById(String id);

    @Query("SELECT * FROM customers WHERE email = :email AND password = :password LIMIT 1")
    CustomerEntity login(String email, String password);

    @Query("SELECT * FROM customers")
    List<CustomerEntity> getAll();
}


