package com.example.prm392_proj.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.prm392_proj.dao.CommentDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.Comment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentRepository {

    private final CommentDao mCommentDao;
    private final LiveData<List<Comment>> mAllComments;
    private final ExecutorService executorService;

    public CommentRepository(Application application, int recipeId) {
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        mCommentDao = db.commentDao();
        mAllComments = mCommentDao.getCommentsByRecipeId(recipeId);
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Comment>> getCommentsByRecipeId() {
        return mAllComments;
    }

    public void insert(Comment comment) {
        executorService.execute(() -> mCommentDao.insert(comment));
    }

    public void update(Comment comment) {
        executorService.execute(() -> mCommentDao.update(comment));
    }

    public void delete(Comment comment) {
        executorService.execute(() -> mCommentDao.delete(comment));
    }
}
