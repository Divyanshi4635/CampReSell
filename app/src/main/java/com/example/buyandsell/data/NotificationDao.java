package com.example.buyandsell.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert
    long insert(NotificationEntity n);

    @Query("SELECT * FROM notifications WHERE recipientId = :recipientId ORDER BY timestamp DESC")
    List<NotificationEntity> getByRecipient(String recipientId);

    @Query("DELETE FROM notifications")
    void clearAll();
}


