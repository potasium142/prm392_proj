package database.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "instruction")
@Getter
@Setter
@Builder
public class Instruction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private int index;
    private String description;
}
