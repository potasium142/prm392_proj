package entities.relationships;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

import entities.models.RecipeEntity;
import entities.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithRecipes {
    @Embedded
    private UserEntity user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    private List<RecipeEntity> recipes;
}
