package database.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "ingredient")
public class IngredientList {
    @PrimaryKey(autoGenerate = true)
    private int index;
    private int recipeId;
    private String ingredient;
    private int amount;
}
