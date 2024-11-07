package com.example.prm392_proj.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.prm392_proj.dao.IngredientDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.Ingredient;

import java.util.List;

public class IngredientRepository {

    private final IngredientDao mIngredientDao;
    private final LiveData<List<Ingredient>> mAllIngredients;

    public IngredientRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getAllIngredients();
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    public LiveData<List<Ingredient>> getAllIngredientsByRecipeId(int recipeId) {
        return mIngredientDao.getAllIngredientsByRecipeId(recipeId);
    }

    public void insert(Ingredient ingredient) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mIngredientDao.insert(ingredient);
        });
    }

    public void update(Ingredient ingredient) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mIngredientDao.update(ingredient);
        });
    }

    public void delete(Ingredient ingredient) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mIngredientDao.delete(ingredient);
        });
    }

    public void deleteAll() {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mIngredientDao.deleteAll();
        });
    }

    public void deleteAllIngredientsByRecipeId(int recipeId) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mIngredientDao.deleteAllIngredientsByRecipeId(recipeId);
        });
    }

    public List<Ingredient> getAllIngredientsByRecipeIdSync(int recipeId) {
        return mIngredientDao.getAllIngredientsByRecipeIdSync(recipeId);
    }

    public List<Ingredient> getAllIngredientsSync() {
        return mIngredientDao.getAllIngredientsSync();
    }

}
