package database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import database.entities.models.User;

@Dao
public interface UserDAO {

    @Insert
    Long insertUser(User user);

    @Query("SELECT * FROM User")
    List<User> getListUser();

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username)")
    Boolean usernameExist(String username);

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username AND password = :password)")
    Boolean accountExist(String username, String password);
}
