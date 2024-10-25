package database.entities.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

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
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userCreatorId;
    private String dishName;
    private String picture;
    private String description;
    private Date creationDate;
}
