package com.example.prm392_proj.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.prm392_proj.model.Comment;

import java.util.List;

public interface CommentDao {
    @Insert
    void insert(Comment comment);
    @Transaction
    @Query("SELECT c.*, u.username FROM comment c " +
            "JOIN user u ON c.userId= u.id " +
            "JOIN recipe r ON c.recipeId = r.id " +
            "WHERE r.id = :recipeId")
    List<Comment> getCommentsByRecipeId(int recipeId);
}
