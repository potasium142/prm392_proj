package database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import database.entities.models.UserEntity;

@Dao
public interface UserDAO {

    @Insert
    Long insertUser(UserEntity user);

    @Query("SELECT * FROM user")
    List<UserEntity> getListUser();

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    Boolean usernameExist(String username);

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND password = :password)")
    Boolean accountExist(String username, String password);
}
