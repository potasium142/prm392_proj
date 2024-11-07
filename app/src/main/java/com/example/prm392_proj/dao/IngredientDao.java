package com.example.prm392_proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.prm392_proj.model.Ingredient;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Query("SELECT * FROM ingredient")
    LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredientsSync();

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    LiveData<List<Ingredient>> getAllIngredientsByRecipeId(int recipeId);

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    List<Ingredient> getAllIngredientsByRecipeIdSync(int recipeId);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("DELETE FROM ingredient WHERE recipeId = :recipeId")
    void deleteAllIngredientsByRecipeId(int recipeId);

    @Query("DELETE FROM ingredient")
    void deleteAll();
}
