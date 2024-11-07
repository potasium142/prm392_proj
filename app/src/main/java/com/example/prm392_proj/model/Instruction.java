package com.example.prm392_proj.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "instruction")
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Instruction implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private int index;
    private String description;

    public Instruction(int recipeId, int index, String description) {
        this.recipeId = recipeId;
        this.index = index;
        this.description = description;
    }
}
