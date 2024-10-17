package database.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "recipe")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;

    private int userCreatorId;

    private String picture;

    private String description;

}
