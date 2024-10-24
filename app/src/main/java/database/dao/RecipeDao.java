package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.entities.models.Recipe;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();
}
