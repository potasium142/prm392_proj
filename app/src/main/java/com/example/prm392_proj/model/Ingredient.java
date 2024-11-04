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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity(tableName = "ingredient")
public class Ingredient implements Serializable {
    private int recipeId;
    private String name;
    private String amount;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Ingredient(int recipeId, String name, String amount) {
        this.recipeId = recipeId;
        this.name = name;
        this.amount = amount;
    }
}
