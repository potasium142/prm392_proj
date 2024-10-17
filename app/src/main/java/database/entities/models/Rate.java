package database.entities.models;

import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(primaryKeys = {"userId", "recipeId"},
        tableName = "rate")
public class Rate {
    private int userId;
    private int recipeId;
    private int rate;
    private String comment;
}
