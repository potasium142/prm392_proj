package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.entities.models.Ingredient;
import database.entities.models.Instruction;

@Dao
public interface InstructionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instruction instruction);

    @Query("SELECT * FROM instruction")
    LiveData<List<Instruction>> getAllInstructions();

    @Query("SELECT * FROM instruction WHERE recipeId = :recipeId")
    LiveData<List<Instruction>> getAllInstructionsByRecipeId(int recipeId);
}
