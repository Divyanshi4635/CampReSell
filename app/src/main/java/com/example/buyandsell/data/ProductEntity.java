package com.example.buyandsell.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity {

    @PrimaryKey
    @NonNull
    public String productId;

    public String sellerId; // reference to CustomerEntity.id

    public String title;

    public String description;

    public String buyDate; // stored as ISO string or yyyy-MM-dd

    public int usedYears;

    public String invoiceUri; // single invoice file URI

    public String damages; // notes

    public String imageUrisJson; // JSON array of image URIs (use converter)

    public String pickupAddress;

    public String deliveryAddress;

    public String paymentMethod; // e.g., Cash, UPI, Card

    public float adminRating; // 0-5

    public String status; // pending, approved, live, rejected

    public long bidEndTimeEpochMs; // if live for bid, 2-hour window end
}


