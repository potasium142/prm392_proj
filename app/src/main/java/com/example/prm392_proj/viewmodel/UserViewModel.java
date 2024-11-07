package com.example.prm392_proj.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prm392_proj.dao.UserDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.User;

public class UserViewModel extends AndroidViewModel {
    private UserDao userDao;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application, int userId) {
        super(application);
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        userDao = db.userDAO();
        fetchUser(userId);
    }

    private void fetchUser(int userId) {
        new Thread(() -> {
            User user = userDao.getUserById(userId);
            userLiveData.postValue(user);
        }).start();
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }
}
