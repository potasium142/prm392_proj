package com.example.prm392_proj.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_proj.dao.IngredientDao;
import com.example.prm392_proj.dao.InstructionDao;
import com.example.prm392_proj.model.Ingredient;
import com.example.prm392_proj.model.Instruction;

import java.util.List;

public class RecipeViewModel extends ViewModel {
    private LiveData<List<Ingredient>> ingredients;
    private LiveData<List<Instruction>> instructions;

    public RecipeViewModel(int recipeId, IngredientDao ingredientDao) {
        this.ingredients = ingredientDao.getAllIngredientsByRecipeId(recipeId);
    }

    public RecipeViewModel(int recipeId, InstructionDao instructionDao) {
        this.instructions = instructionDao.getAllInstructionsByRecipeId(recipeId);
    }

    public RecipeViewModel(int recipeId, IngredientDao ingredientDao, InstructionDao instructionDao) {
        this.ingredients = ingredientDao.getAllIngredientsByRecipeId(recipeId);
        this.instructions = instructionDao.getAllInstructionsByRecipeId(recipeId);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public LiveData<List<Instruction>> getInstructions(){return instructions;
    }
}
