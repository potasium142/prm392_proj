package com.example.prm392_proj.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.prm392_proj.dao.UserDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.User;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    public UserRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        mUserDao = db.userDAO();
        mAllUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(User user) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public void update(User user) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mUserDao.update(user);
        });
    }

    public void delete(User user) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mUserDao.delete(user);
        });
    }

    public User getUserById(int userId) {
        return mUserDao.getUserById(userId);
    }
}
