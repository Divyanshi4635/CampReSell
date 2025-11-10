package com.example.buyandsell.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BidEntity bid);

    @Query("SELECT * FROM bids WHERE productId = :productId ORDER BY amount DESC")
    List<BidEntity> getByProduct(String productId);

    @Query("SELECT COUNT(*) FROM bids WHERE productId = :productId")
    int countByProduct(String productId);

    @Query("DELETE FROM bids WHERE productId = :productId")
    void deleteByProduct(String productId);
}


