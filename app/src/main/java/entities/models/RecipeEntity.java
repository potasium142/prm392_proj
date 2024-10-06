package entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {
    @PrimaryKey(autoGenerate = true)
    private int recipeId;

    private int userCreatorId;

    private byte[] picture;

    private String description;

    private Map<String, Integer> ingredientList;

    private List<String> instructions;
}