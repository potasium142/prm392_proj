package database.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "instruction")
@Getter
@Setter
public class InstructionList {
    @PrimaryKey(autoGenerate = true)
    private int index;
    private int recipeId;
    private String instruction;
    private int step;
}
