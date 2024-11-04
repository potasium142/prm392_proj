package com.example.prm392_proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import androidx.room.Update;

import com.example.prm392_proj.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    Long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username)")
    Boolean usernameExist(String username);

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username AND password = :password)")
    Boolean accountExist(String username, String password);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(int id);
}
