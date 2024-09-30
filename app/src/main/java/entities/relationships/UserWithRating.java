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

@Getter
@Builder
public class UserWithRating {
    @Embedded
    private UserEntity user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "recipeId",
            associateBy = @Junction(Rate.class)
    )
    private List<RecipeEntity> recipes;
}
