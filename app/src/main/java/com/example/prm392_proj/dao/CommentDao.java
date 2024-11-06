package com.example.prm392_proj.dao;

<<<<<<< Updated upstream
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
=======
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
>>>>>>> Stashed changes

import com.example.prm392_proj.model.Comment;

import java.util.List;

<<<<<<< Updated upstream
@Dao
public interface CommentDao {

    @Insert
    void insert(Comment comment);
    @Update
    void update(Comment comment);
    @Delete
    void delete(Comment comment);
    // Lấy tất cả các comment dựa trên recipeId
    @Query("SELECT * FROM Comment WHERE recipeId = :recipeId")
    LiveData<List<Comment>> getCommentsByRecipeId(int recipeId);

    // Lấy tất cả các comment của một user dựa trên userId
    @Query("SELECT * FROM Comment WHERE userId = :userId")
    LiveData<List<Comment>> getCommentsByUserId(int userId);

    // Lấy comment cụ thể của một user trên một recipe
    @Query("SELECT * FROM Comment WHERE userId = :userId AND recipeId = :recipeId")
    Comment getCommentByUserAndRecipe(int userId, int recipeId);

    // Xóa comment dựa trên userId và recipeId
    @Query("DELETE FROM Comment WHERE userId = :userId AND recipeId = :recipeId")
    void deleteCommentByUserAndRecipe(int userId, int recipeId);
=======
public interface CommentDao {
    @Insert
    void insert(Comment comment);
    @Transaction
    @Query("SELECT c.*, u.username FROM comment c " +
            "JOIN user u ON c.userId= u.id " +
            "JOIN recipe r ON c.recipeId = r.id " +
            "WHERE r.id = :recipeId")
    List<Comment> getCommentsByRecipeId(int recipeId);
>>>>>>> Stashed changes
}
