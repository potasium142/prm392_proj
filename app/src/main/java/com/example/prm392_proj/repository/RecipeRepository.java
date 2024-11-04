package com.example.prm392_proj.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.prm392_proj.dao.RecipeDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.Recipe;

import java.util.List;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipes;

    public RecipeRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        mRecipeDao = db.recipeDao();
        mAllRecipes = mRecipeDao.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes;
    }

    void insert(Recipe recipe) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mRecipeDao.insert(recipe);
        });
    }

    void update(Recipe recipe) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mRecipeDao.update(recipe);
        });
    }

    void delete(Recipe recipe) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mRecipeDao.delete(recipe);
        });
    }
}