package entities.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import entities.models.Rate;
import entities.models.RecipeEntity;
import entities.models.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecipeWithRating {
    @Embedded
    private RecipeEntity recipe;
    @Relation(
            parentColumn = "recipeId",
            entityColumn = "userId",
            associateBy = @Junction(Rate.class)
    )
    private List<UserEntity> userEntityList;
}
