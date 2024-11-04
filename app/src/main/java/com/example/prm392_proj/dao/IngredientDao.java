package com.example.prm392_proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.prm392_proj.model.Ingredient;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Query("SELECT * FROM ingredient")
    LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    LiveData<List<Ingredient>> getAllIngredientsByRecipeId(int recipeId);
}