package com.example.prm392_proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.prm392_proj.model.Comment;
import com.example.prm392_proj.model.Ingredient;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert
    void insert(Comment comment);

    @Query("SELECT * FROM comment")
    LiveData<List<Comment>> getAllComments();
    @Transaction
    @Query("SELECT c.*, u.username FROM comment c " +
            "JOIN user u ON c.userId = u.id " +
            "JOIN recipe r ON c.recipeId = r.id " +
            "WHERE r.id = :recipeId")
    List<Comment> getCommentsByRecipeId(int recipeId);
}