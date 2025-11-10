package com.example.buyandsell.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class NotificationEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String recipientId; // sellerId for per-user notifications
    public String title;
    public String message;
    public String productId;
    public long timestamp;
}


