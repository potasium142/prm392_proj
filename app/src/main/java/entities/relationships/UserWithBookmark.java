package entities.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import entities.models.Bookmark;
import entities.models.RecipeEntity;
import entities.models.UserEntity;

public class UserWithBookmark {
    @Embedded
    private UserEntity user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "recipeId",
            associateBy = @Junction(Bookmark.class)
    )
    private List<RecipeEntity> bookmarkedRecipes;
}
