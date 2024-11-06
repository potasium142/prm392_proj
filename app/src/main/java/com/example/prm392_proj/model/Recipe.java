package com.example.prm392_proj.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(tableName = "recipe")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Recipe implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userCreatorId;
    private String dishName;
    private String picture;
    private String description;
    private Date creationDate;
    private String time;

    public Recipe(int userCreatorId, String dishName, String picture, String description, String time) {
        this.userCreatorId = userCreatorId;
        this.dishName = dishName;
        this.picture = picture;
        this.description = description;
        this.creationDate = new Date();
        this.time = time;
    }
}
