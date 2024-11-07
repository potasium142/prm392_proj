package com.example.prm392_proj.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_proj.model.Notification;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void insertNotification(Notification notification);

    @Update
    void updateNotification(Notification notification);  // This updates the read status

    @Query("SELECT * FROM notifications")
    List<Notification> getAllNotifications();
}
