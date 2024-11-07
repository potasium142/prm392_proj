package com.example.prm392_proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_proj.model.Instruction;

import java.util.List;

@Dao
public interface InstructionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instruction instruction);

    @Query("SELECT * FROM instruction ORDER BY `index` ASC")
    LiveData<List<Instruction>> getAllInstructions();

    @Query("SELECT * FROM instruction ORDER BY `index` ASC")
    List<Instruction> getAllInstructionsSync();

    @Query("SELECT * FROM instruction WHERE recipeId = :recipeId ORDER BY `index` ASC")
    LiveData<List<Instruction>> getAllInstructionsByRecipeId(int recipeId);

    @Query("SELECT * FROM instruction WHERE recipeId = :recipeId ORDER BY `index` ASC")
    List<Instruction> getAllInstructionsByRecipeIdSync(int recipeId);

    @Update
    void update(Instruction instruction);

    @Delete
    void delete(Instruction instruction);

    @Query("DELETE FROM instruction WHERE recipeId = :recipeId")
    void deleteAllInstructionsByRecipeId(int recipeId);

    @Query("DELETE FROM instruction")
    void deleteAll();
}
