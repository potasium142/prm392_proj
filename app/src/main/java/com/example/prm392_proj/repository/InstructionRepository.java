package com.example.prm392_proj.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.prm392_proj.dao.InstructionDao;
import com.example.prm392_proj.database.DatabaseHelper;
import com.example.prm392_proj.model.Instruction;

import java.util.List;

public class InstructionRepository {

    private final InstructionDao mInstructionDao;
    private final LiveData<List<Instruction>> mAllInstructions;

    public InstructionRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        mInstructionDao = db.instructionDao();
        mAllInstructions = mInstructionDao.getAllInstructions();
    }

    public LiveData<List<Instruction>> getAllInstructions() {
        return mAllInstructions;
    }

    public LiveData<List<Instruction>> getAllInstructionsByRecipeId(int recipeId) {
        return mInstructionDao.getAllInstructionsByRecipeId(recipeId);
    }

    public void insert(Instruction instruction) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mInstructionDao.insert(instruction);
        });
    }

    public void update(Instruction instruction) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mInstructionDao.update(instruction);
        });
    }

    public void delete(Instruction instruction) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mInstructionDao.delete(instruction);
        });
    }

    public void deleteAll() {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mInstructionDao.deleteAll();
        });
    }

    public void deleteAllInstructionsByRecipeId(int recipeId) {
        DatabaseHelper.databaseWriteExecutor.execute(() -> {
            mInstructionDao.deleteAllInstructionsByRecipeId(recipeId);
        });
    }

    public List<Instruction> getAllInstructionsByRecipeIdSync(int recipeId) {
        return mInstructionDao.getAllInstructionsByRecipeIdSync(recipeId);
    }

    public List<Instruction> getAllInstructionsSync() {
        return mInstructionDao.getAllInstructionsSync();
    }

}
