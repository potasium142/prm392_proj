package database.entities.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(tableName = "ingredient")
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private String name;
    private String amount;
}
