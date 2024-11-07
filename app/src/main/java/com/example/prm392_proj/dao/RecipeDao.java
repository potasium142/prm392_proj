package com.example.prm392_proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_proj.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Recipe recipe);

    @Query("DELETE FROM recipe")
    void deleteAll();

    @Query("SELECT * FROM recipe WHERE id = :id")
    Recipe getRecipeById(int id);

    @Update()
    void update(Recipe recipe);

    @Delete()
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();


    @Query("SELECT * FROM recipe")
    List<Recipe> getAllRecipes_();

    @Query("SELECT COUNT(*) FROM recipe WHERE userCreatorId = :userId")
    int countRecipesByUserId(int userId);

    @Query("SELECT * FROM recipe WHERE dishName = :name LIMIT 1")
    Recipe getRecipeByName(String name);
}
