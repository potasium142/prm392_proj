package com.example.prm392_proj.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.example.prm392_proj.model.User;

@Dao
public interface UserDao {

    @Insert
    Long insert(User user);

    @Query("SELECT * FROM User")
    List<User> getListUser();

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username)")
    Boolean usernameExist(String username);

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username AND password = :password)")
    Boolean accountExist(String username, String password);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);
}
