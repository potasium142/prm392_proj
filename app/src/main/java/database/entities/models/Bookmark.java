package database.entities.models;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(primaryKeys = {"userId", "recipeId"}, tableName = "bookmark")
public class Bookmark {
    private int userId;
    private int recipeId;
}
