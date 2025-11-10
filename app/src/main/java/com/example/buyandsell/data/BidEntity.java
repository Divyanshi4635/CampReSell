package com.example.buyandsell.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bids")
public class BidEntity {
    @PrimaryKey
    @NonNull
    public String bidId;

    public String productId;

    public String bidderId;

    public double amount;

    public long createdAtEpochMs;
}


